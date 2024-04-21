package com.qa.bugkeeper.issue.attachment;

import com.qa.bugkeeper.exception.BugKeeperException;
import com.qa.bugkeeper.issue.IssueRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.qa.bugkeeper.constant.BugKeeperConstants.ID;
import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/attachment")
public class AttachmentController {

    private final AttachmentRepository attachmentRepository;
    private final IssueRepository issueRepository;

    @PostMapping(value = "/uploadFile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String uploadFileHandler(@RequestParam("issueId") long issueId,
                                    @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                var attachment = new Attachment();
                attachment.setName(file.getOriginalFilename());
                attachment.setIssue(issueRepository.getReferenceById(issueId));
                attachment.setAttachment(file.getBytes());
                attachmentRepository.save(attachment);
                log.info("File {} successfully uploaded.", file.getOriginalFilename());
            } catch (Exception e) {
                throw new BugKeeperException("File upload failed.", e);
            }
        } else {
            log.info("File upload failed, the file is empty.");
        }
        return "redirect:/issues/%s/show".formatted(issueId);
    }

    @GetMapping(value = "/retrieveFile")
    public void showImage(@RequestParam(ID) long id, HttpServletResponse response) {
        var attachment = attachmentRepository.getReferenceById(id);
        response.setContentType(IMAGE_JPEG.getType());
        try (var outputStream = response.getOutputStream()) {
            outputStream.write(attachment.getAttachment());
        } catch (IOException e) {
            throw new BugKeeperException("Failed to retrieve attachment.", e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttachment(@PathVariable(ID) long id) {
        attachmentRepository.deleteById(id);
    }
}

package com.qa.bugkeeper.project;

import com.qa.bugkeeper.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

import static com.qa.bugkeeper.constant.BugKeeperConstants.*;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @GetMapping(value = "/projectselector")
    public String showProjectSelector(ModelMap model, Principal principal, HttpServletRequest request) {
        model.addAttribute(PROJECTS, userRepository.getReferenceById(principal.getName()).getProjects());
        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));
        return "projectselector";
    }

    @PostMapping(value = "/project")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void selectProject(@RequestBody Long id, HttpServletRequest request) {
        request.getSession().setAttribute(CURRENT_PROJECT, projectRepository.getReferenceById(id));
    }
}

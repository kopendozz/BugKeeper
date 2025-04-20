package com.qa.bugkeeper.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.qa.bugkeeper.entity.Issue;
import com.qa.bugkeeper.repository.IssueRepository;
import com.qa.bugkeeper.service.IssueService;
import com.qa.bugkeeper.repository.PriorityRepository;
import com.qa.bugkeeper.entity.Project;
import com.qa.bugkeeper.repository.ProjectRepository;
import com.qa.bugkeeper.entity.View;
import com.qa.bugkeeper.repository.StatusRepository;
import com.qa.bugkeeper.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

import static com.qa.bugkeeper.constant.BugKeeperConstants.*;

@RestController
@RequiredArgsConstructor
public class IssueController {

    public static final String ISSUE = "issue";

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final IssueRepository issueRepository;
    private final IssueService issueService;

    @GetMapping(value = {"/", "/issues**"})
    public String showHome(ModelMap model, HttpServletRequest request, Principal principal) {
        if (Objects.isNull(request.getSession().getAttribute(CURRENT_PROJECT))) return "redirect:/projectselector";

        var currentUser = userRepository.getReferenceById(principal.getName());
        var currentProject = (Project) request.getSession().getAttribute(CURRENT_PROJECT);

        request.getSession().setAttribute(CURRENT_USER, currentUser);

        model.addAttribute(PROJECTS, currentUser.getProjects());
        model.addAttribute(USERS, userRepository.findAll());
        model.addAttribute(ISSUES, projectRepository.getReferenceById(currentProject.getId()).getIssues());
        return "dashboard";
    }

    @GetMapping(value = "/issues/new")
    public String showIssueCreationPage(ModelMap model, HttpServletRequest request, Principal principal) {
        if (Objects.isNull(request.getSession().getAttribute(CURRENT_PROJECT))) return "redirect:/projectselector";

        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));

        model.addAttribute(PROJECTS, projectRepository.findAll());
        model.addAttribute(PRIORITIES, priorityRepository.findAll());
        model.addAttribute(STATUSES, statusRepository.findAll());
        model.addAttribute(USERS, userRepository.findAll());
        model.addAttribute(ACTION, "creation");
        return ISSUE;
    }

    @PostMapping(value = "/issues/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveIssue(@RequestBody Issue issue) {
        issueService.save(issue);
    }

    @GetMapping(value = "/issues/{id}/show")
    public String showIssue(ModelMap model, @PathVariable(ID) long id, HttpServletRequest request, Principal principal) {
        if (hasNoAccessToIssue(id, principal)) return "403";

        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));

        model.addAttribute(ISSUE, issueRepository.getReferenceById(id));
        model.addAttribute(ACTION, "show");
        return ISSUE;
    }

    @JsonView(View.Issues.class)
    @GetMapping(value = "/issues/{id}")
    public Issue getIssue(@PathVariable(ID) long id) {
        return issueRepository.getReferenceById(id);
    }

    @GetMapping(value = "/issues/{id}/update")
    public String updateIssue(ModelMap model, @PathVariable(ID) long id, HttpServletRequest request, Principal principal) {
        if (hasNoAccessToIssue(id, principal)) return "403";

        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));

        model.addAttribute(ISSUE, issueRepository.getReferenceById(id));
        model.addAttribute(PRIORITIES, priorityRepository.findAll());
        model.addAttribute(STATUSES, statusRepository.findAll());
        model.addAttribute(USERS, userRepository.findAll());
        model.addAttribute(ACTION, "update");
        return ISSUE;
    }

    @DeleteMapping(value = "/issues/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssue(@PathVariable(ID) long id) {
        issueRepository.deleteById(id);
    }

    private boolean hasNoAccessToIssue(long id, Principal principal) {
        return userRepository.getReferenceById(principal.getName()).getProjects()
                .stream()
                .flatMap(project -> project.getIssues().stream())
                .noneMatch(issue -> Objects.equals(issue.getId(), id));
    }
}

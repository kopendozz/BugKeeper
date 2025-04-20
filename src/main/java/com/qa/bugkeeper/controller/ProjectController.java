package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.repository.ProjectRepository;
import com.qa.bugkeeper.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.qa.bugkeeper.constant.BugKeeperConstants.*;

@RestController
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

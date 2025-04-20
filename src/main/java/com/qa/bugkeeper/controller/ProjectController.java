package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.constant.BugKeeperConstants;
import com.qa.bugkeeper.service.ProjectService;
import com.qa.bugkeeper.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.qa.bugkeeper.constant.BugKeeperConstants.CURRENT_USER;
import static com.qa.bugkeeper.constant.BugKeeperConstants.PROJECTS;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping(value = "/projectselector")
    public String showProjectSelector(ModelMap model, Principal principal, HttpServletRequest request) {
        var user = userService.findUser(principal.getName());
        model.addAttribute(PROJECTS, user.getProjects());
        request.getSession().setAttribute(CURRENT_USER, user);
        return "projectselector";
    }

    @PostMapping(value = "/project")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void selectProject(@RequestBody Long id, HttpServletRequest request) {
        var project = projectService.findProject(id);
        request.getSession().setAttribute(BugKeeperConstants.CURRENT_PROJECT, project);
    }
}

package com.qa.bugkeeper.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.qa.bugkeeper.entity.Project;
import com.qa.bugkeeper.entity.UserEntity;
import com.qa.bugkeeper.entity.View;
import com.qa.bugkeeper.repository.ProjectRepository;
import com.qa.bugkeeper.repository.UserRepository;
import com.qa.bugkeeper.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

import static com.qa.bugkeeper.constant.BugKeeperConstants.*;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @GetMapping()
    public String showAdministrations(HttpServletRequest request, Principal principal) {
        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));
        return "admin";
    }

    @GetMapping(value = "/projects")
    public String showProjects(Model model, HttpServletRequest request, Principal principal) {
        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));
        model.addAttribute(USERS, userRepository.findAll());
        model.addAttribute(PROJECTS, projectRepository.findAll());
        return "projects";
    }

    @JsonView(View.Projects.class)
    @ResponseBody
    @GetMapping(value = "/projects/{id}")
    public Project getProject(@PathVariable(ID) Long id) {
        return projectRepository.getReferenceById(id);
    }

    @PostMapping(value = "/projects/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveProject(@RequestBody Project project) {
        var users = project.getUsers().stream()
                .map(userEntity -> userRepository.getReferenceById(userEntity.getUsername()))
                .collect(Collectors.toSet());
        project.setUsers(users);
        projectRepository.save(project);
    }

    @DeleteMapping(value = "/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable(ID) long id) {
        projectRepository.deleteById(id);
    }

    @GetMapping(value = "/users")
    public String showUsers(Model model, HttpServletRequest request, Principal principal) {
        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));
        model.addAttribute(USERS, userRepository.findAll());
        return "users";
    }

    @JsonView(View.Users.class)
    @GetMapping(value = "/users/{username}")
    public UserEntity getProject(@PathVariable(USERNAME) String username) {
        return userRepository.getReferenceById(username);
    }

    @PostMapping(value = "/users/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveUser(@RequestBody UserEntity user) {
        userService.createOrUpdateUser(user);
    }

    @DeleteMapping(value = "/users/{username}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateUser(@PathVariable(USERNAME) String username) {
        userService.deactivateUser(username);
    }
}

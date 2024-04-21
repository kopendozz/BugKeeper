package com.qa.bugkeeper.auth;

import com.qa.bugkeeper.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import static com.qa.bugkeeper.constant.BugKeeperConstants.CURRENT_USER;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;

    @GetMapping(value = "/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping(value = "/403")
    public String showAccessDeniedPage(HttpServletRequest request, Principal principal) {
        request.getSession().setAttribute(CURRENT_USER, userRepository.getReferenceById(principal.getName()));
        return "403";
    }
}

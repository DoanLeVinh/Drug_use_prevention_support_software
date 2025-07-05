package com.drug.drug.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RoleRedirectController {

    @GetMapping("/role-redirect")
    public void redirectBasedOnRole(Authentication authentication, HttpServletResponse response) throws IOException {
        if (authentication == null) {
            response.sendRedirect("/login");
            return;
        }
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STAFF"))) {
            response.sendRedirect("/staff");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOCTOR"))) {
            response.sendRedirect("/doctor");
        } else {
            // Mặc định: member hoặc role khác về dashboard
            response.sendRedirect("/member/dashboard");
        }
    }
}

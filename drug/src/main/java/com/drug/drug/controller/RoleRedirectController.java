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
        if (authentication == null || !authentication.isAuthenticated()) {
            response.sendRedirect("/login?redirect=/role-redirect");
            return;
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/dashboard");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STAFF"))) {
            response.sendRedirect("/staff/dashboard");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOCTOR"))) {
            response.sendRedirect("/doctor/dashboard");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
            response.sendRedirect("/member/dashboard");
        } else {
            // Xử lý các role không xác định
            response.sendRedirect("/access-denied");
        }
    }
}
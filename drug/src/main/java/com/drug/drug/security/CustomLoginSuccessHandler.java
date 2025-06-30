package com.drug.drug.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Lấy vai trò đầu tiên của user (nếu có nhiều role thì lấy role đầu)
        String redirectURL = "/member/dashboard"; // default
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();
            if (role.equals("admin")) {
                redirectURL = "/admin/dashboard";
                break;
            } else if (role.equals("staff")) {
                redirectURL = "/staff/dashboard";
                break;
            } else if (role.equals("doctor")) {
                redirectURL = "/doctor/dashboard";
                break;
            } else if (role.equals("member")) {
                redirectURL = "/dashboard";
                break;
            }
        }
        response.sendRedirect(redirectURL);
    }
}

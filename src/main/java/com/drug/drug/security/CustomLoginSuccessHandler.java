package com.drug.drug.security;

import com.drug.drug.entity.User;
import com.drug.drug.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Lấy user từ database
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        // Set user vào session để Thymeleaf lấy được
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }

        // Xác định URL chuyển hướng theo role
        String redirectURL = "/";
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

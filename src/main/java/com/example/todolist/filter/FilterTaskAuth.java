package com.example.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todolist.user.User;
import com.example.todolist.user.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (!path.startsWith("/tasks")) {
            filterChain.doFilter(request, response);
            return;
        }

        String auth = request.getHeader("Authorization");
        String passwordEncoded = auth.substring(6).trim();
        String[] credentials = new String(Base64.getDecoder().decode(passwordEncoded)).split((":"));
        String username = credentials[0];
        String password = credentials[1];

        User user = userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(401, "user not found");
        } else {
            BCrypt.Result passwordVerified = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (passwordVerified.verified) {
                request.setAttribute("userId", user.getId());
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401, "user not found");
            }
        }



    }
}

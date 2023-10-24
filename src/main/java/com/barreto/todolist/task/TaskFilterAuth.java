package com.barreto.todolist.task;

import com.barreto.todolist.helpers.CredentialsHelper;
import com.barreto.todolist.user.UserRepository;
import com.barreto.todolist.utils.BCryptUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class TaskFilterAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        var isTasksFeature = servletPath.startsWith("/tasks");
        if (!isTasksFeature) {
            chain.doFilter(request, response);
            return;
        }

        var authorization = request.getHeader("Authorization");
        var credentialsHelper = new CredentialsHelper(authorization);
        var user = userRepository.findByUsername(credentialsHelper.getUsername());

        if (user == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        } else {
            var isValid = BCryptUtils.validate(credentialsHelper.getPassword(), user.getPassword());

            if (isValid) {
                request.setAttribute("idUser", user.getId());
                chain.doFilter(request, response);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}

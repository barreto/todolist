package com.barreto.todolist.task;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class TaskFilterAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        var authorization = request.getHeader("Authorization");
        System.out.println("auth: " + authorization);

        var encodedAuth = authorization.substring("Basic".length()).trim();
        System.out.println("encodedAuth: " + encodedAuth);

        var decodedAuthBuffer = Base64.getDecoder().decode(encodedAuth);
        System.out.println("decodedAuthBuffer: " + decodedAuthBuffer);

        var decodedAuth = new String(decodedAuthBuffer);
        System.out.println("decodedAuth: " + decodedAuth);

        var credentials = decodedAuth.split(":");
        var username = credentials[0];
        var password = credentials[1];
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        chain.doFilter(request, response);
    }
}

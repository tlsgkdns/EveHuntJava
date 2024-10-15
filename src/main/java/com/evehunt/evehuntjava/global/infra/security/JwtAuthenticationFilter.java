package com.evehunt.evehuntjava.global.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final TokenProvider tokenProvider;

    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = this.parseBearerToken(request);
            User user = this.parseUserSpecification(token);
            UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(user, token, user.getAuthorities());
            auth.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header.startsWith("Bearer ")) return header.substring(7);
        else return null;
    }

    private User parseUserSpecification(String token) {
        if(token == null || token.length() < 10) return null;
        String validateToken = tokenProvider.validateTokenAndGetSubject(token);
        if(validateToken == null) validateToken = "anonymous:anonymous";
        String[] list = validateToken.split(":");
        String authString = list[1].substring(1, list[1].length() - 1).chars().filter(c -> c != ' ').toString();
        return new User(list[0], "",  Arrays.stream(authString.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    public JwtAuthenticationFilter(@NotNull TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}

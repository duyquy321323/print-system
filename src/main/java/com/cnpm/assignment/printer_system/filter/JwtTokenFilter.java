package com.cnpm.assignment.printer_system.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cnpm.assignment.printer_system.service.JwtToken;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserDetailsService userDetailsService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            authHeader = request.getParameter("token");
        }
        if (request.getServletPath().contains("/user/login") && request.getMethod().equals("POST")) {
            if (authHeader == null)
                filterChain.doFilter(request, response);
            else
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        try {
            if (isBypassToken(request)) { // nếu request không cần xác thực
                filterChain.doFilter(request, response);
                return;
            }
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            final String token = authHeader.substring(7); // cắt prefix của token đi
            final String username = jwtToken.extractEmail(token); // lấy email từ token + check validate token
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtToken.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null,
                            userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isBypassToken(HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("/account/login", "POST"),
                Pair.of("/swagger-ui", "GET"),
                Pair.of("/v3/api-docs", "GET"),
                Pair.of("/cloudinary/all-file", "GET"),
                Pair.of("/API license URL", "GET"));
        String path = request.getServletPath();
        System.out.println(path);
        for (Pair<String, String> bypassToken : bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst())
                    && request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
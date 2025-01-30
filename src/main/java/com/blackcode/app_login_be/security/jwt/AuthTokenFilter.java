package com.blackcode.app_login_be.security.jwt;

import com.blackcode.app_login_be.model.User;
import com.blackcode.app_login_be.model.UserToken;
import com.blackcode.app_login_be.repository.UserRepository;
import com.blackcode.app_login_be.repository.UserTokenRepository;
import com.blackcode.app_login_be.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                Optional<User> dataUser = userRepository.findByUserName(username);
                dataUser.ifPresent(user -> userTokenRepository.findByUserId(user.getUserId()).ifPresent(userToken -> {
                    if (userToken.getToken().equals(jwt)) {
                        if (!userToken.getIsActive()) {
                            SecurityContextHolder.clearContext(); // Hapus konteks keamanan jika token tidak aktif
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            try {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been invalidated or expired");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        try {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been invalidated");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            logger.error("Cannot set user authentication: {}", e.getMessage());

        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}

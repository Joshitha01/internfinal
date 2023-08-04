package com.usermanagement.infrastructure.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.infrastructure.ResponseMessageDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author Timur Berezhnoi
 */
@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        clearAuthenticationAttributes(request);
        response.setStatus(SC_OK);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().print(new ObjectMapper().writeValueAsString(new ResponseMessageDTO("Logged in.")));
        response.getWriter().flush();
    }
}

package com.usermanagement.infrastructure.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.infrastructure.ResponseMessageDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author Timur Berezhnoi
 */
@Component
public class RESTLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String contentTypeKey = "Content-Type";
        String contentTypeValue = "application/json";

        response.setHeader(contentTypeKey, contentTypeValue);
        response.setStatus(SC_OK);
        response.getWriter().print(new ObjectMapper().writeValueAsString(new ResponseMessageDTO("Log out success.")));
        response.getWriter().flush();
    }
}

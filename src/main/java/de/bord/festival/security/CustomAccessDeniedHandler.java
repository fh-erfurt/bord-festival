package de.bord.festival.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * This class implements the spring security AccessDeniedHandler
 *
 * Handles the http-response in case of an unauthorized access
 * Redirects to the error403 page.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger LOG
            = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    /**
     * @param request
     * @param response
     * @param exc
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException, ServletException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.warn("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());
        }

        response.sendRedirect(request.getContextPath() + "error403");
    }
}
package com.kamadhenu.warehousemanagementsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>Custom Access Denied Handler</h1>
 * <p>
 * This class provides a custom access denied handler configuration for users not logged in
 *
 * @author Kamadhenu
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    /**
     * Runs for every request to check if there is a valid authenticated session
     *
     * @param httpServletRequest  the http servlet request object
     * @param httpServletResponse the http servlet response object
     * @param e                   the access denied exception object
     * @throws IOException      the io exception that is thrown
     * @throws ServletException the servlet exception that is thrown
     */
    @Override
    public void handle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AccessDeniedException e
    ) throws IOException, ServletException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            LOGGER.info("User '" + auth.getName() + " with role " + auth.getAuthorities()
                    + "' attempted to access the protected URL: "
                    + httpServletRequest.getRequestURI());
        }

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error/403");
    }

}

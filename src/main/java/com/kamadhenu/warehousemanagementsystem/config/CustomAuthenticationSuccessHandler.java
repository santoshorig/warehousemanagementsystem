package com.kamadhenu.warehousemanagementsystem.config;

import com.kamadhenu.warehousemanagementsystem.model.db.security.UserDevice;
import com.kamadhenu.warehousemanagementsystem.model.domain.user.CustomUser;
import com.kamadhenu.warehousemanagementsystem.service.db.security.UserDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * <h1>Custom Authentication Success Handler</h1>
 * <p>
 * This custom class on authentication success updates user activity
 *
 * @author Kamadhenu
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    private static final Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 60 * 24;

    private static final String LOGIN_SUCCESS_URL = "/admin/home";

    private final UserDeviceService userDeviceService;

    /**
     * Custom Authentication Success Service
     *
     * @param userDeviceService
     */
    public CustomAuthenticationSuccessHandler(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }

    /**
     * Extract IP
     *
     * @param request
     * @return
     */
    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request
                .getHeader("x-forwarded-for");
        if (clientXForwardedForIp != null) {
            clientIp = clientXForwardedForIp.split(" *, *")[0];
        } else {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    /**
     * Get device details
     *
     * @param userAgent
     * @return
     */
    private String getDeviceDetails(String userAgent) throws IOException {
        String deviceDetails = "";
        Parser parser = new Parser();
        Client client = parser.parse(userAgent);
        if (Objects.nonNull(client)) {
            deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." +
                    client.userAgent.minor + " - " + client.os.family + " " +
                    client.os.major + "." + client.os.minor;
        }

        return deviceDetails;
    }

    /**
     * Log user details
     *
     * @param authentication
     * @param request
     * @param customUser
     */
    private void logUserDetails(
            Authentication authentication, HttpServletRequest request,
            CustomUser customUser
    ) {
        try {
            UserDevice userDevice = new UserDevice();
            userDevice.setDeviceDetails(getDeviceDetails(request.getHeader("user-agent")));
            userDevice.setIp(extractIp((request)));
            userDevice.setLastLoggedIn(new Date());
            userDevice.setUser(customUser);
            this.userDeviceService.edit(userDevice);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    /**
     * On authentication success updates user activity and redirects to success url
     *
     * @param request        the http servlet request
     * @param response       the http servlet response
     * @param authentication the authentication
     * @throws IOException      the io exception that is thrown
     * @throws ServletException the servlet exception that is thrown
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        LOGGER.debug("authenticated user found {}", customUser.toString());
        // Log user details
        logUserDetails(authentication, request, customUser);
        request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        request.getSession().setAttribute("LOGIN_TIME", simpleDateFormat.format(new Date()));
        response.sendRedirect(request.getContextPath() + LOGIN_SUCCESS_URL);
        ///TODO Add logic for user activity
    }
}
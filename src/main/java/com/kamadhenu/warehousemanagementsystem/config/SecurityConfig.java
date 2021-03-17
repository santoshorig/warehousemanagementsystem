package com.kamadhenu.warehousemanagementsystem.config;

import com.kamadhenu.warehousemanagementsystem.repository.security.UserRepository;
import com.kamadhenu.warehousemanagementsystem.service.db.security.UserDeviceService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import com.kamadhenu.warehousemanagementsystem.service.domain.user.CustomUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Custom Security Configuration</h1>
 * <p>
 * This custom class configures the security system for spring boot
 *
 * @author Kamadhenu
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private static final String LOGIN_URL = "/login";

    private static final String LOGOUT_URL = "/logout";

    private static final String LOGIN_SUCCESS_URL = "/admin/home";

    private static final String LOGIN_ERROR_URL = "/login?error=true";

    private static final String LOGOUT_SUCCESS_URL = "/";

    private static final String USERNAME_PARAMETER = "email";

    private static final String PASSWORD_PARAMETER = "password";

    private static final String HOME_URL = "/admin/home/**";

    private final List<String> COMMON_USER_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/home/**",
                    "/admin/profile/**",
                    "/admin/client/index",
                    "/admin/warehouse/index",
                    "/admin/report/**"
            )
    );

    private final List<String> ADMIN_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/user/**",
                    "/admin/bank/**",
                    "/admin/bank-branch/**",
                    "/admin/business-type/**",
                    "/admin/client-constitution/**",
                    "/admin/client-mode-of-operation/**",
                    "/admin/client-type/**",
                    "/admin/billing-charge/**",
                    "/admin/commodity/**",
                    "/admin/state/**",
                    "/admin/region/**",
                    "/admin/district/**",
                    "/admin/location/**",
                    "/admin/document-purpose/**",
                    "/admin/document-type/**",
                    "/admin/employee-position/**",
                    "/admin/employee/**",
                    "/admin/highmark-risk-category/**",
                    "/admin/takeover-type/**",
                    "/admin/warehouse-type/**",
                    "/admin/tender/**",
                    "/admin/insurance/**",
                    "/admin/policy-owner/**",
                    "/admin/policy-type/**",
                    "/admin/add-on-service/**",
                    "/admin/material-owner/**",
                    "/admin/quality-parameter/**",
                    "/admin/status-of-party/**",
                    "/admin/inspection/**",
                    "/admin/inspection-option/**",
                    "/admin/reports/**",
                    "/admin/contract/lock",
                    "/admin/contract/lock-edit"
            )
    );

    private final List<String> MIS_FIELD_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/client/edit",
                    "/admin/client/edit/**",
                    "/admin/client-address/**",
                    "/admin/client-bank/**",
                    "/admin/client-document/**",
                    "/admin/client-signatory/**"
            )
    );

    private final List<String> MIS_OPS_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/warehouse/edit",
                    "/admin/warehouse/edit/**",
                    "/admin/warehouse/warehouse-owner/**",
                    "/admin/warehouse/warehouse-detail/**",
                    "/admin/warehouse/warehouse-weighbridge/**",
                    "/admin/warehouse/warehouse-manpower/**",
                    "/admin/warehouse/warehouse-document/**",
                    "/admin/warehouse/warehouse-inspection/**",
                    "/admin/warehouse/warehouse-closure/**"
            )
    );

    private final List<String> RISK_APPROVER_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/client/risk-approval",
                    "/admin/warehouse/risk-approval"
            )
    );

    private final List<String> MGMT_APPROVER_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/client/management-approval",
                    "/admin/warehouse/management-approval",
                    "/admin/warehouse-closure/management-approval"
            )
    );

    private final List<String> MANAGEMENT_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/reports/**"
            )
    );

    private final List<String> INWARD_OUTWARD_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/inward/**",
                    "/admin/outward/**"
            )
    );

    private final List<String> SR_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/sr/**"
            )
    );

    private final List<String> LR_DO_URLS = new ArrayList<>(
            Arrays.asList(
                    "/admin/lr/**",
                    "/admin/do/**"
            )
    );

    private final List<String> IGNORE_URLS = new ArrayList<>(
            Arrays.asList(
                    "/resources/**",
                    "/static/**",
                    "/custom/**",
                    "/slim/**",
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/xls/**",
                    "/lib/**"
            )
    );

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private UserDeviceService userDeviceService;

    /**
     * Configure security via custom user service
     *
     * @param auth the authentication manager
     * @throws Exception the exception that is thrown
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.debug("Configuring custom user details service");
        auth.userDetailsService(customUserService).passwordEncoder(getPasswordEncoder());
    }

    /**
     * Configure http security for various routes basis roles
     *
     * @param http the  http security manager
     * @throws Exception the exception that is thrown
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.debug("Configuring security");
        ///TODO Add logic to allow roles to be defined as constants
        http.authorizeRequests()
                .antMatchers(ADMIN_URLS.toArray(new String[ADMIN_URLS.size()])).hasRole("ADMIN")
                .antMatchers(COMMON_USER_URLS.toArray(new String[COMMON_USER_URLS.size()]))
                .hasAnyRole(
                        "IW_OW_OPS",
                        "MIS_FIELD",
                        "MIS_OPS",
                        "SR_MAKER",
                        "OPS_REVIEWER",
                        "SR_CHECKER",
                        "MIS_SR_WR_DO_OW",
                        "DO_CHECKER",
                        "BD_REVIEWER",
                        "OPS_BH_APPROVER",
                        "SR_SIGNATORY",
                        "RISK_USER",
                        "RISK_APPROVER",
                        "MGMT_APPROVER"
                )
                .antMatchers(MIS_FIELD_URLS.toArray(new String[MIS_FIELD_URLS.size()]))
                .hasAnyRole("MIS_FIELD", "BD_REVIEWER")
                .antMatchers(MIS_OPS_URLS.toArray(new String[MIS_OPS_URLS.size()]))
                .hasAnyRole("MIS_OPS", "OPS_REVIEWER", "IW_OW_OPS")
                .antMatchers(RISK_APPROVER_URLS.toArray(new String[RISK_APPROVER_URLS.size()]))
                .hasAnyRole("RISK_USER", "RISK_APPROVER")
                .antMatchers(MGMT_APPROVER_URLS.toArray(new String[MGMT_APPROVER_URLS.size()])).hasRole("MGMT_APPROVER")
                .antMatchers(MANAGEMENT_URLS.toArray(new String[MANAGEMENT_URLS.size()]))
                .hasAnyRole("RISK_USER", "RISK_APPROVER", "MGMT_APPROVER")
                .antMatchers(LR_DO_URLS.toArray(new String[LR_DO_URLS.size()]))
                .hasAnyRole("MIS_SR_WR_DO_OW", "DO_CHECKER")
                .antMatchers(SR_URLS.toArray(new String[SR_URLS.size()]))
                .hasAnyRole("SR_MAKER", "SR_CHECKER", "SR_SIGNATORY")
                .antMatchers(INWARD_OUTWARD_URLS.toArray(new String[INWARD_OUTWARD_URLS.size()]))
                .hasAnyRole("IW_OW_OPS", "MIS_OPS")
                .antMatchers(LOGOUT_SUCCESS_URL, LOGIN_URL, HOME_URL).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable().formLogin()
                .loginPage(LOGIN_URL)
                .failureUrl(LOGIN_ERROR_URL)
                .defaultSuccessUrl(LOGIN_SUCCESS_URL)
                .successHandler(customAuthenticationSuccessHandler())
                .usernameParameter(USERNAME_PARAMETER)
                .passwordParameter(PASSWORD_PARAMETER)
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
                .deleteCookies("JSESSIONID")
                .and().exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler());
    }

    /**
     * Configure ignores for allowing access without security session
     *
     * @param web the web security manager
     * @throws Exception the exception that is thrown
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        LOGGER.debug("Configuring matchers for security");
        web.ignoring().antMatchers(IGNORE_URLS.toArray(new String[IGNORE_URLS.size()]));
    }

    /**
     * Custom password encoder
     *
     * @return the password encoder
     */
    private PasswordEncoder getPasswordEncoder() {
        LOGGER.debug("Configuring password encoder");
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encodedPassword = helperService.getMD5(charSequence.toString());
                return encodedPassword.equals(s);
            }
        };
    }

    /**
     * Login success handler bean
     *
     * @return the custom authentication handler
     * @throws Exception the exception that is thrown
     */
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() throws Exception {
        LOGGER.debug("Configuring custom authentication handler");
        return new CustomAuthenticationSuccessHandler(userDeviceService);
    }

    /**
     * Custom access denied handler bean
     *
     * @return the custom access denied handler bean
     * @throws Exception the exception that is thrown
     */
    public AccessDeniedHandler customAccessDeniedHandler() throws Exception {
        LOGGER.debug("Configuring custom access denied handler");
        return new CustomAccessDeniedHandler();
    }

}

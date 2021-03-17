package com.kamadhenu.warehousemanagementsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import java.util.Locale;

/**
 * <h1>Custom Application Configuration</h1>
 * <p>
 * This class provides a custom application configuration for locale resolver and locale change
 *
 * @author Kamadhenu
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    /**
     * Sets the spring dialect security bean
     *
     * @return
     */
    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    /**
     * Sets the default locale to US
     *
     * @return the locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        LOGGER.debug("Setting locale resolver");
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    /**
     * Sets the new locale based on lang parameter in the url
     *
     * @return the interceptor to identify the local change
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LOGGER.debug("Setting locale change");
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Adds an interceptor to listen for locale change requests using the lang param in the url
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.debug("Adding interceptors");
        registry.addInterceptor(localeChangeInterceptor());
    }
}
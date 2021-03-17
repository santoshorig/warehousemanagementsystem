package com.kamadhenu.warehousemanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <h1>Error controller</h1>
 * <p>
 * This error controller class provides the various error actions
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    /**
     * Http 403 action
     *
     * @return the model and view
     */
    @GetMapping("/403")
    public ModelAndView error403() {
        LOGGER.error("HTTP 403 encountered");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/403");
        return modelAndView;
    }

    /**
     * Http 404 action
     *
     * @return the model and view
     */
    @GetMapping("/404")
    public ModelAndView error404() {
        LOGGER.error("HTTP 404 encountered");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/404");
        return modelAndView;
    }

    /**
     * Http 500 action
     *
     * @return the model and view
     */
    @GetMapping("/500")
    public ModelAndView error500() {
        LOGGER.error("HTTP 500 encountered");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", null);
        modelAndView.setViewName("error/500");
        return modelAndView;
    }

}

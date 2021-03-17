package com.kamadhenu.warehousemanagementsystem.controller;

import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * <h1>Index controller</h1>
 * <p>
 * This index controller class provides the entry point to the application along with the login form
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private HelperService helperService;

    /**
     * Login action
     *
     * @return the model and view
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", defaultValue = "false", required = false) Boolean error) {
        LOGGER.info("Login action reached");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", error);
        modelAndView.setViewName("index/login");
        return modelAndView;
    }

}

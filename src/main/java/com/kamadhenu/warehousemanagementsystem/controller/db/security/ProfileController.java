package com.kamadhenu.warehousemanagementsystem.controller.db.security;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeeService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.security.UserService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

/**
 * <h1>Profile controller</h1>
 * <p>
 * This profile controller class provides the edit action for user profile
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/profile")
public class ProfileController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Profile edit action called");
        ModelAndView modelAndView = new ModelAndView();
        User model = new User();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Profile edit action called on existing model with id {}", identifier);
            Optional<User> user = userService.get(identifier);
            if (user.isPresent()) {
                model = user.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/security/profile/edit");
        LOGGER.info("Profile edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") User user,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Profile save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/security/profile/edit");
        if (!result.hasErrors()) {
            try {
                Optional<User> existingUser = userService.get(user.getId());
                if (existingUser.isPresent()) {
                    User model = existingUser.get();
                    model.setPassword(user.getPassword());
                    userService.edit(model);
                    addFlashMessage(redirectAttributes, true);
                    LOGGER.info("Profile save action successful");
                    modelAndView.setViewName("redirect:/logout");
                } else {
                    throw new Exception("User doesn't exist");
                }
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Profile save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

}

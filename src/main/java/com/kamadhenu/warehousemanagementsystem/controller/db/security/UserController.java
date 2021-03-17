package com.kamadhenu.warehousemanagementsystem.controller.db.security;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
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
import java.util.List;
import java.util.Optional;

/**
 * <h1>User controller</h1>
 * <p>
 * This user controller class provides the CRUD actions for users
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("User index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userService.getAll();
        modelAndView.addObject("models", userList);
        modelAndView.setViewName("admin/security/user/index");
        LOGGER.info("User index action returned {} models", userList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("User edit action called");
        ModelAndView modelAndView = new ModelAndView();
        User model = new User();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("User edit action called on existing model with id {}", identifier);
            Optional<User> user = userService.get(identifier);
            if (user.isPresent()) {
                model = user.get();
            }
        }

        List<Employee> employeeList = employeeService.getAll();
        List<String> roleList = constantService.getROLES();
        List<Location> locationList = locationService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("employees", employeeList);
        modelAndView.addObject("roles", roleList);
        modelAndView.addObject("locations", locationList);
        modelAndView.setViewName("admin/security/user/edit");
        LOGGER.info("User edit action returned {} model", model);
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
        LOGGER.info("User save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/security/user/edit");
        if (!result.hasErrors()) {
            try {
                userService.edit(user);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("User save action successful");
                modelAndView.setViewName("redirect:/admin/user/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("User save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("User delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            userService.delete(id);
            LOGGER.info("User delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/user/index");
        return modelAndView;
    }


}

package com.kamadhenu.warehousemanagementsystem.controller.db.hr;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.EmployeePosition;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeePositionService;
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
 * <h1>EmployeePosition controller</h1>
 * <p>
 * This employee controller class provides the CRUD actions for employees
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/employee-position")
public class EmployeePositionController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePositionController.class);

    @Autowired
    private EmployeePositionService employeePositionService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("EmployeePosition index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<EmployeePosition> employeePositionList = employeePositionService.getAll();
        modelAndView.addObject("models", employeePositionList);
        modelAndView.setViewName("admin/hr/employee-position/index");
        LOGGER.info("EmployeePosition index action returned {} models", employeePositionList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("EmployeePosition edit action called");
        ModelAndView modelAndView = new ModelAndView();
        EmployeePosition model = new EmployeePosition();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("EmployeePosition edit action called on existing model with id {}", identifier);
            Optional<EmployeePosition> employee = employeePositionService.get(identifier);
            if (employee.isPresent()) {
                model = employee.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/hr/employee-position/edit");
        LOGGER.info("Bank edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") EmployeePosition employeePosition,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("EmployeePosition save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/hr/employee-position/edit");
        if (!result.hasErrors()) {
            try {
                employeePositionService.edit(employeePosition);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("EmployeePosition save action successful");
                modelAndView.setViewName("redirect:/admin/employee-position/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
            }
        } else {
            LOGGER.error("EmployeePosition save action had errors {}", result.getAllErrors());
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
        LOGGER.info("EmployeePosition delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            employeePositionService.delete(id);
            LOGGER.info("EmployeePosition delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/employee-position/index");
        return modelAndView;
    }

}

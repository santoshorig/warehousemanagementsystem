package com.kamadhenu.warehousemanagementsystem.controller.db.hr;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.hr.EmployeeDocumentForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.EmployeePosition;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeePositionService;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeeService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Employee controller</h1>
 * <p>
 * This employee controller class provides the CRUD actions for employees
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/employee")
public class EmployeeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeePositionService employeePositionService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private DocumentService documentService;

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
        LOGGER.info("Employee index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Employee> employeeList = employeeService.getAll();
        modelAndView.addObject("models", employeeList);
        modelAndView.setViewName("admin/hr/employee/index");
        LOGGER.info("Employee index action returned {} models", employeeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Employee edit action called");
        ModelAndView modelAndView = new ModelAndView();
        EmployeeDocumentForm model = new EmployeeDocumentForm();
        if (id.isPresent()) {
            Employee employeeModel = new Employee();
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Employee edit action called on existing model with id {}", identifier);
            Optional<Employee> employee = employeeService.get(identifier);
            if (employee.isPresent()) {
                employeeModel = employee.get();
            }
            model.setEmployee(employeeModel);
        }

        List<Employee> employeeList = employeeService.getAll();
        List<EmployeePosition> employeePositionList = employeePositionService.getAll();
        List<Location> locationList = locationService.getAll();
        List<BusinessType> businessTypeList = businessTypeService.getAll();
        List<String> titleList = constantService.getTITLES();
        modelAndView.addObject("model", model);
        modelAndView.addObject("employees", employeeList);
        modelAndView.addObject("employeePositions", employeePositionList);
        modelAndView.addObject("locations", locationList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.addObject("titles", titleList);
        modelAndView.setViewName("admin/hr/employee/edit");
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
            @Valid @ModelAttribute("model") EmployeeDocumentForm employeeDocumentForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Employee save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/hr/employee/edit");
        if (!result.hasErrors()) {
            try {
                if (employeeDocumentForm.getUpload().isEmpty()) {
                    if (employeeDocumentForm.getEmployee().getId() != null && employeeDocumentForm.getEmployee()
                            .getSignature() != null) {
                        employeeDocumentForm.getEmployee()
                                .setSignature(employeeDocumentForm.getEmployee().getSignature());
                    }
                } else {
                    MultipartFile file = employeeDocumentForm.getUpload();
                    Document document = documentService.store(file);
                    employeeDocumentForm.getEmployee().setSignature(document);
                }
                employeeService.edit(employeeDocumentForm.getEmployee());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Employee save action successful");
                modelAndView.setViewName("redirect:/admin/employee/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Employee save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Employee delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            employeeService.delete(id);
            LOGGER.info("Employee delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/employee/index");
        return modelAndView;
    }

}

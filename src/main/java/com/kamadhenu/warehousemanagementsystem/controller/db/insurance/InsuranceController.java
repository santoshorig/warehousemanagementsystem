package com.kamadhenu.warehousemanagementsystem.controller.db.insurance;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyOwner;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyType;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.insurance.InsuranceService;
import com.kamadhenu.warehousemanagementsystem.service.db.insurance.PolicyOwnerService;
import com.kamadhenu.warehousemanagementsystem.service.db.insurance.PolicyTypeService;
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
 * <h1>Insurance controller</h1>
 * <p>
 * This insurance controller class provides the CRUD actions for insurances
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/insurance")
public class InsuranceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceController.class);

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private PolicyTypeService policyTypeService;

    @Autowired
    private PolicyOwnerService policyOwnerService;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Insurance index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Insurance> insuranceList = insuranceService.getAll();
        modelAndView.addObject("models", insuranceList);
        modelAndView.setViewName("admin/insurance/insurance/index");
        LOGGER.info("Insurance index action returned {} models", insuranceList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Insurance edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Insurance model = new Insurance();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Insurance edit action called on existing model with id {}", identifier);
            Optional<Insurance> insurance = insuranceService.get(identifier);
            if (insurance.isPresent()) {
                model = insurance.get();
            }
        }

        List<PolicyType> policyTypeList = policyTypeService.getAll();
        List<PolicyOwner> policyOwnerList = policyOwnerService.getAll();
        List<BusinessType> businessTypeList = businessTypeService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("policyTypes", policyTypeList);
        modelAndView.addObject("policyOwners", policyOwnerList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.setViewName("admin/insurance/insurance/edit");
        LOGGER.info("Insurance edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Insurance insurance,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Insurance save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/insurance/insurance/edit");
        if (!result.hasErrors()) {
            try {
                insuranceService.edit(insurance);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Insurance save action successful");
                modelAndView.setViewName("redirect:/admin/insurance/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Insurance save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Insurance delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            insuranceService.delete(id);
            LOGGER.info("Insurance delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/insurance/index");
        return modelAndView;
    }

}

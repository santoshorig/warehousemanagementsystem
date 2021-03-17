package com.kamadhenu.warehousemanagementsystem.controller.db.insurance;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.PolicyOwner;
import com.kamadhenu.warehousemanagementsystem.service.db.insurance.PolicyOwnerService;
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
 * <h1>PolicyOwner controller</h1>
 * <p>
 * This policy owner controller class provides the CRUD actions for policy owners
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/policy-owner")
public class PolicyOwnerController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyOwnerController.class);

    @Autowired
    private PolicyOwnerService policyOwnerService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("PolicyOwner index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<PolicyOwner> policyOwnerList = policyOwnerService.getAll();
        modelAndView.addObject("models", policyOwnerList);
        modelAndView.setViewName("admin/insurance/policy-owner/index");
        LOGGER.info("PolicyOwner index action returned {} models", policyOwnerList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("PolicyOwner edit action called");
        ModelAndView modelAndView = new ModelAndView();
        PolicyOwner model = new PolicyOwner();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("PolicyOwner edit action called on existing model with id {}", identifier);
            Optional<PolicyOwner> policyOwner = policyOwnerService.get(identifier);
            if (policyOwner.isPresent()) {
                model = policyOwner.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/insurance/policy-owner/edit");
        LOGGER.info("PolicyOwner edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") PolicyOwner policyOwner,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("PolicyOwner save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/insurance/policy-owner/edit");
        if (!result.hasErrors()) {
            try {
                policyOwnerService.edit(policyOwner);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("PolicyOwner save action successful");
                modelAndView.setViewName("redirect:/admin/policy-owner/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("PolicyOwner save action had errors {}", result.getAllErrors());
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
        LOGGER.info("PolicyOwner delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            policyOwnerService.delete(id);
            LOGGER.info("PolicyOwner delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/policy-owner/index");
        return modelAndView;
    }

}

package com.kamadhenu.warehousemanagementsystem.controller.db.general;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.general.UtilizationFactor;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.CommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.UtilizationFactorService;
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
 * <h1>UtilizationFactor controller</h1>
 * <p>
 * This utilization factor controller class provides the CRUD actions for utilization factors
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/utilization-factor")
public class UtilizationFactorController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilizationFactorController.class);

    @Autowired
    private UtilizationFactorService utilizationFactorService;

    @Autowired
    private CommodityService commodityService;

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
        LOGGER.info("UtilizationFactor index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<UtilizationFactor> utilizationFactorList = utilizationFactorService.getAll();
        modelAndView.addObject("models", utilizationFactorList);
        modelAndView.setViewName("admin/general/utilization-factor/index");
        LOGGER.info("UtilizationFactor index action returned {} models", utilizationFactorList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("UtilizationFactor edit action called");
        ModelAndView modelAndView = new ModelAndView();
        UtilizationFactor model = new UtilizationFactor();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("UtilizationFactor edit action called on existing model with id {}", identifier);
            Optional<UtilizationFactor> utilizationFactor = utilizationFactorService.get(identifier);
            if (utilizationFactor.isPresent()) {
                model = utilizationFactor.get();
            }
        }

        List<Commodity> commodityList = commodityService.getAll();
        List<BusinessType> businessTypeList = businessTypeService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("commodities", commodityList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.setViewName("admin/general/utilization-factor/edit");
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
            @Valid @ModelAttribute("model") UtilizationFactor utilizationFactor,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("UtilizationFactor save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/general/utilization-factor/edit");
        if (!result.hasErrors()) {
            try {
                utilizationFactorService.edit(utilizationFactor);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("UtilizationFactor save action successful");
                modelAndView.setViewName("redirect:/admin/utilization-factor/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("UtilizationFactor save action had errors {}", result.getAllErrors());
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
        LOGGER.info("UtilizationFactor delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            utilizationFactorService.delete(id);
            LOGGER.info("UtilizationFactor delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/utilization-factor/index");
        return modelAndView;
    }

}

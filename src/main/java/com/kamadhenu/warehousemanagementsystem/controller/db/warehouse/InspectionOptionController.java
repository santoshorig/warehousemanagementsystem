package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inspection;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionOption;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InspectionOptionService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InspectionService;
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
 * <h1>InspectionOption controller</h1>
 * <p>
 * This inspection option controller class provides the CRUD actions for inspection options
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inspection-option")
public class InspectionOptionController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionOptionController.class);

    @Autowired
    private InspectionOptionService inspectionOptionService;

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("InspectionOption index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<InspectionOption> inspectionOptionList = inspectionOptionService.getAll();
        modelAndView.addObject("models", inspectionOptionList);
        modelAndView.setViewName("admin/warehouse/inspection-option/index");
        LOGGER.info("InspectionOption index action returned {} models", inspectionOptionList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("InspectionOption edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InspectionOption model = new InspectionOption();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("InspectionOption edit action called on existing model with id {}", identifier);
            Optional<InspectionOption> inspectionOption = inspectionOptionService.get(identifier);
            if (inspectionOption.isPresent()) {
                model = inspectionOption.get();
            }
        }

        List<Inspection> inspectionList = inspectionService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("inspections", inspectionList);
        modelAndView.setViewName("admin/warehouse/inspection-option/edit");
        LOGGER.info("InspectionOption edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InspectionOption inspectionOption,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InspectionOption save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inspection-option/edit");
        if (!result.hasErrors()) {
            try {
                inspectionOptionService.edit(inspectionOption);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("InspectionOption save action successful");
                modelAndView.setViewName("redirect:/admin/inspection-option/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("InspectionOption save action had errors {}", result.getAllErrors());
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
        LOGGER.info("InspectionOption delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            inspectionOptionService.delete(id);
            LOGGER.info("InspectionOption delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inspection-option/index");
        return modelAndView;
    }

}

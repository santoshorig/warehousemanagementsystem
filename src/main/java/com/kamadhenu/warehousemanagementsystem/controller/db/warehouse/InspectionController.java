package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inspection;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InspectionService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InspectionTypeService;
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
 * <h1>Inspection controller</h1>
 * <p>
 * This inspection controller class provides the CRUD actions for inspections
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inspection")
public class InspectionController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionController.class);

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private InspectionTypeService inspectionTypeService;

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
        LOGGER.info("Inspection index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Inspection> inspectionList = inspectionService.getAll();
        modelAndView.addObject("models", inspectionList);
        modelAndView.setViewName("admin/warehouse/inspection/index");
        LOGGER.info("Inspection index action returned {} models", inspectionList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Inspection edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Inspection model = new Inspection();
        Double allowedWeight = new Double(100);
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Inspection edit action called on existing model with id {}", identifier);
            Optional<Inspection> inspection = inspectionService.get(identifier);
            if (inspection.isPresent()) {
                model = inspection.get();
            }
            List<Inspection> inspectionList = inspectionService.getAll();
            for (Inspection inspectionModel : inspectionList) {
                allowedWeight = allowedWeight - inspectionModel.getWeight();
            }
        }

        List<InspectionType> inspectionTypeList = inspectionTypeService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("allowedWeight", allowedWeight);
        modelAndView.addObject("inspectionTypes", inspectionTypeList);
        modelAndView.setViewName("admin/warehouse/inspection/edit");
        LOGGER.info("Inspection edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Inspection inspection,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Inspection save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inspection/edit");
        if (!result.hasErrors()) {
            try {
                inspectionService.edit(inspection);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Inspection save action successful");
                modelAndView.setViewName("redirect:/admin/inspection/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Inspection save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Inspection delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            inspectionService.delete(id);
            LOGGER.info("Inspection delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inspection/index");
        return modelAndView;
    }

}

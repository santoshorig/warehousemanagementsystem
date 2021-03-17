package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InspectionService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InspectionTypeService;
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
 * <h1>InspectionType controller</h1>
 * <p>
 * This inspection type controller class provides the CRUD actions for inspection types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inspection-type")
public class InspectionTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionTypeController.class);

    @Autowired
    private InspectionTypeService inspectionTypeService;

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
        LOGGER.info("InspectionType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<InspectionType> inspectionTypeList = inspectionTypeService.getAll();
        modelAndView.addObject("models", inspectionTypeList);
        modelAndView.setViewName("admin/warehouse/inspection-type/index");
        LOGGER.info("InspectionType index action returned {} models", inspectionTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("InspectionType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InspectionType model = new InspectionType();
        Double allowedWeight = new Double(100);
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("InspectionType edit action called on existing model with id {}", identifier);
            Optional<InspectionType> inspectionType = inspectionTypeService.get(identifier);
            if (inspectionType.isPresent()) {
                model = inspectionType.get();
            }
            List<InspectionType> inspectionTypeList = inspectionTypeService.getAll();
            for (InspectionType inspectionTypeModel : inspectionTypeList) {
                allowedWeight = allowedWeight - inspectionTypeModel.getWeight();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.addObject("allowedWeight", allowedWeight);
        modelAndView.setViewName("admin/warehouse/inspection-type/edit");
        LOGGER.info("InspectionType edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InspectionType inspectionType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InspectionType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inspection-type/edit");
        if (!result.hasErrors()) {
            try {
                inspectionTypeService.edit(inspectionType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("InspectionType save action successful");
                modelAndView.setViewName("redirect:/admin/inspection-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("InspectionType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("InspectionType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            inspectionTypeService.delete(id);
            LOGGER.info("InspectionType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inspection-type/index");
        return modelAndView;
    }

}

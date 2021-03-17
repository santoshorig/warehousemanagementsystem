package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.AddOnService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.AddOnServiceService;
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
import java.util.Map;
import java.util.Optional;

/**
 * <h1>AddOnService controller</h1>
 * <p>
 * This add on service controller class provides the CRUD actions for add on services
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/add-on-service")
public class AddOnServiceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddOnServiceController.class);

    @Autowired
    private AddOnServiceService addOnServiceService;

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
        LOGGER.info("AddOnService index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<AddOnService> addOnServiceList = addOnServiceService.getAll();
        modelAndView.addObject("models", addOnServiceList);
        modelAndView.setViewName("admin/warehouse/add-on-service/index");
        LOGGER.info("AddOnService index action returned {} models", addOnServiceList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("AddOnService edit action called");
        ModelAndView modelAndView = new ModelAndView();
        AddOnService model = new AddOnService();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("AddOnService edit action called on existing model with id {}", identifier);
            Optional<AddOnService> addOnService = addOnServiceService.get(identifier);
            if (addOnService.isPresent()) {
                model = addOnService.get();
            }
        }

        Map<String, String> unitOfMeasuresMap = constantService.getUNIT_OF_MEASURE();
        modelAndView.addObject("model", model);
        modelAndView.addObject("unitOfMeasures", unitOfMeasuresMap);
        modelAndView.setViewName("admin/warehouse/add-on-service/edit");
        LOGGER.info("AddOnService edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") AddOnService addOnService,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("AddOnService save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/add-on-service/edit");
        if (!result.hasErrors()) {
            try {
                addOnServiceService.edit(addOnService);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("AddOnService save action successful");
                modelAndView.setViewName("redirect:/admin/add-on-service/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("AddOnService save action had errors {}", result.getAllErrors());
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
        LOGGER.info("AddOnService delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            addOnServiceService.delete(id);
            LOGGER.info("AddOnService delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/add-on-service/index");
        return modelAndView;
    }

}

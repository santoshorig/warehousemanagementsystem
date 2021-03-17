package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.QualityParameterService;
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
 * <h1>QualityParameter controller</h1>
 * <p>
 * This quality parameter controller class provides the CRUD actions for quality parameters
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/quality-parameter")
public class QualityParameterController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QualityParameterController.class);

    @Autowired
    private QualityParameterService qualityParameterService;

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
        LOGGER.info("QualityParameter index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<QualityParameter> qualityParameterList = qualityParameterService.getAll();
        modelAndView.addObject("models", qualityParameterList);
        modelAndView.setViewName("admin/warehouse/quality-parameter/index");
        LOGGER.info("QualityParameter index action returned {} models", qualityParameterList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("QualityParameter edit action called");
        ModelAndView modelAndView = new ModelAndView();
        QualityParameter model = new QualityParameter();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("QualityParameter edit action called on existing model with id {}", identifier);
            Optional<QualityParameter> qualityParameter = qualityParameterService.get(identifier);
            if (qualityParameter.isPresent()) {
                model = qualityParameter.get();
            }
        }

        Map<String, String> unitOfMeasureMap = constantService.getUNIT_OF_MEAURES_QUALITY_PARAMETER();
        modelAndView.addObject("model", model);
        modelAndView.addObject("unitOfMeasures", unitOfMeasureMap);
        modelAndView.setViewName("admin/warehouse/quality-parameter/edit");
        LOGGER.info("QualityParameter edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") QualityParameter qualityParameter,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("QualityParameter save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/quality-parameter/edit");
        if (!result.hasErrors()) {
            try {
                qualityParameterService.edit(qualityParameter);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("QualityParameter save action successful");
                modelAndView.setViewName("redirect:/admin/quality-parameter/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("QualityParameter save action had errors {}", result.getAllErrors());
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
        LOGGER.info("QualityParameter delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            qualityParameterService.delete(id);
            LOGGER.info("QualityParameter delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/quality-parameter/index");
        return modelAndView;
    }

}

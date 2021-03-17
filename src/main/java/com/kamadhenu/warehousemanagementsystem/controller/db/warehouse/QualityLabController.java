package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityLab;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.QualityLabService;
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
 * <h1>QualityLab controller</h1>
 * <p>
 * This quality lab controller class provides the CRUD actions for quality labs
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/quality-lab")
public class QualityLabController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QualityLabController.class);

    @Autowired
    private QualityLabService qualityLabService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("QualityLab index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<QualityLab> qualityLabList = qualityLabService.getAll();
        modelAndView.addObject("models", qualityLabList);
        modelAndView.setViewName("admin/warehouse/quality-lab/index");
        LOGGER.info("QualityLab index action returned {} models", qualityLabList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("QualityLab edit action called");
        ModelAndView modelAndView = new ModelAndView();
        QualityLab model = new QualityLab();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("QualityLab edit action called on existing model with id {}", identifier);
            Optional<QualityLab> qualityLab = qualityLabService.get(identifier);
            if (qualityLab.isPresent()) {
                model = qualityLab.get();
            }
        }

        List<Location> locationList = locationService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("locations", locationList);
        modelAndView.setViewName("admin/warehouse/quality-lab/edit");
        LOGGER.info("QualityLab edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") QualityLab qualityLab,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("QualityLab save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/quality-lab/edit");
        if (!result.hasErrors()) {
            try {
                qualityLabService.edit(qualityLab);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("QualityLab save action successful");
                modelAndView.setViewName("redirect:/admin/quality-lab/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("QualityLab save action had errors {}", result.getAllErrors());
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
        LOGGER.info("QualityLab delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            qualityLabService.delete(id);
            LOGGER.info("QualityLab delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/quality-lab/index");
        return modelAndView;
    }

}

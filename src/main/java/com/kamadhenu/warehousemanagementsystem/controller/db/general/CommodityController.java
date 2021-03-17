package com.kamadhenu.warehousemanagementsystem.controller.db.general;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.service.db.general.CommodityService;
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
 * <h1>Commodity controller</h1>
 * <p>
 * This commodity controller class provides the CRUD actions for commodities
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/commodity")
public class CommodityController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    private CommodityService commodityService;

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
        LOGGER.info("Commodity index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Commodity> commodityList = commodityService.getAll();
        modelAndView.addObject("models", commodityList);
        modelAndView.setViewName("admin/general/commodity/index");
        LOGGER.info("Commodity index action returned {} models", commodityList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Commodity edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Commodity model = new Commodity();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Commodity edit action called on existing model with id {}", identifier);
            Optional<Commodity> commodity = commodityService.get(identifier);
            if (commodity.isPresent()) {
                model = commodity.get();
            }
        }

        Map<String, String> unitOfMeasuresMap = constantService.getUNIT_OF_MEASURE();
        Map<String, String> storageInMap = constantService.getSTORAGE_IN();
        modelAndView.addObject("model", model);
        modelAndView.addObject("unitOfMeasures", unitOfMeasuresMap);
        modelAndView.addObject("storageIn", storageInMap);
        modelAndView.setViewName("admin/general/commodity/edit");
        LOGGER.info("Commodity edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Commodity commodity,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Commodity save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/general/commodity/edit");
        if (!result.hasErrors()) {
            try {
                commodityService.edit(commodity);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Commodity save action successful");
                modelAndView.setViewName("redirect:/admin/commodity/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Commodity save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Commodity delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            commodityService.delete(id);
            LOGGER.info("Commodity delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/commodity/index");
        return modelAndView;
    }

}

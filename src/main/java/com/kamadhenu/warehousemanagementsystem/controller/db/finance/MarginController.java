package com.kamadhenu.warehousemanagementsystem.controller.db.finance;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.finance.FinanceEntity;
import com.kamadhenu.warehousemanagementsystem.model.db.finance.Margin;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.finance.FinanceEntityService;
import com.kamadhenu.warehousemanagementsystem.service.db.finance.MarginService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.CommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
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
 * <h1>Margin controller</h1>
 * <p>
 * This margin controller class provides the CRUD actions for margins
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/margin")
public class MarginController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarginController.class);

    @Autowired
    private MarginService marginService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private FinanceEntityService financeEntityService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Margin index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Margin> marginList = marginService.getAll();
        modelAndView.addObject("models", marginList);
        modelAndView.setViewName("admin/finance/margin/index");
        LOGGER.info("Margin index action returned {} models", marginList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Margin edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Margin model = new Margin();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Margin edit action called on existing model with id {}", identifier);
            Optional<Margin> margin = marginService.get(identifier);
            if (margin.isPresent()) {
                model = margin.get();
            }
        }

        List<Commodity> commodityList = commodityService.getAll();
        List<Location> locationList = locationService.getAll();
        List<FinanceEntity> financeEntityList = financeEntityService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("commodities", commodityList);
        modelAndView.addObject("locations", locationList);
        modelAndView.addObject("financeEntities", financeEntityList);
        modelAndView.setViewName("admin/finance/margin/edit");
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
            @Valid @ModelAttribute("model") Margin margin,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Margin save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/finance/margin/edit");
        if (!result.hasErrors()) {
            try {
                marginService.edit(margin);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Margin save action successful");
                modelAndView.setViewName("redirect:/admin/margin/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Margin save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Margin delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            marginService.delete(id);
            LOGGER.info("Margin delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/margin/index");
        return modelAndView;
    }

}

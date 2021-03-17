package com.kamadhenu.warehousemanagementsystem.controller.db.finance;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.finance.SpotPrice;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.finance.SpotPriceService;
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
 * <h1>SpotPrice controller</h1>
 * <p>
 * This spot price controller class provides the CRUD actions for spot prices
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/spot-price")
public class SpotPriceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotPriceController.class);

    @Autowired
    private SpotPriceService spotPriceService;

    @Autowired
    private CommodityService commodityService;

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
        LOGGER.info("SpotPrice index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<SpotPrice> spotPriceList = spotPriceService.getAll();
        modelAndView.addObject("models", spotPriceList);
        modelAndView.setViewName("admin/finance/spot-price/index");
        LOGGER.info("SpotPrice index action returned {} models", spotPriceList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("SpotPrice edit action called");
        ModelAndView modelAndView = new ModelAndView();
        SpotPrice model = new SpotPrice();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("SpotPrice edit action called on existing model with id {}", identifier);
            Optional<SpotPrice> spotPrice = spotPriceService.get(identifier);
            if (spotPrice.isPresent()) {
                model = spotPrice.get();
            }
        }

        List<Commodity> commodityList = commodityService.getAll();
        List<Location> locationList = locationService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("commodities", commodityList);
        modelAndView.addObject("locations", locationList);
        modelAndView.setViewName("admin/finance/spot-price/edit");
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
            @Valid @ModelAttribute("model") SpotPrice spotPrice,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("SpotPrice save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/finance/spot-price/edit");
        if (!result.hasErrors()) {
            try {
                spotPriceService.edit(spotPrice);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("SpotPrice save action successful");
                modelAndView.setViewName("redirect:/admin/spot-price/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("SpotPrice save action had errors {}", result.getAllErrors());
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
        LOGGER.info("SpotPrice delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            spotPriceService.delete(id);
            LOGGER.info("SpotPrice delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/spot-price/index");
        return modelAndView;
    }

}

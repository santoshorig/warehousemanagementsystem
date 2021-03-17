package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Asset;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.AssetService;
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
 * <h1>Asset controller</h1>
 * <p>
 * This asset controller class provides the CRUD actions for assets
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/asset")
public class AssetController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    @Autowired
    private AssetService assetService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Asset index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Asset> assetList = assetService.getAll();
        modelAndView.addObject("models", assetList);
        modelAndView.setViewName("admin/warehouse/asset/index");
        LOGGER.info("Asset index action returned {} models", assetList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Asset edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Asset model = new Asset();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Asset edit action called on existing model with id {}", identifier);
            Optional<Asset> asset = assetService.get(identifier);
            if (asset.isPresent()) {
                model = asset.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/asset/edit");
        LOGGER.info("Asset edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Asset asset,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Asset save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/asset/edit");
        if (!result.hasErrors()) {
            try {
                assetService.edit(asset);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Asset save action successful");
                modelAndView.setViewName("redirect:/admin/asset/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Asset save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Asset delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            assetService.delete(id);
            LOGGER.info("Asset delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/asset/index");
        return modelAndView;
    }

}

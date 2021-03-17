package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.location.District;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.HeadOffice;
import com.kamadhenu.warehousemanagementsystem.service.db.location.DistrictService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.HeadOfficeService;
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
 * <h1>HeadOffice controller</h1>
 * <p>
 * This head office controller class provides the CRUD actions for head offices
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/head-office")
public class HeadOfficeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeadOfficeController.class);

    @Autowired
    private HeadOfficeService headOfficeService;

    @Autowired
    private DistrictService districtService;

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
        LOGGER.info("HeadOffice index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<HeadOffice> headOfficeList = headOfficeService.getAll();
        modelAndView.addObject("models", headOfficeList);
        modelAndView.setViewName("admin/warehouse/head-office/index");
        LOGGER.info("HeadOffice index action returned {} models", headOfficeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("HeadOffice edit action called");
        ModelAndView modelAndView = new ModelAndView();
        HeadOffice model = new HeadOffice();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("HeadOffice edit action called on existing model with id {}", identifier);
            Optional<HeadOffice> headOffice = headOfficeService.get(identifier);
            if (headOffice.isPresent()) {
                model = headOffice.get();
            }
        }

        List<District> districtList = districtService.getAll();
        List<String> titleList = constantService.getTITLES();
        modelAndView.addObject("model", model);
        modelAndView.addObject("districts", districtList);
        modelAndView.addObject("titles", titleList);
        modelAndView.setViewName("admin/warehouse/head-office/edit");
        LOGGER.info("HeadOffice edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") HeadOffice headOffice,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("HeadOffice save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/head-office/edit");
        if (!result.hasErrors()) {
            try {
                headOfficeService.edit(headOffice);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("HeadOffice save action successful");
                modelAndView.setViewName("redirect:/admin/head-office/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("HeadOffice save action had errors {}", result.getAllErrors());
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
        LOGGER.info("HeadOffice delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            headOfficeService.delete(id);
            LOGGER.info("HeadOffice delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/head-office/index");
        return modelAndView;
    }

}

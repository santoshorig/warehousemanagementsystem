package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.AddOnService;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderAddOnService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.AddOnServiceService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TenderAddOnServiceService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TenderService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>TenderAddOnService controller</h1>
 * <p>
 * This tender add on service controller class provides the CRUD actions for tender add on services
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/tender-add-on-service")
public class TenderAddOnServiceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderAddOnServiceController.class);

    @Autowired
    private TenderAddOnServiceService tenderAddOnServiceService;

    @Autowired
    private TenderService tenderService;

    @Autowired
    private AddOnServiceService addOnServiceService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/tender/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("TenderAddOnService index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<TenderAddOnService> tenderAddOnServiceList = new ArrayList<>();
        Optional<Tender> tender = tenderService.get(id);
        if (tender.isPresent()) {
            Tender tenderModel = tender.get();
            tenderAddOnServiceList = tenderAddOnServiceService.getByTender(tenderModel);
            modelAndView.addObject("tender", tenderModel);
        }
        if (tenderAddOnServiceList.size() > 0) {
            modelAndView.addObject("models", tenderAddOnServiceList);
            modelAndView.setViewName("admin/warehouse/tender-add-on-service/index");
        } else {
            modelAndView.setViewName("redirect:/admin/tender-add-on-service/edit/tender/" + id);
        }
        LOGGER.info("TenderAddOnService index action returned {} models", tenderAddOnServiceList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/tender/{tenderId}", "/edit/tender/{tenderId}/{id}"})
    public ModelAndView edit(@PathVariable("tenderId") Integer tenderId, @PathVariable("id") Optional<Integer> id) {
        LOGGER.info("TenderAddOnService edit action called");
        ModelAndView modelAndView = new ModelAndView();
        TenderAddOnService model = new TenderAddOnService();
        Optional<Tender> tender = tenderService.get(tenderId);
        if (tender.isPresent()) {
            Tender tenderModel = tender.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<TenderAddOnService> tenderAddOnService = tenderAddOnServiceService.get(identifier);
                if (tenderAddOnService.isPresent()) {
                    model = tenderAddOnService.get();
                }
            }
            model.setTender(tenderModel);
        }

        List<AddOnService> addOnServiceList = addOnServiceService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("addOnServices", addOnServiceList);
        modelAndView.setViewName("admin/warehouse/tender-add-on-service/edit");
        LOGGER.info("TenderAddOnService edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") TenderAddOnService tenderAddOnService,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("TenderAddOnService save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/tender-add-on-service/edit");
        if (!result.hasErrors()) {
            try {
                tenderAddOnServiceService.edit(tenderAddOnService);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("TenderAddOnService save action successful");
                modelAndView.setViewName("redirect:/admin/tender-add-on-service/index/tender/" + tenderAddOnService
                        .getTender()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.setViewName("redirect:/admin/tender-add-on-service/edit/tender/" + tenderAddOnService
                        .getTender()
                        .getId());
            }
        } else {
            LOGGER.error("TenderAddOnService save action had errors {}", result.getAllErrors());
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
        LOGGER.info("TenderAddOnService delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer tenderId = null;
        try {
            Optional<TenderAddOnService> tenderAddOnService = tenderAddOnServiceService.get(id);
            if (tenderAddOnService.isPresent()) {
                TenderAddOnService model = tenderAddOnService.get();
                tenderId = model.getTender().getId();
                tenderAddOnServiceService.delete(id);
                LOGGER.info("TenderAddOnService delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/tender-add-on-service/index/tender/" + tenderId);
        return modelAndView;
    }

}

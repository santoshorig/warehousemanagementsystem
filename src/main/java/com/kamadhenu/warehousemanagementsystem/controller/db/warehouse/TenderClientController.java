package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderClient;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TenderClientService;
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
 * <h1>TenderClient controller</h1>
 * <p>
 * This tender client controller class provides the CRUD actions for tender clients
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/tender-client")
public class TenderClientController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderClientController.class);

    @Autowired
    private TenderClientService tenderClientService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("TenderClient index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<TenderClient> tenderClientList = tenderClientService.getAll();
        modelAndView.addObject("models", tenderClientList);
        modelAndView.setViewName("admin/warehouse/tender-client/index");
        LOGGER.info("TenderClient index action returned {} models", tenderClientList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("TenderClient edit action called");
        ModelAndView modelAndView = new ModelAndView();
        TenderClient model = new TenderClient();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("TenderClient edit action called on existing model with id {}", identifier);
            Optional<TenderClient> tenderClient = tenderClientService.get(identifier);
            if (tenderClient.isPresent()) {
                model = tenderClient.get();
            }
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/tender-client/edit");
        LOGGER.info("TenderClient edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") TenderClient tenderClient,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("TenderClient save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/tender-client/edit");
        if (!result.hasErrors()) {
            try {
                tenderClientService.edit(tenderClient);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("TenderClient save action successful");
                modelAndView.setViewName("redirect:/admin/tender-client/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("TenderClient save action had errors {}", result.getAllErrors());
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
        LOGGER.info("TenderClient delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            tenderClientService.delete(id);
            LOGGER.info("TenderClient delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/tender-client/index");
        return modelAndView;
    }

}

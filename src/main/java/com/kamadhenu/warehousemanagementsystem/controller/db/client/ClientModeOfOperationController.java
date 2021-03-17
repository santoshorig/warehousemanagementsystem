package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientModeOfOperation;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientModeOfOperationService;
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
 * <h1>ClientModeOfOperation controller</h1>
 * <p>
 * This client mode of operation controller class provides the CRUD actions for client mode of operations
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-mode-of-operation")
public class ClientModeOfOperationController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientModeOfOperationController.class);

    @Autowired
    private ClientModeOfOperationService clientModeOfOperationService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("ClientModeOfOperation index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ClientModeOfOperation> clientModeOfOperationList = clientModeOfOperationService.getAll();
        modelAndView.addObject("models", clientModeOfOperationList);
        modelAndView.setViewName("admin/client/client-mode-of-operation/index");
        LOGGER.info("ClientModeOfOperation index action returned {} models", clientModeOfOperationList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ClientModeOfOperation edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientModeOfOperation model = new ClientModeOfOperation();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("ClientModeOfOperation edit action called on existing model with id {}", identifier);
            Optional<ClientModeOfOperation> clientModeOfOperation = clientModeOfOperationService.get(identifier);
            if (clientModeOfOperation.isPresent()) {
                model = clientModeOfOperation.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/client/client-mode-of-operation/edit");
        LOGGER.info("ClientModeOfOperation edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientModeOfOperation clientModeOfOperation,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientModeOfOperation save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/client-mode-of-operation/edit");
        if (!result.hasErrors()) {
            try {
                clientModeOfOperationService.edit(clientModeOfOperation);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientModeOfOperation save action successful");
                modelAndView.setViewName("redirect:/admin/client-mode-of-operation/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("ClientModeOfOperation save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientModeOfOperation delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientModeOfOperationService.delete(id);
            LOGGER.info("ClientModeOfOperation delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-mode-of-operation/index");
        return modelAndView;
    }

}

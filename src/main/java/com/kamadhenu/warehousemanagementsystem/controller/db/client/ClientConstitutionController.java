package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientConstitution;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientConstitutionService;
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
 * <h1>ClientConstitution controller</h1>
 * <p>
 * This client constitution controller class provides the CRUD actions for client constitutions
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-constitution")
public class ClientConstitutionController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientConstitutionController.class);

    @Autowired
    private ClientConstitutionService clientConstitutionService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("ClientConstitution index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ClientConstitution> clientConstitutionList = clientConstitutionService.getAll();
        modelAndView.addObject("models", clientConstitutionList);
        modelAndView.setViewName("admin/client/client-constitution/index");
        LOGGER.info("ClientConstitution index action returned {} models", clientConstitutionList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ClientConstitution edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientConstitution model = new ClientConstitution();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("ClientConstitution edit action called on existing model with id {}", identifier);
            Optional<ClientConstitution> clientConstitution = clientConstitutionService.get(identifier);
            if (clientConstitution.isPresent()) {
                model = clientConstitution.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/client/client-constitution/edit");
        LOGGER.info("ClientConstitution edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientConstitution clientConstitution,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientConstitution save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/client-constitution/edit");
        if (!result.hasErrors()) {
            try {
                clientConstitutionService.edit(clientConstitution);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientConstitution save action successful");
                modelAndView.setViewName("redirect:/admin/client-constitution/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("ClientConstitution save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientConstitution delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientConstitutionService.delete(id);
            LOGGER.info("ClientConstitution delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-constitution/index");
        return modelAndView;
    }

}

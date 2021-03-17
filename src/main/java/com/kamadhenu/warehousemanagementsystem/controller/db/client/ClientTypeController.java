package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientType;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientTypeService;
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
 * <h1>ClientType controller</h1>
 * <p>
 * This client type controller class provides the CRUD actions for client types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-type")
public class ClientTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTypeController.class);

    @Autowired
    private ClientTypeService clientTypeService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("ClientType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ClientType> clientTypeList = clientTypeService.getAll();
        modelAndView.addObject("models", clientTypeList);
        modelAndView.setViewName("admin/client/client-type/index");
        LOGGER.info("ClientType index action returned {} models", clientTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ClientType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientType model = new ClientType();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("ClientType edit action called on existing model with id {}", identifier);
            Optional<ClientType> clientType = clientTypeService.get(identifier);
            if (clientType.isPresent()) {
                model = clientType.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/client/client-type/edit");
        LOGGER.info("ClientType edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientType clientType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/client-type/edit");
        if (!result.hasErrors()) {
            try {
                clientTypeService.edit(clientType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientType save action successful");
                modelAndView.setViewName("redirect:/admin/client-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("ClientType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientTypeService.delete(id);
            LOGGER.info("ClientType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-type/index");
        return modelAndView;
    }

}

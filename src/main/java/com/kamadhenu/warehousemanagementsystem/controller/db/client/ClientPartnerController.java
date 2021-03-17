package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientPartner;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientPartnerService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * <h1>ClientPartner controller</h1>
 * <p>
 * This client partner controller class provides the CRUD actions for client partners
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-partner")
public class ClientPartnerController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPartnerController.class);

    @Autowired
    private ClientPartnerService clientPartnerService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/client/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("ClientPartner index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ClientPartner> clientPartnerList = new ArrayList<>();
        Optional<Client> client = clientService.get(id);
        Boolean hasEnoughPartners = false;
        if (client.isPresent()) {
            Client clientModel = client.get();
            clientPartnerList = clientPartnerService.getByClient(clientModel);
            if (clientPartnerList.size() >= clientModel.getClientConstitution().getMinimumPartners()) {
                hasEnoughPartners = true;
            }
            modelAndView.addObject("client", clientModel);
        }

        if (clientPartnerList.size() > 0) {
            modelAndView.addObject("models", clientPartnerList);
            modelAndView.setViewName("admin/client/client-partner/index");
        } else {
            modelAndView.setViewName("redirect:/admin/client-partner/edit/client/" + id);
        }
        modelAndView.addObject("hasEnoughPartners", hasEnoughPartners);
        LOGGER.info("ClientPartner index action returned {} models", clientPartnerList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/client/{clientId}", "/edit/client/{clientId}/{id}"})
    public ModelAndView edit(@PathVariable("clientId") Integer clientId, @PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ClientPartner edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Boolean panNumberRequired = true;
        Boolean aadharNumberRequired = true;
        ClientPartner model = new ClientPartner();
        Optional<Client> client = clientService.get(clientId);
        if (client.isPresent()) {
            Client clientModel = client.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<ClientPartner> clientPartner = clientPartnerService.get(identifier);
                if (clientPartner.isPresent()) {
                    model = clientPartner.get();
                }
            }
            model.setClient(clientModel);
            if (clientModel.getClientConstitution().getName()
                    .equalsIgnoreCase(constantService.getINDIVIDUAL_CONSTITUTION())) {
                if (clientModel.getBusinessType().getName()
                        .equalsIgnoreCase(constantService.getBUSINESS_TYPES().get("government"))) {
                    aadharNumberRequired = false;
                    panNumberRequired = false;
                } else {
                    aadharNumberRequired = true;
                    panNumberRequired = true;
                }
            } else {
                aadharNumberRequired = false;
                if (clientModel.getBusinessType().getName()
                        .equalsIgnoreCase(constantService.getBUSINESS_TYPES().get("government"))) {
                    panNumberRequired = false;
                }
            }
        }

        List<String> titleList = constantService.getTITLES();
        List<Location> locationList = locationService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("titles", titleList);
        modelAndView.addObject("locations", locationList);
        modelAndView.addObject("panNumberRequired", panNumberRequired);
        modelAndView.addObject("aadharNumberRequired", aadharNumberRequired);
        modelAndView.setViewName("admin/client/client-partner/edit");
        LOGGER.info("ClientPartner edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientPartner clientPartner,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientPartner save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/client/client-partner/edit");
        if (!result.hasErrors()) {
            try {
                clientPartnerService.edit(clientPartner);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientPartner save action successful");
                modelAndView.setViewName("redirect:/admin/client-partner/index/client/" + clientPartner.getClient()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.setViewName("redirect:/admin/client-partner/edit/client/" + clientPartner.getClient()
                        .getId());
            }
        } else {
            LOGGER.error("ClientPartner save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientPartner delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer clientId = null;
        try {
            Optional<ClientPartner> clientPartner = clientPartnerService.get(id);
            if (clientPartner.isPresent()) {
                ClientPartner model = clientPartner.get();
                clientId = model.getClient().getId();
                clientPartnerService.delete(id);
                LOGGER.info("ClientPartner delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-partner/index/client/" + clientId);
        return modelAndView;
    }

    /**
     * Get partner details
     *
     * @param id the id of partner
     * @return json
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Integer id) {
        Optional<ClientPartner> clientPartner = clientPartnerService.get(id);
        ClientPartner model = new ClientPartner();
        if (clientPartner.isPresent()) {
            model = clientPartner.get();
        }
        return new ResponseEntity(model, HttpStatus.OK);
    }
}

package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.client.ClientAddressForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientAddress;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientAddressService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
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
 * <h1>ClientAddress controller</h1>
 * <p>
 * This client address controller class provides the CRUD actions for client addresses
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-address")
public class ClientAddressController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAddressController.class);

    @Autowired
    private ClientAddressService clientAddressService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/client/{id}", "/edit/client/{id}/{type}"})
    public ModelAndView edit(@PathVariable("id") Integer id, @PathVariable("type") Optional<String> type) {
        LOGGER.info("ClientAddress edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientAddressForm model = new ClientAddressForm();
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            ClientAddress clientAddressModel = new ClientAddress();
            String addressType = constantService.getCLIENT_ADDRESS_TYPES().firstEntry().getValue();
            if (type.isPresent()) {
                addressType = type.get();
            }
            Optional<ClientAddress> clientAddress =
                    clientAddressService.getByClientAndAddressType(clientModel, addressType);
            if (clientAddress.isPresent()) {
                clientAddressModel = clientAddress.get();
            }
            clientAddressModel.setClient(clientModel);
            clientAddressModel.setAddressType(addressType);
            model.setClientAddress(clientAddressModel);
        }

        List<Location> locationList = helperService.getUserLocations();
        if (locationList.isEmpty()) {
            locationList = locationService.getAll();
        }
        modelAndView.addObject("model", model);
        modelAndView.addObject("locations", locationList);
        modelAndView.setViewName("admin/client/client-address/edit");
        LOGGER.info("ClientAddress edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientAddressForm clientAddressForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientAddress save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/client-address/edit");
        if (!result.hasErrors()) {
            try {
                String view =
                        "redirect:/admin/client-bank/edit/client/" + clientAddressForm.getClientAddress().getClient()
                                .getId();
                clientAddressService.edit(clientAddressForm.getClientAddress());
                if (clientAddressForm.isSameForAllAddresses()) {
                    for (String addressType : constantService.getCLIENT_ADDRESS_TYPES().values()) {
                        if (!clientAddressForm.getClientAddress().getAddressType().equalsIgnoreCase(addressType)) {
                            Optional<ClientAddress> clientAddress =
                                    clientAddressService.getByClientAndAddressType(
                                            clientAddressForm.getClientAddress().getClient(),
                                            addressType
                                    );
                            ClientAddress otherAddressTypeClient =
                                    new ClientAddress(clientAddressForm.getClientAddress());
                            otherAddressTypeClient.setAddressType(addressType);
                            if (clientAddress.isPresent()) {
                                otherAddressTypeClient.setId(clientAddress.get().getId());
                            }
                            clientAddressService.edit(otherAddressTypeClient);
                        }
                    }
                } else {
                    try {
                        if (constantService.getCLIENT_ADDRESS_TYPES()
                                .ceilingEntry(clientAddressForm.getClientAddress().getAddressType())
                                .getValue() !=
                                constantService.getCLIENT_ADDRESS_TYPES()
                                        .higherEntry(clientAddressForm.getClientAddress().getAddressType())
                                        .getValue()) {
                            view = "redirect:/admin/client-address/edit/client/" + clientAddressForm.getClientAddress()
                                    .getClient()
                                    .getId() + "/" + constantService.getCLIENT_ADDRESS_TYPES()
                                    .higherEntry(clientAddressForm.getClientAddress().getAddressType())
                                    .getValue();
                        }
                    } catch (Exception e) {
                        LOGGER.debug("Reached end of address types");
                    }
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientAddress save action successful");
                modelAndView
                        .setViewName(view);
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.setViewName("redirect:/admin/client-address/edit/client/" + clientAddressForm
                        .getClientAddress().getClient().getId() + "/" + clientAddressForm.getClientAddress()
                        .getAddressType());
            }
        } else {
            LOGGER.error("ClientAddress save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientAddress delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientAddressService.delete(id);
            LOGGER.info("ClientAddress delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-address/index");
        return modelAndView;
    }

}

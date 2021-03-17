package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>ClientRemark controller</h1>
 * <p>
 * This client remark controller class provides the CRUD actions for client remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-remark")
public class ClientRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRemarkController.class);

    @Autowired
    private ClientRemarkService clientRemarkService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("ClientRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientRemarkService.delete(id);
            LOGGER.info("ClientRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by client action
     *
     * @param id the id of client
     * @return json
     */
    @RequestMapping(value = "/getByClient/{id}", method = RequestMethod.GET)
    public ResponseEntity getByClient(@PathVariable Integer id) {
        Optional<Client> client = clientService.get(id);
        List<ClientRemark> clientRemarkList = new ArrayList<ClientRemark>();
        if (client.isPresent()) {
            Client model = client.get();
            clientRemarkList = clientRemarkService.getByClient(model);
        }
        return new ResponseEntity(clientRemarkList, HttpStatus.OK);
    }
}

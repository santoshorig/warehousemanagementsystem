package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientBank;
import com.kamadhenu.warehousemanagementsystem.service.db.bank.BankBranchService;
import com.kamadhenu.warehousemanagementsystem.service.db.bank.BankService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientBankService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
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
 * <h1>ClientBank controller</h1>
 * <p>
 * This client bank controller class provides the CRUD actions for client banks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-bank")
public class ClientBankController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientBankController.class);

    @Autowired
    private ClientBankService clientBankService;

    @Autowired
    private BankBranchService bankBranchService;

    @Autowired
    private BankService bankService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private HelperService helperService;

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/client/{id}"})
    public ModelAndView edit(@PathVariable("id") Integer id) {
        LOGGER.info("ClientBank edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientBank model = new ClientBank();
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            List<ClientBank> clientBankList = clientBankService.getByClient(clientModel);
            if (clientBankList.size() > 0) {
                model = clientBankList.get(0);
            }
            model.setClient(clientModel);
        }
        List<BankBranch> bankBranchList = bankBranchService.getAll();
        List<Bank> bankList = bankService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("bankBranches", bankBranchList);
        modelAndView.addObject("banks", bankList);
        modelAndView.setViewName("admin/client/client-bank/edit");
        LOGGER.info("ClientBank edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientBank clientBank,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientBank save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/client-bank/edit");
        if (!result.hasErrors()) {
            try {
                clientBankService.edit(clientBank);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientBank save action successful");
                modelAndView
                        .setViewName("redirect:/admin/client-signatory/index/client/" + clientBank.getClient().getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("ClientBank save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientBank delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientBankService.delete(id);
            LOGGER.info("ClientBank delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-bank/index");
        return modelAndView;
    }

    /**
     * Get branch by bank action
     *
     * @param id the id of bank
     * @return json
     */
    @RequestMapping(value = "/getByBank/{id}", method = RequestMethod.GET)
    public ResponseEntity getByBank(@PathVariable Integer id) {
        Optional<Bank> bank = bankService.get(id);
        List<BankBranch> bankBranchList = new ArrayList<BankBranch>();
        if (bank.isPresent()) {
            Bank model = bank.get();
            bankBranchList = bankBranchService.getByBank(model);
        }
        return new ResponseEntity(bankBranchList, HttpStatus.OK);
    }

}

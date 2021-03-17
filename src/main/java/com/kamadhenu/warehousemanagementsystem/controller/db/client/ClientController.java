package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.client.ClientPartnerForm;
import com.kamadhenu.warehousemanagementsystem.form.client.ClientRemarkForm;
import com.kamadhenu.warehousemanagementsystem.form.client.ClientRemarkRiskForm;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.client.*;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.hr.Employee;
import com.kamadhenu.warehousemanagementsystem.model.db.risk.HighmarkRiskCategory;
import com.kamadhenu.warehousemanagementsystem.service.db.bank.BankService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.*;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.CommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeeService;
import com.kamadhenu.warehousemanagementsystem.service.db.risk.HighmarkRiskCategoryService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Client controller</h1>
 * <p>
 * This client controller class provides the CRUD actions for clients
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client")
public class ClientController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConstitutionService clientConstitutionService;

    @Autowired
    private ClientTypeService clientTypeService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private ClientCommodityService clientCommodityService;

    @Autowired
    private HighmarkRiskCategoryService highmarkRiskCategoryService;

    @Autowired
    private ClientRemarkService clientRemarkService;

    @Autowired
    private ClientPartnerService clientPartnerService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.client.ClientService clientDomainService;

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
        LOGGER.info("Client index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clientList = new ArrayList<>();
        List<Integer> locationList = helperService.getUserLocationsId();
        List<Client> existingClientList = clientService.getByStatusAndBusinessType();
        ///TODO can be better
        if (!locationList.isEmpty() && !helperService.isBusinessHeadUser() && !helperService
                .isRiskUser() && !helperService.isRiskHead()) {
            for (Client client : existingClientList) {
                List<ClientAddress> clientAddressList = client.getClientAddresses();
                for (ClientAddress clientAddress : clientAddressList) {
                    if (locationList.contains(clientAddress.getLocation().getId()) && !clientList.contains(client)) {
                        clientList.add(client);
                    }
                }
            }
        } else {
            clientList = existingClientList;
        }
        modelAndView.addObject("models", clientList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_CLIENT_STATUS());
        modelAndView.setViewName("admin/client/client/index");
        LOGGER.info("Client index action returned {} models", clientList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Client edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Client model = new Client();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Client edit action called on existing model with id {}", identifier);
            Optional<Client> client = clientService.get(identifier);
            if (client.isPresent()) {
                model = client.get();
                if (model.getStatus().equalsIgnoreCase(constantService.getCLIENT_STATUS()
                        .get("pendingforbusinessreview")) && !helperService.isBusinessReviewerUser()) {
                    modelAndView.setViewName("redirect:/error/403");
                    return modelAndView;
                }
                if (model.getStatus()
                        .equalsIgnoreCase(constantService.getCLIENT_STATUS().get("underprocess")) && !helperService
                        .isGeneralUser()) {
                    modelAndView.setViewName("redirect:/error/403");
                    return modelAndView;
                }
            }
        } else {
            if (helperService.isGeneralUser()) {
                model.setStatus(constantService.getCONTRACT_STATUS().get("underprocess"));
                model.setFundingEligible(Boolean.FALSE);
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }

        List<ClientConstitution> clientConstitutionList = clientConstitutionService.getAll();
        List<ClientType> clientTypeList = clientTypeService.getAll();
        List<Employee> employeeList = employeeService.getAll();
        List<Bank> bankList = bankService.getAll();
        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        List<Client> clientList = clientService.getAll();
        List<Commodity> commodityList = commodityService.getAll();
        Boolean canEdit = clientService.canEdit(model);
        modelAndView.addObject("model", model);
        modelAndView.addObject("clientConstitutions", clientConstitutionList);
        modelAndView.addObject("clientTypes", clientTypeList);
        modelAndView.addObject("employees", employeeList);
        modelAndView.addObject("banks", bankList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.addObject("clients", clientList);
        modelAndView.addObject("commodities", commodityList);
        modelAndView.addObject("canEdit", canEdit);
        modelAndView.setViewName("admin/client/client/edit");
        LOGGER.info("Client edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Client client,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Client save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/client/edit");
        if (!result.hasErrors()) {
            try {
                clientService.edit(client);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Client save action successful");
                modelAndView
                        .setViewName("redirect:/admin/client-partner/index/client/" + client
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Client save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Client delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            Optional<Client> client = clientService.get(id);
            if (client.isPresent()) {
                Client clientModel = client.get();
                clientModel.setStatus(constantService.getCLIENT_STATUS().get("inactive"));
                clientService.edit(clientModel);
            }
            LOGGER.info("Client delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client/index");
        return modelAndView;
    }

    /**
     * Risk approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/risk-approval/{id}"})
    public ModelAndView riskApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Client risk approval action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientRemarkRiskForm clientRemarkRiskForm = new ClientRemarkRiskForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.client.Client model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.client.Client();
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            if (helperService.getClientStatusByRole().stream().anyMatch(clientModel.getStatus()::equalsIgnoreCase)) {
                model = clientDomainService.get(clientModel);
            }
            clientRemarkRiskForm.setClient(clientModel);
            List<ClientPartnerForm> clientPartnerFormList = new ArrayList<>();
            List<ClientPartner> clientPartnerList = clientPartnerService.getByClient(clientModel);
            for (ClientPartner clientPartner : clientPartnerList) {
                ClientPartnerForm clientPartnerForm = new ClientPartnerForm();
                clientPartnerForm.setClientPartner(clientPartner);
                clientPartnerFormList.add(clientPartnerForm);
            }
            clientRemarkRiskForm.setClientPartnerFormList(clientPartnerFormList);
            clientRemarkRiskForm.setFundingEligible(clientModel.getFundingEligible());
        }

        List<String> statusList = constantService.getRISK_USER_APPROVAL_CLIENT_STATUS();
        if (helperService.isRiskHead()) {
            statusList = constantService.getRISK_HEAD_APPROVAL_CLIENT_STATUS();
        }
        List<HighmarkRiskCategory> highmarkRiskCategoryList = highmarkRiskCategoryService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("highMarkRiskCategories", highmarkRiskCategoryList);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("clientRemarkRiskForm", clientRemarkRiskForm);
        modelAndView.setViewName("admin/client/client/risk-approval");
        LOGGER.info("Client risk approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Risk approval save action
     *
     * @return the model and view
     */
    @PostMapping("/risk-approval-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientRemarkRiskForm clientRemarkRiskForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Client risk approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/client/index");
        if (!result.hasErrors()) {
            try {
                for (ClientPartnerForm clientPartnerForm : clientRemarkRiskForm.getClientPartnerFormList()) {
                    ClientPartner clientPartner = clientPartnerForm.getClientPartner();
                    MultipartFile highmarkUpload = clientPartnerForm.getHighmarkUpload();
                    if (!highmarkUpload.isEmpty()) {
                        Document highmarkDocument = documentService.store(highmarkUpload);
                        clientPartner.setHighmarkDocument(highmarkDocument);
                    }
                    MultipartFile cibilUpload = clientPartnerForm.getCibilUpload();
                    if (!cibilUpload.isEmpty()) {
                        Document cibilDocument = documentService.store(cibilUpload);
                        clientPartner.setCibilDocument(cibilDocument);
                    }
                    clientPartnerService.edit(clientPartner);
                }
                Client client = clientRemarkRiskForm.getClient();
                client.setFundingEligible(clientRemarkRiskForm.getFundingEligible());
                client.setStatus(clientRemarkRiskForm.getStatus());
                clientService.edit(client);
                ClientRemark clientRemark = new ClientRemark();
                clientRemark.setRemark(clientRemarkRiskForm.getRemark());
                clientRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                clientRemark.setRemarkDate(new Date());
                clientRemark.setClient(client);
                clientRemark.setUser(helperService.getLoggedInDbUser());
                clientRemarkService.edit(clientRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Client risk approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false, result.getAllErrors().toString());
            LOGGER.error("Client risk approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }


    /**
     * Business approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/business-approval/{id}"})
    public ModelAndView businessApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Client business approval action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientRemarkForm clientRemarkForm = new ClientRemarkForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.client.Client model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.client.Client();
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            if (helperService.getClientStatusByRole().stream().anyMatch(clientModel.getStatus()::equalsIgnoreCase)) {
                model = clientDomainService.get(clientModel);
            }
            clientRemarkForm.setClient(clientModel);
        }

        List<String> statusList = constantService.getBUSINESS_APPROVAL_CLIENT_STATUS();
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("clientRemarkForm", clientRemarkForm);
        modelAndView.setViewName("admin/client/client/business-approval");
        LOGGER.info("Client business approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Business approval save action
     *
     * @return the model and view
     */
    @PostMapping("/business-approval-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientRemarkForm clientRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Client business approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/client/index");
        if (!result.hasErrors()) {
            try {
                Client client = clientRemarkForm.getClient();
                client.setStatus(clientRemarkForm.getStatus());
                clientService.edit(client);
                ClientRemark clientRemark = new ClientRemark();
                clientRemark.setRemark(clientRemarkForm.getRemark());
                clientRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                clientRemark.setRemarkDate(new Date());
                clientRemark.setClient(client);
                clientRemark.setUser(helperService.getLoggedInDbUser());
                clientRemarkService.edit(clientRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Client business approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
                
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Client business approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Check if client code exists
     *
     * @param client
     * @param code
     * @return
     */
    @RequestMapping(value = {"/check-code/{code}", "/check-code/{code}/{client}"}, method = RequestMethod.GET)
    public ResponseEntity checkCode(@PathVariable String code, @PathVariable Optional<Integer> client) {
        Optional<Client> clientModel = clientService.getByCode(code);
        Boolean valid = true;
        if (clientModel.isPresent()) {
            if (client.isPresent()) {
                if (clientModel.get().getId() != client.get().intValue()) {
                    valid = false;
                }
            } else {
                valid = false;
            }
        }
        return new ResponseEntity(valid, HttpStatus.OK);
    }

    /**
     * Get client by id action
     *
     * @param id the id of client
     * @return json
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable Integer id) {
        Optional<Client> client = clientService.get(id);
        Client clientModel = new Client();
        if (client.isPresent()) {
            clientModel = client.get();
        }
        return new ResponseEntity(clientModel, HttpStatus.OK);
    }

    /**
     * Get commodity by id action
     *
     * @param client the id of client
     * @return json
     */
    @RequestMapping(value = "/getCommodityByClient/{client}", method = RequestMethod.GET)
    public ResponseEntity getCommodityByClient(@PathVariable Integer client) {
        Optional<Client> clientModel = clientService.get(client);
        List<ClientCommodity> clientCommodityList = new ArrayList<>();
        if (clientModel.isPresent()) {
            clientCommodityList = clientCommodityService.getByClient(clientModel.get());
        }
        return new ResponseEntity(clientCommodityList, HttpStatus.OK);
    }
}

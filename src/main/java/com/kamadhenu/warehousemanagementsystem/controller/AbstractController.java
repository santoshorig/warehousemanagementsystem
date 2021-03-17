package com.kamadhenu.warehousemanagementsystem.controller;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Notification;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.NotificationService;
import com.kamadhenu.warehousemanagementsystem.service.db.insurance.InsuranceService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * <h1>Abstract controller</h1>
 * <p>
 * This abstract controller class provides the base data needed for all other controllers
 *
 * @author Kamadhenu
 */
public abstract class AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Get model name split basis camel case
     *
     * @return the model name
     */
    private String getModelName() {
        LOGGER.debug("Getting model name");
        return helperService.splitCamelCase(this.getClass().getSimpleName().replaceAll("Controller", ""), "-")
                .toLowerCase();
    }

    /**
     * Get friendly model name split basis camel case
     *
     * @return the model name
     */
    private String getFriendlyModelName() {
        LOGGER.debug("Getting model name");
        return helperService.splitCamelCase(this.getClass().getSimpleName().replaceAll("Controller", ""), " ")
                .toLowerCase();
    }

    /**
     * Get controller name
     *
     * @return the controller name
     */
    @ModelAttribute("controller")
    public String getController() {
        LOGGER.debug("Getting controller name");
        return this.getModelName();
    }

    /**
     * Get friendly controller name
     *
     * @return the controller name
     */
    @ModelAttribute("friendlyController")
    public String getFriendlyController() {
        LOGGER.debug("Getting friendly controller name");
        return this.getFriendlyModelName();
    }

    /**
     * Get user id
     *
     * @return the logged in users id
     */
    @ModelAttribute("userId")
    public Integer getUserId() {
        LOGGER.debug("Getting user id");
        return helperService.getUserId();
    }

    /**
     * Get user first name
     *
     * @return the logged in users first name
     */
    @ModelAttribute("userFirstName")
    public String getUserFirstName() {
        LOGGER.debug("Getting user first name");
        return helperService.getUserName();
    }

    /**
     * Get user first name with details
     *
     * @return the logged in users first name
     */
    @ModelAttribute("userFirstNameWithDetails")
    public String getUserFirstNameWithDetails() {
        LOGGER.debug("Getting user first name with details");
        return helperService.getUserNameWithDetails();
    }

    /**
     * Get user details
     *
     * @return the logged in users details
     */
    @ModelAttribute("userDetails")
    public String getUserDetails() {
        LOGGER.debug("Getting user details");
        return helperService.getUserDetails();
    }

    /**
     * Get female or not
     *
     * @return the logged in user is female or not
     */
    @ModelAttribute("isFemale")
    public Boolean isFemale() {
        LOGGER.debug("Getting user female or not");
        return helperService.isFemale();
    }

    /**
     * Get user login time
     *
     * @return the logged in users login time
     */
    @ModelAttribute("loginTime")
    public String getLoginTime() {
        return helperService.getLoginTime();
    }

    /**
     * Get session time
     *
     * @return the logged in users session time
     */
    @ModelAttribute("sessionTime")
    public String getSessionTime() {
        return helperService.getSessionTime();
    }

    /**
     * Add redirect attributes to the current session
     *
     * @param redirectAttributes the redirect atributes object from the current controller action
     * @param success            if redirect attribute needs to be for a success or failure action
     */
    public void addFlashMessage(final RedirectAttributes redirectAttributes, Boolean success) {
        redirectAttributes.addFlashAttribute("success", success);
        redirectAttributes.addFlashAttribute("msg", success ? "Action performed successfully" : "Action failed");
    }    
    
    /**
    * Add redirect attributes to the current session
    *
    * @param redirectAttributes the redirect atributes object from the current controller action
    * @param success            if redirect attribute needs to be for a success or failure action
    */
   public void addFlashMessage(final RedirectAttributes redirectAttributes, Boolean success, String errorMsg) {
       redirectAttributes.addFlashAttribute("success", success);
       redirectAttributes.addFlashAttribute("msg", success ? "Action performed successfully" : "Action failed: " + errorMsg);
   }

    /**
     * Get if user is government
     *
     * @return
     */
    @ModelAttribute("isGovernment")
    public Boolean isGovernment() {
        return helperService.isGovernmentUser();
    }

    /**
     * Get if user is risk user
     *
     * @return
     */
    @ModelAttribute("isRiskUser")
    public Boolean isRiskUser() {
        return helperService.isRiskUser();
    }

    /**
     * Get if user is  business user
     *
     * @return
     */
    @ModelAttribute("isBusinessHeadUser")
    public Boolean isBusinessHeadUser() {
        return helperService.isBusinessHeadUser();
    }

    /**
     * Get list of notifications based on user role
     *
     * @return
     */
    @ModelAttribute("notifications")
    public Map<String, String> getNotifications() {
        Map<String, String> notificationsMap = new HashMap<>();

        // get pending clients
        List<String> statusList = new ArrayList<>();
        if (helperService.isRiskUser()) {
            statusList.add(constantService.getCLIENT_STATUS().get("pendingforriskassessment"));
        } else if (helperService.isRiskHead()) {
            statusList.add(constantService.getCLIENT_STATUS().get("pendingforriskapproval"));
        } else if (helperService.isBusinessHeadUser()) {
            statusList.add(constantService.getCLIENT_STATUS().get("pendingforbusinessapproval"));
        } else if (helperService.isGeneralUser()) {
            statusList.add(constantService.getCLIENT_STATUS().get("rework"));
        } else if (helperService.isBusinessReviewerUser()) {
            statusList.add(constantService.getCLIENT_STATUS().get("pendingforbusinessreview"));
        }

        List<Client> clientList = clientService.getByStatusAndBusinessType(statusList);
        if (clientList.size() > 0) {
            notificationsMap.put("client", String
                    .format("%d client(s) in %s status", clientList.size(), String.join(" or ", statusList)));
        }

        // get pending contracts
        statusList = new ArrayList<>();
        if (helperService.isBusinessHeadUser()) {
            statusList.add(constantService.getCONTRACT_STATUS().get("pendingforbusinessapproval"));
        } else if (helperService.isGeneralUser()) {
            statusList.add(constantService.getCONTRACT_STATUS().get("rework"));
        } else if (helperService.isOpsApprover()) {
            statusList.add(constantService.getCONTRACT_STATUS().get("pendingforopsapproval"));
        }

        List<Contract> contractList = contractService.getByStatusAndBusinessType(statusList);
        if (contractList.size() > 0) {
            notificationsMap.put("contract", String
                    .format("%d contract(s) in %s status", contractList.size(), String.join(" or ", statusList)));
        }

        // get pending warehouses
        statusList = new ArrayList<>();
        if (helperService.isRiskUser()) {
            statusList.add(constantService.getWAREHOUSE_STATUS().get("pendingforriskassessment"));
        } else if (helperService.isRiskHead()) {
            statusList.add(constantService.getWAREHOUSE_STATUS().get("pendingforriskapproval"));
        } else if (helperService.isBusinessHeadUser()) {
            statusList.add(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessapproval"));
        } else if (helperService.isGeneralUser()) {
            statusList.add(constantService.getWAREHOUSE_STATUS().get("rework"));
        } else if (helperService.isBusinessReviewerUser()) {
            statusList.add(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessreview"));
        }

        List<Warehouse> warehouseList = warehouseService.getByStatus(statusList);
        if (warehouseList.size() > 0) {
            notificationsMap.put("warehouse", String
                    .format("%d warehouse(s) in %s status", warehouseList.size(), String.join(" or ", statusList)));
        }

        // get pending inward
        if (helperService.isInwardReviewer()) {
            statusList.add(constantService.getINWARD_STATUS().get("pendingforreview"));
        } else if (helperService.isGeneralUser()) {
            statusList.add(constantService.getINWARD_STATUS().get("rework"));
        }

        List<Inward> inwardList = inwardService.getByStatusAndBusinessType(statusList);
        if (inwardList.size() > 0) {
            notificationsMap.put("inward", String
                    .format("%d inward(s) in %s status", inwardList.size(), String.join(" or ", statusList)));
        }

        return notificationsMap;
    }

    /**
     * Get list of inward notifications
     *
     * @return
     */
    @ModelAttribute("inwardNotifications")
    public List<Notification> getInwardNotifications() {
        return notificationService.getUnreadByRole(constantService.getNOTIFICATION_INWARD_OLD_ROLE());
    }

    /**
     * Get list of insurance alerts
     *
     * @return
     */
    @ModelAttribute("insuranceAlerts")
    public List<Insurance> getInsuranceAlerts() {
        Integer daysAheadForInsurnce = constantService.getInsuranceDaysForExpiryAlert();
        Date dateAheadForInsurance = helperService.getDaysAheadFromNow(daysAheadForInsurnce);
        return insuranceService.getAllByEffectiveToBefore(dateAheadForInsurance);
    }
}

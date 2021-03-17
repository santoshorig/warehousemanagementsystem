package com.kamadhenu.warehousemanagementsystem.service.domain.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Constant service
 */
@Service("constantService")
@Data
@ToString
@EqualsAndHashCode
public class ConstantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstantService.class);

    // User and Employee constants
    private final List<String> TITLES = new ArrayList<>(
            Arrays.asList(
                    "Mr.",
                    "Mrs.",
                    "Ms."
            )
    );

    private final String MALE_TITLE = "Mr.";

    private final Map<String, String> TITLE_TO_GENDERS = new HashMap<String, String>() {
        {
            put("Mr.", "Male");
            put("Mrs.", "Female");
            put("Ms.", "Female");
        }
    };


    private final List<String> ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_IW_OW_OPS",
                    "ROLE_MIS_OPS",
                    "ROLE_SR_MAKER",
                    "ROLE_OPS_REVIEWER",
                    "ROLE_SR_CHECKER",
                    "ROLE_MIS_SR_WR_DO_OW",
                    "ROLE_DO_CHECKER",
                    "ROLE_MIS_FIELD",
                    "ROLE_BD_REVIEWER",
                    "ROLE_OPS_BH_APPROVER",
                    "ROLE_RISK_USER",
                    "ROLE_RISK_APPROVER",
                    "ROLE_MGMT_APPROVER",
                    "ROLE_SR_SIGNATORY",
                    "ROLE_CUSTOMER",
                    "ROLE_ADMIN"
            )
    );

    private final List<String> ADMIN_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_ADMIN"
            )
    );

    private final List<String> RISK_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_RISK_USER"
            )
    );

    private final List<String> RISK_APPROVER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_RISK_APPROVER"
            )
    );

    private final List<String> BUSINESS_HEAD_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_MGMT_APPROVER",
                    "ROLE_SR_SIGNATORY"
            )
    );

    private final List<String> MANAGEMENT_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_MGMT_APPROVER",
                    "ROLE_SR_SIGNATORY"
            )
    );

    private final List<String> GENERAL_USER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_IW_OW_OPS",
                    "ROLE_MIS_FIELD",
                    "ROLE_MIS_OPS",
                    "ROLE_SR_MAKER",
                    "ROLE_MIS_SR_WR_DO_OW"
            )
    );

    private final List<String> BUSINESS_REVIEWER_USER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_BD_REVIEWER",
                    "ROLE_OPS_REVIEWER",
                    "ROLE_SR_CHECKER",
                    "ROLE_DO_CHECKER"
            )
    );

    private final List<String> OPS_APPROVER_USER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_OPS_BH_APPROVER"
            )
    );

    private final List<String> IW_GENERAL_USER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_IW_OW_OPS"
            )
    );

    private final List<String> IW_REVIEWER_USER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_MIS_OPS"
            )
    );

    private final List<String> OW_GENERAL_USER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_IW_OW_OPS"
            )
    );

    private final List<String> OW_REVIEWER_USER_ROLES = new ArrayList<>(
            Arrays.asList(
                    "ROLE_MIS_OPS"
            )
    );

    // Business type constants
    private final Map<String, String> BUSINESS_TYPES = new HashMap<String, String>() {
        {
            put("government", "Government");
            put("nongovernment", "Non Government");
        }
    };

    // Client constants
    private final TreeMap<String, String> CLIENT_ADDRESS_TYPES = new TreeMap<String, String>() {
        {
            put("office", "office");
            put("registered", "registered");
        }
    };

    private final Map<String, List<String>> CLIENT_DOCUMENT_PURPOSE = new HashMap<String, List<String>>() {
        {
            put("farmer", Arrays.asList(
                    "farmer"
            ));
            put("trader", Arrays.asList(
                    "trader"
            ));
            put("company", Arrays.asList(
                    "company"
            ));
            put("others", Arrays.asList(
                    "others"
            ));
        }
    };

    private final Map<String, String> CLIENT_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforbusinessreview", "Pending for Business Review");
            put("pendingforriskassessment", "Pending for Risk Assessment");
            put("pendingforriskapproval", "Pending for Risk Approval");
            put("pendingforbusinessapproval", "Pending for Business Approval");
            put("rework", "Rework");
            put("rejected", "Rejected");
            put("approved", "Approved");
            put("inactive", "Inactive");
        }
    };

    private final List<String> GENERAL_USER_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("underprocess"),
                    CLIENT_STATUS.get("rework"),
                    CLIENT_STATUS.get("approved")
            )
    );

    private final List<String> BUSINESS_REVIEWER_USER_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("pendingforbusinessreview")
            )
    );

    private final List<String> RISK_USER_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("pendingforriskassessment")
            )
    );

    private final List<String> RISK_HEAD_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("pendingforriskapproval")
            )
    );

    private final List<String> BUSINESS_HEAD_USER_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("pendingforbusinessapproval")
            )
    );

    private final List<String> ADMIN_USER_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("underprocess"),
                    CLIENT_STATUS.get("pendingforbusinessreview"),
                    CLIENT_STATUS.get("pendingforriskassessment"),
                    CLIENT_STATUS.get("pendingforriskapproval"),
                    CLIENT_STATUS.get("pendingforbusinessapproval"),
                    CLIENT_STATUS.get("rework"),
                    CLIENT_STATUS.get("approved"),
                    CLIENT_STATUS.get("rejected")
            )
    );

    private final List<String> RISK_USER_APPROVAL_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("pendingforriskapproval"),
                    CLIENT_STATUS.get("rework"),
                    CLIENT_STATUS.get("rejected")
            )
    );

    private final List<String> RISK_HEAD_APPROVAL_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("pendingforriskassessment"),
                    CLIENT_STATUS.get("pendingforbusinessapproval"),
                    CLIENT_STATUS.get("rework"),
                    CLIENT_STATUS.get("rejected")
            )
    );

    private final List<String> BUSINESS_APPROVAL_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("approved"),
                    CLIENT_STATUS.get("rejected"),
                    CLIENT_STATUS.get("rework")
            )
    );

    private final List<String> RISK_REPORT_CLIENT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CLIENT_STATUS.get("pendingforriskassessment"),
                    CLIENT_STATUS.get("pendingforriskapproval")
            )
    );

    private final Map<String, String> FRIENDLY_CLIENT_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Business Review", "Pending for Business Review");
            put("Pending for Risk Assessment", "Sent for Risk Assessment");
            put("Pending for Risk Approval", "Sent for Risk Approval");
            put("Pending for Business Approval", "Sent for Business Approval");
            put("Rework", "Rework for MIS");
            put("Rejected", "Reject the Client");
            put("Approved", "Approved");
            put("Inactive", "Inactive");
        }
    };

    // Contract constants
    private final Map<String, String> CONTRACT_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforopsapproval", "Pending for Ops Approval");
            put("pendingforbusinessapproval", "Pending for Business Approval");
            put("rework", "Rework");
            put("rejected", "Rejected");
            put("approved", "Approved");
            put("inactive", "Inactive");
        }
    };

    private final List<String> GENERAL_USER_CONTRACT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CONTRACT_STATUS.get("underprocess"),
                    CONTRACT_STATUS.get("rework"),
                    CONTRACT_STATUS.get("approved")
            )
    );

    private final List<String> OPS_APPROVER_USER_CONTRACT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CONTRACT_STATUS.get("pendingforopsapproval")
            )
    );

    private final List<String> BUSINESS_HEAD_USER_CONTRACT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CONTRACT_STATUS.get("pendingforbusinessapproval")
            )
    );

    private final List<String> OPS_APPROVER_APPROVAL_CONTRACT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CONTRACT_STATUS.get("pendingforbusinessapproval"),
                    CONTRACT_STATUS.get("rework")
            )
    );

    private final List<String> BUSINESS_APPROVAL_CONTRACT_STATUS = new ArrayList<>(
            Arrays.asList(
                    CONTRACT_STATUS.get("approved"),
                    CONTRACT_STATUS.get("rejected"),
                    CONTRACT_STATUS.get("rework")
            )
    );

    private final Map<String, String> FRIENDLY_CONTRACT_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Ops Approval", "Pending for Ops Approval");
            put("Pending for Business Approval", "Sent for Business Approval");
            put("Rework", "Rework for MIS");
            put("Rejected", "Reject the Contract");
            put("Approved", "Approved");
            put("Inactive", "Inactive");
        }
    };

    // Inward constants
    private final Map<String, String> INWARD_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforreview", "Pending for Review");
            put("rework", "Rework");
            put("rejected", "Rejected");
            put("approved", "Approved");
        }
    };

    private final List<String> GENERAL_USER_INWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    INWARD_STATUS.get("underprocess"),
                    INWARD_STATUS.get("rework")
            )
    );

    private final List<String> REVIEWER_USER_INWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    INWARD_STATUS.get("pendingforreview")
            )
    );

    private final List<String> REVIEWER_APPROVAL_INWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    CONTRACT_STATUS.get("approved"),
                    CONTRACT_STATUS.get("rejected"),
                    CONTRACT_STATUS.get("rework")
            )
    );

    private final Map<String, String> FRIENDLY_INWARD_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Review", "Pending for Review");
            put("Rework", "Rework for Ops");
            put("Rejected", "Reject the Inward");
            put("Approved", "Approved");
        }
    };

    private final List<String> OPEN_INWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    INWARD_STATUS.get("underprocess"),
                    INWARD_STATUS.get("pendingforreview"),
                    INWARD_STATUS.get("rework"),
                    INWARD_STATUS.get("approved")
            )
    );

    private final List<String> APPROVED_INWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    INWARD_STATUS.get("approved")
            )
    );

    // Outward constants
    private final Map<String, String> OUTWARD_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforreview", "Pending for Review");
            put("rework", "Rework");
            put("approved", "Approved");
        }
    };

    private final List<String> GENERAL_USER_OUTWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    OUTWARD_STATUS.get("underprocess"),
                    OUTWARD_STATUS.get("rework")
            )
    );

    private final List<String> REVIEWER_USER_OUTWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    OUTWARD_STATUS.get("pendingforreview")
            )
    );

    private final List<String> REVIEWER_APPROVAL_OUTWARD_STATUS = new ArrayList<>(
            Arrays.asList(
                    CONTRACT_STATUS.get("approved"),
                    CONTRACT_STATUS.get("rejected"),
                    CONTRACT_STATUS.get("rework")
            )
    );

    private final Map<String, String> FRIENDLY_OUTWARD_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Review", "Pending for Review");
            put("Rework", "Rework for Ops");
            put("Approved", "Approved");
        }
    };

    // SR constants
    private final Map<String, String> SR_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforbusinessreview", "Pending for Business Review");
            put("pendingforbusinessapproval", "Pending for Business Approval");
            put("rework", "Rework");
            put("rejected", "Rejected");
            put("approved", "Approved");
        }
    };

    private final List<String> GENERAL_USER_SR_STATUS = new ArrayList<>(
            Arrays.asList(
                    SR_STATUS.get("underprocess"),
                    SR_STATUS.get("rework"),
                    SR_STATUS.get("approved")
            )
    );

    private final List<String> BUSINESS_REVIEWER_USER_SR_STATUS = new ArrayList<>(
            Arrays.asList(
                    SR_STATUS.get("pendingforbusinessreview")
            )
    );

    private final List<String> BUSINESS_HEAD_USER_SR_STATUS = new ArrayList<>(
            Arrays.asList(
                    SR_STATUS.get("pendingforbusinessapproval")
            )
    );

    private final List<String> REVIEWER_APPROVAL_SR_STATUS = new ArrayList<>(
            Arrays.asList(
                    SR_STATUS.get("pendingforbusinessapproval"),
                    SR_STATUS.get("rework")
            )
    );

    private final List<String> BUSINESS_APPROVAL_SR_STATUS = new ArrayList<>(
            Arrays.asList(
                    SR_STATUS.get("approved"),
                    SR_STATUS.get("rejected"),
                    SR_STATUS.get("rework")
            )
    );

    private final List<String> APPROVED_SR_STATUS = new ArrayList<>(
            Arrays.asList(
                    SR_STATUS.get("approved")
            )
    );

    private final Map<String, String> FRIENDLY_SR_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Business Review", "Pending for Business Review");
            put("Pending for Business Approval", "Sent for Business Approval");
            put("Rework", "Rework for MIS");
            put("Rejected", "Reject the SR");
            put("Approved", "Approved");
        }
    };

    // LR constants
    private final Map<String, String> LR_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforbusinessreview", "Pending for Business Review");
            put("rework", "Rework");
            put("rejected", "Rejected");
            put("approved", "Approved");
        }
    };

    private final List<String> GENERAL_USER_LR_STATUS = new ArrayList<>(
            Arrays.asList(
                    LR_STATUS.get("underprocess"),
                    LR_STATUS.get("rework"),
                    LR_STATUS.get("approved")
            )
    );

    private final List<String> BUSINESS_REVIEWER_USER_LR_STATUS = new ArrayList<>(
            Arrays.asList(
                    LR_STATUS.get("pendingforbusinessreview")
            )
    );

    private final List<String> REVIEWER_APPROVAL_LR_STATUS = new ArrayList<>(
            Arrays.asList(
                    LR_STATUS.get("approved"),
                    LR_STATUS.get("rejected"),
                    LR_STATUS.get("rework")
            )
    );

    private final List<String> APPROVED_LR_STATUS = new ArrayList<>(
            Arrays.asList(
                    LR_STATUS.get("approved")
            )
    );

    private final Map<String, String> FRIENDLY_LR_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Business Review", "Pending for Business Review");
            put("Rework", "Rework for MIS");
            put("Rejected", "Reject the LR");
            put("Approved", "Approved");
        }
    };

    // DO constants
    private final Map<String, String> DO_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforbusinessreview", "Pending for Business Review");
            put("rework", "Rework");
            put("approved", "Approved");
        }
    };

    private final List<String> GENERAL_USER_DO_STATUS = new ArrayList<>(
            Arrays.asList(
                    DO_STATUS.get("underprocess"),
                    DO_STATUS.get("rework")
            )
    );

    private final List<String> BUSINESS_REVIEWER_USER_DO_STATUS = new ArrayList<>(
            Arrays.asList(
                    DO_STATUS.get("pendingforbusinessreview")
            )
    );

    private final List<String> REVIEWER_APPROVAL_DO_STATUS = new ArrayList<>(
            Arrays.asList(
                    LR_STATUS.get("approved"),
                    LR_STATUS.get("rejected"),
                    LR_STATUS.get("rework")
            )
    );

    private final List<String> APPROVED_DO_STATUS = new ArrayList<>(
            Arrays.asList(
                    LR_STATUS.get("approved")
            )
    );

    private final Map<String, String> FRIENDLY_DO_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Business Review", "Pending for Business Review");
            put("Rework", "Rework for MIS");
            put("Approved", "Approved");
        }
    };

    // Document constants
    private final Map<String, String> DOCUMENT_PURPOSE = new HashMap<String, String>() {
        {
            put("signatory", "signatory");
            put("client", "client");
            put("clienttype", "clienttype");
            put("clientconstitution", "clientconstitution");
            put("warehouse", "warehouse");
            put("inspection", "inspection");
            put("contract", "contract");
        }
    };

    // Remark constants
    private final Map<String, String> REMARK_STATUS = new HashMap<String, String>() {
        {
            put("unread", "unread");
            put("read", "read");
        }
    };

    // Unit of measure constants
    private final Map<String, String> UNIT_OF_MEASURE = new HashMap<String, String>() {
        {
            put("Bales", "Bales");
            put("Drums", "Drums");
            put("MT", "MT");
        }
    };

    // Storage in constants
    private final Map<String, String> STORAGE_IN = new HashMap<String, String>() {
        {
            put("Bales", "Bales");
            put("Drums", "Drums");
            put("Bags", "Bags");
        }
    };

    // Individual constitution constant
    private final String INDIVIDUAL_CONSTITUTION = "individual";

    // Storage charge basis constant
    private final List<String> STORAGE_CHARGE_BASIS = new ArrayList<>(
            Arrays.asList(
                    "Per MT",
                    "Per Bale",
                    "Per Bag",
                    "Per Lot"
            )
    );

    // Lock In Units constant
    private final List<String> LOCK_IN_UNITS = new ArrayList<>(
            Arrays.asList(
                    "MT",
                    "KG"
            )
    );

    // Billing Peak Stock constant
    private final List<String> BILLING_PEAK_STOCK = new ArrayList<>(
            Arrays.asList(
                    "Monthly",
                    "Fortnightly",
                    "Weekly",
                    "Daily",
                    "Actual"
            )
    );

    // Inspection constant
    private final Map<String, String> INSPECTION_TYPE = new HashMap<String, String>() {
        {
            put("inside", "Inside Warehouse");
            put("outside", "Outside Warehouse");
            put("others", "Others");
        }
    };

    // Warehouse constant
    private final Map<String, String> AGREEMENT_TYPE = new HashMap<String, String>() {
        {
            put("lease", "Lease");
            put("sublease", "Sub Lease");
            put("franchise", "Franchise");
            put("actual", "Actual");
            put("tri-partite", "Tri-Partite");
            put("wms", "WMS");
        }
    };

    private final Map<String, String> WAREHOUSE_LICENSE = new HashMap<String, String>() {
        {
            put("yes", "Yes");
            put("no", "No");
        }
    };

    private final Map<String, String> WAREHOUSE_LOCATION = new HashMap<String, String>() {
        {
            put("standalone", "Standalone");
            put("factorypremises", "Factory Premises");
            put("gatedcampus", "Gated Campus");
            put("mandpremises", "Mandi Premises");
            put("other", "Other");
        }
    };

    private final Map<String, String> WAREHOUSE_SHAPE = new HashMap<String, String>() {
        {
            put("rectangular", "Rectangular");
            put("nonrectangular", "Non-Rectangular");
        }
    };

    // Warehouse status
    private final Map<String, String> WAREHOUSE_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforbusinessreview", "Pending for Business Review");
            put("pendingforriskassessment", "Pending for Risk Assessment");
            put("pendingforriskapproval", "Pending for Risk Approval");
            put("pendingforbusinessapproval", "Pending for Business Approval");
            put("rework", "Rework");
            put("rejected", "Rejected");
            put("approved", "Approved");
            put("inactive", "Inactive");
        }
    };

    private final List<String> GENERAL_USER_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("underprocess"),
                    WAREHOUSE_STATUS.get("rework"),
                    WAREHOUSE_STATUS.get("approved")
            )
    );

    private final List<String> BUSINESS_REVIEWER_USER_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("pendingforbusinessreview")
            )
    );

    private final List<String> RISK_USER_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("pendingforriskassessment")
            )
    );

    private final List<String> RISK_HEAD_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("pendingforriskapproval")
            )
    );

    private final List<String> BUSINESS_HEAD_USER_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("pendingforbusinessapproval")
            )
    );

    private final List<String> ADMIN_USER_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("underprocess"),
                    WAREHOUSE_STATUS.get("pendingforbusinessreview"),
                    WAREHOUSE_STATUS.get("pendingforriskassessment"),
                    WAREHOUSE_STATUS.get("pendingforriskapproval"),
                    WAREHOUSE_STATUS.get("pendingforbusinessapproval"),
                    WAREHOUSE_STATUS.get("rework"),
                    WAREHOUSE_STATUS.get("approved"),
                    WAREHOUSE_STATUS.get("rejected")
            )
    );

    private final List<String> RISK_USER_APPROVAL_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("pendingforriskapproval"),
                    WAREHOUSE_STATUS.get("rework"),
                    WAREHOUSE_STATUS.get("rejected")
            )
    );

    private final List<String> RISK_HEAD_APPROVAL_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("pendingforriskassessment"),
                    WAREHOUSE_STATUS.get("pendingforbusinessapproval"),
                    WAREHOUSE_STATUS.get("rework"),
                    WAREHOUSE_STATUS.get("rejected")
            )
    );

    private final List<String> BUSINESS_APPROVAL_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("approved"),
                    WAREHOUSE_STATUS.get("rejected"),
                    WAREHOUSE_STATUS.get("rework")
            )
    );

    private final List<String> APPROVED_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("approved")
            )
    );

    private final List<String> RISK_REPORT_WAREHOUSE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_STATUS.get("pendingforriskassessment"),
                    WAREHOUSE_STATUS.get("pendingforriskapproval")
            )
    );

    private final Map<String, String> FRIENDLY_WAREHOUSE_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Business Review", "Pending for Business Review");
            put("Pending for Risk Assessment", "Sent for Risk Assessment");
            put("Pending for Risk Approval", "Sent for Risk Approval");
            put("Pending for Business Approval", "Sent for Business Approval");
            put("Rework", "Rework for MIS");
            put("Rejected", "Reject the Client");
            put("Approved", "Approved");
        }
    };

    // Warehouse Closure status
    private final Map<String, String> WAREHOUSE_CLOSURE_STATUS = new HashMap<String, String>() {
        {
            put("underprocess", "Under Process");
            put("pendingforbusinessreview", "Pending for Business Review");
            put("pendingforbusinessapproval", "Pending for Business Approval");
            put("rework", "Rework");
            put("rejected", "Rejected");
            put("approved", "Approved");
        }
    };

    private final List<String> GENERAL_USER_WAREHOUSE_CLOSURE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_CLOSURE_STATUS.get("underprocess"),
                    WAREHOUSE_CLOSURE_STATUS.get("rework"),
                    WAREHOUSE_CLOSURE_STATUS.get("approved")
            )
    );

    private final List<String> BUSINESS_REVIEWER_USER_WAREHOUSE_CLOSURE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_CLOSURE_STATUS.get("pendingforbusinessreview")
            )
    );

    private final List<String> BUSINESS_HEAD_USER_WAREHOUSE_CLOSURE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_CLOSURE_STATUS.get("pendingforbusinessapproval")
            )
    );

    private final List<String> ADMIN_USER_WAREHOUSE_CLOSURE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_CLOSURE_STATUS.get("underprocess"),
                    WAREHOUSE_CLOSURE_STATUS.get("pendingforbusinessreview"),
                    WAREHOUSE_CLOSURE_STATUS.get("pendingforbusinessapproval"),
                    WAREHOUSE_CLOSURE_STATUS.get("rework"),
                    WAREHOUSE_CLOSURE_STATUS.get("approved"),
                    WAREHOUSE_CLOSURE_STATUS.get("rejected")
            )
    );

    private final List<String> BUSINESS_APPROVAL_WAREHOUSE_CLOSURE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_CLOSURE_STATUS.get("approved"),
                    WAREHOUSE_CLOSURE_STATUS.get("rejected"),
                    WAREHOUSE_CLOSURE_STATUS.get("rework")
            )
    );

    private final List<String> APPROVED_WAREHOUSE_CLOSURE_STATUS = new ArrayList<>(
            Arrays.asList(
                    WAREHOUSE_CLOSURE_STATUS.get("approved")
            )
    );

    private final Map<String, String> FRIENDLY_WAREHOUSE_CLOSURE_STATUS = new HashMap<String, String>() {
        {
            put("Under Process", "Under Process");
            put("Pending for Business Review", "Pending for Business Review");
            put("Pending for Business Approval", "Sent for Business Approval");
            put("Rework", "Rework for MIS");
            put("Rejected", "Reject the Client");
            put("Approved", "Approved");
        }
    };

    // GST
    private final Integer GST = 18;

    // Unit of measure for quality parameter constants
    private final Map<String, String> UNIT_OF_MEAURES_QUALITY_PARAMETER = new HashMap<String, String>() {
        {
            put("Number", "Number");
            put("Percent", "Percent");
        }
    };

    // Weighbridge status
    private final Map<String, String> WEIGHBRIDGE_STATUS = new HashMap<String, String>() {
        {
            put("rejected", "Rejected");
            put("approved", "Approved");
            put("inactive", "Inactive");
        }
    };

    // Weighbridge availability
    private final Map<String, String> WEIGHBRIDGE_LOCATION = new HashMap<String, String>() {
        {
            put("Inside", "Inside");
            put("Outside", "Outside");
            put("Not Available", "Not Available");
        }
    };

    // Weighbridge type
    private final Map<String, String> WEIGHBRIDGE_TYPE = new HashMap<String, String>() {
        {
            put("Main", "Main");
            put("Alternate", "Alternate");
        }
    };

    // Quality Check by
    private final Map<String, String> QUALITY_CHECK_BY = new HashMap<String, String>() {
        {
            put("Origo", "Origo");
            put("Third Party", "Third Party");
            put("Client", "Client");
            put("Depositor", "Depositor");
        }
    };

    // Contract Billing Type
    private final Map<String, String> BILLING_TYPE = new HashMap<String, String>() {
        {
            put("Fixed", "Fixed");
            put("Variable", "Variable");
        }
    };

    // Risk Scores to Grades
    private final Map<String, Range<Double>> RISK_SCORE_GRADE = new HashMap<String, Range<Double>>() {
        {
            put("A", Range.between(new Double(8), new Double(100)));
            put("B", Range.between(new Double(6), new Double(7.99)));
            put("C", Range.between(new Double(4), new Double(5.99)));
            put("Not Acceptable", Range.between(new Double(0), new Double(3.99)));
        }
    };

    // Default risk score grade
    private final String DEFAULT_RISK_SCORE_GRADE = "Not Acceptable";

    // Insurance Owner
    private final Map<String, String> INSURANCE_OWNER = new HashMap<String, String>() {
        {
            put("Origo", "Origo");
            put("Bank", "Bank");
            put("Client", "Client");
        }
    };

    // Inward type
    private final Map<String, String> INWARD_TYPE = new HashMap<String, String>() {
        {
            put("Fresh", "Fresh");
            put("Pre-Stacked", "Pre-Stacked");
            put("Made-Up Bag", "Made-Up Bag");
            put("Dispatched Return", "Dispatched Return");
        }
    };

    // Notification constants
    private final Map<String, String> NOTIFICATION_STATUS = new HashMap<String, String>() {
        {
            put("unread", "unread");
            put("read", "read");
        }
    };

    private Integer INWARD_OLD = 7;

    private String NOTIFICATION_INWARD_OLD = "Inward greater than 7 days for CAD ";

    private String NOTIFICATION_INWARD_OLD_ROLE = "ROLE_OPS_BH_APPROVER";

    private Double EXTERNAL_QC_PERCENTAGE = 10.0;

    private Integer inwardOutwardDaysBeforeForGraph = 14;

    private Integer insuranceDaysForExpiryAlert = 14;
}
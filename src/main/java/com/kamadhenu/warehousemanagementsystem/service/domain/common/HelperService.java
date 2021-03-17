package com.kamadhenu.warehousemanagementsystem.service.domain.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.security.User;
import com.kamadhenu.warehousemanagementsystem.model.domain.user.CustomUser;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Helper service
 */
@Service("helperService")
public class HelperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private ConstantService constantService;

    /**
     * Split string by camel case
     *
     * @param s
     * @param replacement
     * @return
     */
    public String splitCamelCase(String s, String replacement) {
        return s.replaceAll(
                String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                replacement
        );
    }

    /**
     * Get session time
     *
     * @return
     */
    public String getSessionTime() {
        String sessionTime = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date loginTime = simpleDateFormat.parse(getLoginTime());
            Date now = new Date();
            long duration = now.getTime() - loginTime.getTime();
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
            sessionTime = diffInHours + " hrs " + diffInMinutes + " mins";
        } catch (ParseException ex) {
            LOGGER.error("exception found in getting session time {}", ex.getMessage());
        }
        return sessionTime;
    }

    /**
     * Get days behind from now
     *
     * @param date
     * @return
     */
    public Integer getDaysBehindFromNow(Date date) {
        Date now = new Date();
        Long diffInMilliSeconds = Math.abs(now.getTime() - date.getTime());
        Long diffInDays = TimeUnit.DAYS.convert(diffInMilliSeconds, TimeUnit.MILLISECONDS);
        return diffInDays.intValue();
    }

    /**
     * Get days ahead from now
     *
     * @param date
     * @return
     */
    public Integer getDaysAheadFromNow(Date date) {
        Date now = new Date();
        Long diffInMilliSeconds = Math.abs(date.getTime() - now.getTime());
        Long diffInDays = TimeUnit.DAYS.convert(diffInMilliSeconds, TimeUnit.MILLISECONDS);
        return diffInDays.intValue();
    }

    /**
     * Get days ahead from now
     *
     * @param duration
     * @return
     */
    public Date getDaysAheadFromNow(Integer duration) {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, duration);
        return c.getTime();
    }


    /**
     * Get user login time
     *
     * @return
     */
    public String getLoginTime() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession().getAttribute("LOGIN_TIME").toString();
    }

    /**
     * Get logged in db user
     *
     * @return
     */
    public User getLoggedInDbUser() {
        Optional<User> user = userService.get(getUserId());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    /**
     * Is user logged in
     *
     * @return
     */
    public Boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    /**
     * Get logged in user
     *
     * @return
     */
    public CustomUser getLoggedInUser() {
        return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Get user id
     *
     * @return
     */
    public Integer getUserId() {
        return getLoggedInUser().getId();
    }

    /**
     * Get user first name
     *
     * @return
     */
    public String getUserName() {
        return getLoggedInUser().getId() != null && getLoggedInUser().getEmployee() != null ? getLoggedInUser()
                .getEmployee().getName() : null;
    }

    /**
     * Get user first name with details
     *
     * @return
     */
    public String getUserNameWithDetails() {
        return getLoggedInUser().getId() != null ? getLoggedInUser().getEmployee().getName() + " (" + getLoggedInUser()
                .getRole() + " - " + getLoggedInUser().getEmployee().getBusinessType().getName() + ")" : null;
    }

    /**
     * Get user details
     *
     * @return
     */
    public String getUserDetails() {
        return getLoggedInUser().getId() != null ? getLoggedInUser().getRole() + " - " +
                getLoggedInUser().getEmployee().getBusinessType().getName() : null;
    }

    /**
     * Get female or not
     *
     * @return the logged in user is female or not
     */
    public Boolean isFemale() {
        return !getLoggedInUser().getEmployee().getTitle()
                .equalsIgnoreCase(constantService.getMALE_TITLE());
    }

    /**
     * Get md5 message
     *
     * @param message
     * @return
     */
    public String getMD5(String message) {
        String md5 = null;
        try {
            MessageDigest sha1 = MessageDigest.getInstance("MD5");
            sha1.reset();
            sha1.update(message.getBytes());
            byte[] digest = sha1.digest();
            StringBuilder password = new StringBuilder();
            for (byte b : digest) {
                password.append(String.format("%02x", b & 0xff));
            }
            md5 = password.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(stackTraceToString(e));
        }
        return md5;
    }

    /**
     * Convert stack trace to string
     *
     * @param e the exception
     * @return the stack trace in string format
     */
    public String stackTraceToString(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Convert object to map
     *
     * @param o
     * @return
     */
    public Map<String, Object> objectMap(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, Map.class);
    }

    /**
     * Get logged in users business type
     *
     * @return
     */
    public BusinessType getLoggedInUserBusinessType() {
        return getLoggedInUser().getEmployee().getBusinessType();
    }

    /**
     * Get business type of u ser
     *
     * @return
     */
    public List<BusinessType> getUserBusinessType() {
        if (isAllBusinessTypeUser()) {
            return businessTypeService.getAll();
        } else {
            return Arrays.asList(getLoggedInUserBusinessType());
        }
    }

    /**
     * Check if user can view all business types
     *
     * @return
     */
    public Boolean isAllBusinessTypeUser() {
        return
                constantService.getADMIN_ROLES().stream().anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase) ||
                        constantService.getRISK_ROLES().stream()
                                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase) ||
                        constantService.getRISK_APPROVER_ROLES().stream()
                                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase) ||
                        constantService.getMANAGEMENT_ROLES().stream()
                                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);

    }

    /**
     * Check if user is of government
     *
     * @return
     */
    public Boolean isGovernmentUser() {
        return getUserBusinessType().stream().map(BusinessType::getName)
                .anyMatch(constantService.getBUSINESS_TYPES().get("government")::equalsIgnoreCase);
    }

    /**
     * Check if user is risk user
     *
     * @return
     */
    public Boolean isRiskUser() {
        return constantService.getRISK_ROLES().stream().anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is risk head
     *
     * @return
     */
    public Boolean isRiskHead() {
        return constantService.getRISK_APPROVER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is business head
     *
     * @return
     */
    public Boolean isBusinessHeadUser() {
        return constantService.getBUSINESS_HEAD_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is general user
     *
     * @return
     */
    public Boolean isGeneralUser() {
        return constantService.getGENERAL_USER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is business reviewer
     *
     * @return
     */
    public Boolean isBusinessReviewerUser() {
        return constantService.getBUSINESS_REVIEWER_USER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is ops approver
     *
     * @return
     */
    public Boolean isOpsApprover() {
        return constantService.getOPS_APPROVER_USER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is inward general user
     *
     * @return
     */
    public Boolean isInwardGeneralUser() {
        return constantService.getIW_GENERAL_USER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is inward reviewer
     *
     * @return
     */
    public Boolean isInwardReviewer() {
        return constantService.getIW_REVIEWER_USER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is outward general user
     *
     * @return
     */
    public Boolean isOutwardGeneralUser() {
        return constantService.getOW_GENERAL_USER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Check if user is outward reviewer
     *
     * @return
     */
    public Boolean isOutwardReviewer() {
        return constantService.getOW_REVIEWER_USER_ROLES().stream()
                .anyMatch(getLoggedInUser().getRole()::equalsIgnoreCase);
    }

    /**
     * Get client status by role
     *
     * @return
     */
    public List<String> getClientStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_CLIENT_STATUS();
        if (isRiskUser()) {
            statusList = constantService.getRISK_USER_CLIENT_STATUS();
        } else if (isRiskHead()) {
            statusList = constantService.getRISK_HEAD_CLIENT_STATUS();
        } else if (isBusinessHeadUser()) {
            statusList = constantService.getBUSINESS_HEAD_USER_CLIENT_STATUS();
        } else if (isBusinessReviewerUser()) {
            statusList = constantService.getBUSINESS_REVIEWER_USER_CLIENT_STATUS();
        }
        return statusList;
    }

    /**
     * Get contract status by role
     *
     * @return
     */
    public List<String> getContractStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_CONTRACT_STATUS();
        if (isBusinessHeadUser()) {
            statusList = constantService.getBUSINESS_HEAD_USER_CONTRACT_STATUS();
        } else if (isOpsApprover()) {
            statusList = constantService.getOPS_APPROVER_USER_CONTRACT_STATUS();
        }
        return statusList;
    }

    /**
     * Get warehouse status by role
     *
     * @return
     */
    public List<String> getWarehouseStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_WAREHOUSE_STATUS();
        if (isRiskUser()) {
            statusList = constantService.getRISK_USER_WAREHOUSE_STATUS();
        } else if (isRiskHead()) {
            statusList = constantService.getRISK_HEAD_WAREHOUSE_STATUS();
        } else if (isBusinessHeadUser()) {
            statusList = constantService.getBUSINESS_HEAD_USER_WAREHOUSE_STATUS();
        } else if (isBusinessReviewerUser()) {
            statusList = constantService.getBUSINESS_REVIEWER_USER_WAREHOUSE_STATUS();
        }
        return statusList;
    }

    /**
     * Get warehouse closure status by role
     *
     * @return
     */
    public List<String> getWarehouseClosureStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_WAREHOUSE_CLOSURE_STATUS();
        if (isBusinessHeadUser()) {
            statusList = constantService.getBUSINESS_HEAD_USER_WAREHOUSE_CLOSURE_STATUS();
        } else if (isBusinessReviewerUser()) {
            statusList = constantService.getBUSINESS_REVIEWER_USER_WAREHOUSE_CLOSURE_STATUS();
        }
        return statusList;
    }

    /**
     * Get inward status by role
     *
     * @return
     */
    public List<String> getInwardStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_INWARD_STATUS();
        if (isInwardReviewer()) {
            statusList = constantService.getREVIEWER_USER_INWARD_STATUS();
        }
        return statusList;
    }

    /**
     * Get open inward status
     *
     * @return
     */
    public List<String> getOpenInwardStatus() {
        return constantService.getOPEN_INWARD_STATUS();
    }

    /**
     * Get approved inward status
     *
     * @return
     */
    public List<String> getApprovedInwardStatus() {
        return constantService.getAPPROVED_INWARD_STATUS();
    }

    /**
     * Get outward status by role
     *
     * @return
     */
    public List<String> getOutwardStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_OUTWARD_STATUS();
        if (isOutwardReviewer()) {
            statusList = constantService.getREVIEWER_USER_OUTWARD_STATUS();
        }
        return statusList;
    }

    /**
     * Get SR status by role
     *
     * @return
     */
    public List<String> getSRStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_SR_STATUS();
        if (isBusinessHeadUser()) {
            statusList = constantService.getBUSINESS_HEAD_USER_SR_STATUS();
        } else if (isBusinessReviewerUser()) {
            statusList = constantService.getBUSINESS_REVIEWER_USER_SR_STATUS();
        }
        return statusList;
    }

    /**
     * Get LR status by role
     *
     * @return
     */
    public List<String> getLRStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_LR_STATUS();
        if (isBusinessReviewerUser()) {
            statusList = constantService.getBUSINESS_REVIEWER_USER_LR_STATUS();
        }
        return statusList;
    }

    /**
     * Get DO status by role
     *
     * @return
     */
    public List<String> getDOStatusByRole() {
        List<String> statusList = constantService.getGENERAL_USER_DO_STATUS();
        if (isBusinessReviewerUser()) {
            statusList = constantService.getBUSINESS_REVIEWER_USER_DO_STATUS();
        }
        return statusList;
    }

    /**
     * Get approved warehouse status
     *
     * @return
     */
    public List<String> getApprovedWarehouseStatus() {
        return constantService.getAPPROVED_WAREHOUSE_STATUS();
    }

    /**
     * Get approved sr status
     *
     * @return
     */
    public List<String> getApprovedSrStatus() {
        return constantService.getAPPROVED_SR_STATUS();
    }

    /**
     * Get approved lr status
     *
     * @return
     */
    public List<String> getApprovedLrStatus() {
        return constantService.getAPPROVED_LR_STATUS();
    }

    /**
     * Get approved do status
     *
     * @return
     */
    public List<String> getApprovedDoStatus() {
        return constantService.getAPPROVED_DO_STATUS();
    }

    /**
     * Get location list by user
     *
     * @return
     */
    public List<Location> getUserLocations() {
        Set<Location> locationSet = getLoggedInDbUser().getLocation();
        List<Location> locationList = new ArrayList<>();
        locationList.addAll(locationSet);
        return locationList;
    }

    /**
     * Get location ids list by user
     *
     * @return
     */
    public List<Integer> getUserLocationsId() {
        List<Location> locationList = getUserLocations();
        List<Integer> locations = new ArrayList<>();
        for (Location location : locationList) {
            locations.add(location.getId());
        }
        return locations;
    }

    /**
     * Convert date to string
     *
     * @param date
     * @param format
     * @return
     */
    public String dateToString(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    /**
     * Get resource file
     *
     * @param path
     * @return
     */
    public InputStream readResourceFile(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return resource.getInputStream();
    }

    /**
     * Get key by value
     *
     * @param map
     * @param value
     * @return
     */
    public String getKeyByValue(Map<String, String> map, String value) {
        String key = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                key = entry.getKey();
            }
        }
        return key;
    }

    /**
     * Generate random number of a fixed number of digits
     *
     * @param numberOfDigits
     * @return
     */
    public Integer generateRandomDigits(Integer numberOfDigits) {
        int m = (int) Math.pow(10, numberOfDigits - 1);
        return m + new Random().nextInt(9 * m);
    }

    /**
     * Generate epoch
     *
     * @return
     */
    public Long getEpoch() {
        return ZonedDateTime.now().toInstant().toEpochMilli();
    }

    /**
     * Get year list
     *
     * @param durationYear
     * @param duration
     * @return
     */
    public List<Integer> getYearList(Integer durationYear, Integer duration) {
        List<Integer> yearList = new ArrayList<>();
        Integer startYear = durationYear - duration;
        Integer endYear = durationYear + duration;
        for (int i = startYear; i <= endYear; i++) {
            yearList.add(i);
        }
        return yearList;
    }
}

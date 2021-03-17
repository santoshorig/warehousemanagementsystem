package com.kamadhenu.warehousemanagementsystem.controller.db.report;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.report.ClientReportForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import com.kamadhenu.warehousemanagementsystem.service.excel.client.ClientExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Report controller</h1>
 * <p>
 * This report controller class provides the actions for reports
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/report")
public class ReportController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.client.ClientService clientDomainService;

    @Autowired
    private ClientExcelService clientExcelService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;


    /**
     * Client action
     *
     * @return the model and view
     */
    @GetMapping("/client")
    public ModelAndView client() {
        LOGGER.info("Report client action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientReportForm model = new ClientReportForm();
        List<String> statusList = constantService.getADMIN_USER_CLIENT_STATUS();
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("models", new ArrayList<>());
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/report/client/index");
        LOGGER.info("Report client action returned {} model", model);
        return modelAndView;
    }

    /**
     * Client post action
     *
     * @return the model and view
     */
    @PostMapping("/client-data")
    public ModelAndView clientData(
            @Valid @ModelAttribute("model") ClientReportForm clientReportForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Report client action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/report/client/index");
        if (!result.hasErrors()) {
            try {
                List<String> allowedStatusList = new ArrayList<>();
                allowedStatusList.add(clientReportForm.getStatus());
                List<Client> clientList = clientService.getByStatusAndBusinessType(allowedStatusList);
                List<String> statusList = constantService.getADMIN_USER_CLIENT_STATUS();
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Report client action successful");
                modelAndView.addObject("statuses", statusList);
                modelAndView.addObject("models", clientList);
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.setViewName("redirect:/admin/report/client-data");
            }
        } else {
            LOGGER.error("Report client action had errors {}", result.getAllErrors());
            modelAndView.setViewName("redirect:/admin/report/client-data");
        }
        return modelAndView;
    }

    /**
     * Client View  action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/client-view/{id}"})
    public ModelAndView clientView(@PathVariable("id") Integer id) {
        LOGGER.info("Client view action called");
        ModelAndView modelAndView = new ModelAndView();
        com.kamadhenu.warehousemanagementsystem.model.domain.client.Client model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.client.Client();
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            model = clientDomainService.get(clientModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/report/client/view");
        LOGGER.info("Client view action returned {} model", model);
        return modelAndView;
    }

    /**
     * Risk action
     *
     * @return the model and view
     */
    @GetMapping("/risk")
    public ModelAndView risk() {
        LOGGER.info("Report risk action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientReportForm model = new ClientReportForm();
        List<String> statusList = constantService.getRISK_REPORT_CLIENT_STATUS();
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("models", new ArrayList<>());
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/report/risk/index");
        LOGGER.info("Report client action returned {} model", model);
        return modelAndView;
    }

    /**
     * Risk post action
     *
     * @return the model and view
     */
    @PostMapping("/risk-data")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientReportForm clientReportForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Report client action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/report/risk/index");
        if (!result.hasErrors()) {
            try {
                List<String> allowedStatusList = new ArrayList<>();
                allowedStatusList.add(clientReportForm.getStatus());
                List<Client> clientList = clientService.getByStatusAndBusinessType(allowedStatusList);
                List<String> statusList = constantService.getRISK_REPORT_CLIENT_STATUS();
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Report risk action successful");
                modelAndView.addObject("statuses", statusList);
                modelAndView.addObject("models", clientList);
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.setViewName("redirect:/admin/report/risk");
            }
        } else {
            LOGGER.error("Report client action had errors {}", result.getAllErrors());
            modelAndView.setViewName("redirect:/admin/report/risk");
        }
        return modelAndView;
    }

    /**
     * Risk Download  action
     *
     * @return the httpServletResponse
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/risk-download/{id}"})
    public void clientDownload(@PathVariable("id") Integer id, HttpServletResponse httpServletResponse) {
        LOGGER.info("Risk download action called");
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            com.kamadhenu.warehousemanagementsystem.model.domain.client.Client model =
                    clientDomainService.get(clientModel);
            try {
                Workbook workbook = clientExcelService.riskReport(model);
                httpServletResponse.setHeader("Content-disposition", "attachment; filename=report.xlsx");
                httpServletResponse.setContentType("application/vnd.ms-excel");
                workbook.write(httpServletResponse.getOutputStream());
                httpServletResponse.flushBuffer();
                workbook.close();
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage());
            }
        }
    }
}

package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.InwardTruckInvoiceForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckInvoice;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardTruckInvoiceService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardTruckService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>InwardTruckInvoice controller</h1>
 * <p>
 * This inward truck invoice controller class provides the CRUD actions for inward truck invoices
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inward-truck-invoice")
public class InwardTruckInvoiceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardTruckInvoiceController.class);

    @Autowired
    private InwardTruckInvoiceService inwardTruckInvoiceService;

    @Autowired
    private InwardTruckService inwardTruckService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/inwardTruck/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("InwardTruckInvoice index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<InwardTruckInvoice> inwardTruckInvoiceList = new ArrayList<>();
        Boolean needsInvoiceDetails = false;
        Optional<InwardTruck> inwardTruck = inwardTruckService.get(id);
        if (inwardTruck.isPresent()) {
            InwardTruck inwardTruckModel = inwardTruck.get();
            if (inwardTruckModel.getSupplier() != null) {
                needsInvoiceDetails = true;
            }
            inwardTruckInvoiceList = inwardTruckInvoiceService.getByInwardTruck(inwardTruckModel);
            modelAndView.addObject("inwardTruck", inwardTruckModel);
        }
        if (inwardTruckInvoiceList.size() > 0) {
            modelAndView.addObject("models", inwardTruckInvoiceList);
            modelAndView.setViewName("admin/warehouse/inward-truck-invoice/index");
        } else {
            if (needsInvoiceDetails) {
                modelAndView.setViewName("redirect:/admin/inward-truck-invoice/edit/inwardTruck/" + id);
            } else {
                modelAndView.setViewName("redirect:/admin/inward-truck-quality-check/edit/inwardTruck/" + id);
            }
        }
        LOGGER.info("InwardTruckInvoice index action returned {} models", inwardTruckInvoiceList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/inwardTruck/{inwardTruckId}", "/edit/inwardTruck/{inwardTruckId}/{id}"})
    public ModelAndView edit(
            @PathVariable("inwardTruckId") Integer inwardTruckId,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("InwardTruckInvoice edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InwardTruckInvoiceForm model = new InwardTruckInvoiceForm();
        Optional<InwardTruck> inwardTruck = inwardTruckService.get(inwardTruckId);
        if (inwardTruck.isPresent()) {
            InwardTruck inwardTruckModel = inwardTruck.get();
            InwardTruckInvoice inwardTruckInvoiceModel = new InwardTruckInvoice();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("InwardTruckInvoice edit action called on existing model with id {}", identifier);
                Optional<InwardTruckInvoice> inwardTruckInvoice = inwardTruckInvoiceService.get(identifier);
                if (inwardTruckInvoice.isPresent()) {
                    inwardTruckInvoiceModel = inwardTruckInvoice.get();
                }
            }
            inwardTruckInvoiceModel.setInwardTruck(inwardTruckModel);
            model.setInwardTruckInvoice(inwardTruckInvoiceModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/inward-truck-invoice/edit");
        LOGGER.info("InwardTruckInvoice edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InwardTruckInvoiceForm inwardTruckInvoiceForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InwardTruckInvoice save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inward-truck-invoice/edit");
        if (!result.hasErrors()) {
            try {
                if (inwardTruckInvoiceForm.getInvoiceDocument().isEmpty()) {
                    if (inwardTruckInvoiceForm.getInwardTruckInvoice().getId() != null) {
                        Optional<InwardTruckInvoice> existingInwardTruckInvoice =
                                inwardTruckInvoiceService.get(inwardTruckInvoiceForm.getInwardTruckInvoice().getId());
                        if (existingInwardTruckInvoice.isPresent()) {
                            inwardTruckInvoiceForm.getInwardTruckInvoice()
                                    .setInvoiceDocument(existingInwardTruckInvoice.get().getInvoiceDocument());
                        }
                    }
                } else {
                    MultipartFile file = inwardTruckInvoiceForm.getInvoiceDocument();
                    Document document = documentService.store(file);
                    inwardTruckInvoiceForm.getInwardTruckInvoice().setInvoiceDocument(document);
                }
                inwardTruckInvoiceService.edit(inwardTruckInvoiceForm.getInwardTruckInvoice());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("InwardTruckInvoice save action successful");
                modelAndView
                        .setViewName("redirect:/admin/inward-truck-quality-check/index/inwardTruck/" + inwardTruckInvoiceForm
                                .getInwardTruckInvoice()
                                .getInwardTruck()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("InwardTruckInvoice save action had errors {}", result.getAllErrors());
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
        LOGGER.info("InwardTruckInvoice delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer inwardTruckId = null;
        try {
            Optional<InwardTruckInvoice> inwardTruckInvoice = inwardTruckInvoiceService.get(id);
            if (inwardTruckInvoice.isPresent()) {
                InwardTruckInvoice model = inwardTruckInvoice.get();
                inwardTruckId = model.getInwardTruck().getId();
                inwardTruckInvoiceService.delete(id);
                LOGGER.info("InwardTruckInvoice delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inward-truck-invoice/index/inwardTruck/" + inwardTruckId);
        return modelAndView;
    }

}

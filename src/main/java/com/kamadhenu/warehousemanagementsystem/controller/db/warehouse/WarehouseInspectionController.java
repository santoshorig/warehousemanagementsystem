package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseInspectionForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseInspectionTypesForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseInspectionsForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentPurposeService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
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
import java.util.*;

/**
 * <h1>WarehouseInspection controller</h1>
 * <p>
 * This warehouse inspection controller class provides the CRUD actions for warehouse inspections
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-inspection")
public class WarehouseInspectionController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseInspectionController.class);

    @Autowired
    private WarehouseInspectionService warehouseInspectionService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseRemarkService warehouseRemarkService;

    @Autowired
    private InspectionTypeService inspectionTypeService;

    @Autowired
    private InspectionOptionService inspectionOptionService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentPurposeService documentPurposeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/warehouse/{warehouseId}"})
    public ModelAndView edit(
            @PathVariable("warehouseId") Integer warehouseId
    ) {
        LOGGER.info("WarehouseInspection edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseInspectionsForm model = new WarehouseInspectionsForm();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            model.setWarehouse(warehouseModel);
            List<InspectionType> inspectionTypeList = inspectionTypeService.getAll();
            ///TODO This is not great code
            Map<Inspection, List<InspectionOption>> inspectionListMap = new HashMap<>();
            List<InspectionOption> inspectionOptionList = inspectionOptionService.getAll();
            for (InspectionOption inspectionOption : inspectionOptionList) {
                Inspection inspection = inspectionOption.getInspection();
                if (!inspectionListMap.containsKey(inspection)) {
                    inspectionListMap.put(inspection, new ArrayList<>());
                }
                inspectionListMap.get(inspection).add(inspectionOption);
            }

            List<WarehouseInspection> warehouseInspectionList =
                    warehouseInspectionService.getByWarehouse(warehouseModel);
            Map<Inspection, WarehouseInspection> warehouseInspectionMap = new HashMap<>();
            for (WarehouseInspection warehouseInspection : warehouseInspectionList) {
                warehouseInspectionMap
                        .put(warehouseInspection.getInspectionOption().getInspection(), warehouseInspection);
            }

            Map<InspectionType, List<WarehouseInspectionForm>> warehouseInspectionFormMap = new HashMap<>();
            for (Map.Entry<Inspection, List<InspectionOption>> ilm : inspectionListMap.entrySet()) {
                WarehouseInspectionForm warehouseInspectionForm = new WarehouseInspectionForm();
                warehouseInspectionForm.setInspection(ilm.getKey());
                warehouseInspectionForm.setInspectionOption(ilm.getValue());
                WarehouseInspection warehouseInspection = new WarehouseInspection();
                if (warehouseInspectionMap.containsKey(ilm.getKey())) {
                    warehouseInspection = warehouseInspectionMap.get(ilm.getKey());
                }
                warehouseInspection.setWarehouse(warehouseModel);
                warehouseInspectionForm.setWarehouseInspection(warehouseInspection);
                if (!warehouseInspectionFormMap.containsKey(ilm.getKey().getInspectionType())) {
                    warehouseInspectionFormMap.put(ilm.getKey().getInspectionType(), new ArrayList<>());
                }
                warehouseInspectionFormMap.get(ilm.getKey().getInspectionType()).add(warehouseInspectionForm);
            }

            List<WarehouseInspectionTypesForm> warehouseInspectionTypesFormList = new ArrayList<>();
            for (Map.Entry<InspectionType, List<WarehouseInspectionForm>> wlm : warehouseInspectionFormMap.entrySet()) {
                WarehouseInspectionTypesForm warehouseInspectionTypesForm = new WarehouseInspectionTypesForm();
                warehouseInspectionTypesForm.setInspectionType(wlm.getKey());
                warehouseInspectionTypesForm.setWarehouseInspectionForm(wlm.getValue());
                warehouseInspectionTypesFormList.add(warehouseInspectionTypesForm);
            }

            model.setWarehouseInspectionTypesForm(warehouseInspectionTypesFormList);
            model.setWarehouse(warehouseModel);
        }
        List<DocumentType> documentTypeList = new ArrayList<>();
        Optional<DocumentPurpose> documentPurpose =
                documentPurposeService.getByName(constantService.getDOCUMENT_PURPOSE().get("inspection"));
        if (documentPurpose.isPresent()) {
            documentTypeList = documentTypeService.getByDocumentPurpose(documentPurpose.get());
        }

        modelAndView.addObject("model", model);
        modelAndView.addObject("documentTypes", documentTypeList);
        modelAndView.setViewName("admin/warehouse/warehouse-inspection/edit");
        LOGGER.info("WarehouseInspection edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseInspectionsForm warehouseInspectionsForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseInspection save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-inspection/edit");
        if (!result.hasErrors()) {
            try {
                Warehouse warehouse = warehouseInspectionsForm.getWarehouse();
                for (WarehouseInspectionTypesForm warehouseInspectionTypesForm : warehouseInspectionsForm
                        .getWarehouseInspectionTypesForm()) {
                    for (WarehouseInspectionForm warehouseInspectionForm : warehouseInspectionTypesForm
                            .getWarehouseInspectionForm()) {
                        WarehouseInspection warehouseInspection = warehouseInspectionForm.getWarehouseInspection();
                        if (!warehouseInspectionForm.getUpload().isEmpty() && warehouseInspection
                                .getDocumentType() != null) {
                            MultipartFile file = warehouseInspectionForm.getUpload();
                            Document document = documentService.store(file);
                            ///TODO need to delete old documents as well
                            warehouseInspection.setDocument(document);
                        }
                        warehouseInspectionService.edit(warehouseInspection);
                    }
                }
                WarehouseRemark warehouseRemark = new WarehouseRemark();
                warehouseRemark.setRemark(warehouseInspectionsForm.getRemark());
                warehouseRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                warehouseRemark.setRemarkDate(new Date());
                warehouseRemark.setWarehouse(warehouse);
                warehouseRemark.setUser(helperService.getLoggedInDbUser());
                warehouseRemarkService.edit(warehouseRemark);
                if (warehouseInspectionsForm.getReview()) {
                    if (warehouse.getStatus()
                            .equalsIgnoreCase(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessreview"))) {
                        warehouse.setStatus(constantService.getWAREHOUSE_STATUS().get("pendingforriskassessment"));
                    } else {
                        warehouse.setStatus(constantService.getWAREHOUSE_STATUS().get("pendingforbusinessreview"));
                    }
                } else {
                    warehouse.setStatus(constantService.getWAREHOUSE_STATUS().get("rework"));
                }
                warehouseService.edit(warehouse);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseInspection save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.addObject("stacktrace", helperService.stackTraceToString(e));
            }
        } else {
            LOGGER.error("WarehouseInspection save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseInspection delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseInspection> warehouseInspection = warehouseInspectionService.get(id);
            if (warehouseInspection.isPresent()) {
                WarehouseInspection model = warehouseInspection.get();
                warehouseId = model.getWarehouse().getId();
                warehouseInspectionService.delete(id);
                LOGGER.info("WarehouseInspection delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-inspection/index/warehouse/" + warehouseId);
        return modelAndView;
    }

}

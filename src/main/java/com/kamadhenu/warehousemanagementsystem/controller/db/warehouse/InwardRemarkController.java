package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardService;
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
 * <h1>InwardRemark controller</h1>
 * <p>
 * This inward remark controller class provides the CRUD actions for inward remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inward-remark")
public class InwardRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardRemarkController.class);

    @Autowired
    private InwardRemarkService inwardRemarkService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("InwardRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            inwardRemarkService.delete(id);
            LOGGER.info("InwardRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
            modelAndView.addObject("error", e);
            modelAndView.setViewName("error/500");
        }

        modelAndView.setViewName("redirect:/admin/inward-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by inward action
     *
     * @param id the id of inward
     * @return json
     */
    @RequestMapping(value = "/getByInward/{id}", method = RequestMethod.GET)
    public ResponseEntity getByInward(@PathVariable Integer id) {
        Optional<Inward> inward = inwardService.get(id);
        List<InwardRemark> inwardRemarkList = new ArrayList<>();
        if (inward.isPresent()) {
            Inward model = inward.get();
            inwardRemarkList = inwardRemarkService.getByInward(model);
        }
        return new ResponseEntity(inwardRemarkList, HttpStatus.OK);
    }
}

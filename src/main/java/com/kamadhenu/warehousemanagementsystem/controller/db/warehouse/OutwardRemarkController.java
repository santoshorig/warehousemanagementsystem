package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardService;
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
 * <h1>OutwardRemark controller</h1>
 * <p>
 * This outward remark controller class provides the CRUD actions for outward remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/outward-remark")
public class OutwardRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutwardRemarkController.class);

    @Autowired
    private OutwardRemarkService outwardRemarkService;

    @Autowired
    private OutwardService outwardService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("OutwardRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            outwardRemarkService.delete(id);
            LOGGER.info("OutwardRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/outward-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by outward action
     *
     * @param id the id of outward
     * @return json
     */
    @RequestMapping(value = "/getByOutward/{id}", method = RequestMethod.GET)
    public ResponseEntity getByOutward(@PathVariable Integer id) {
        Optional<Outward> outward = outwardService.get(id);
        List<OutwardRemark> outwardRemarkList = new ArrayList<>();
        if (outward.isPresent()) {
            Outward model = outward.get();
            outwardRemarkList = outwardRemarkService.getByOutward(model);
        }
        return new ResponseEntity(outwardRemarkList, HttpStatus.OK);
    }
}

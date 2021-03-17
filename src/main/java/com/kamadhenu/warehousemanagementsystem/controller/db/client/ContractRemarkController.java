package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
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
 * <h1>ContractRemark controller</h1>
 * <p>
 * This contract remark controller class provides the CRUD actions for contract remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/contract-remark")
public class ContractRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractRemarkController.class);

    @Autowired
    private ContractRemarkService clientRemarkService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("ContractRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientRemarkService.delete(id);
            LOGGER.info("ContractRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/contract-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by contract action
     *
     * @param id the id of contract
     * @return json
     */
    @RequestMapping(value = "/getByContract/{id}", method = RequestMethod.GET)
    public ResponseEntity getByContract(@PathVariable Integer id) {
        Optional<Contract> contract = contractService.get(id);
        List<ContractRemark> contractRemarkList = new ArrayList<ContractRemark>();
        if (contract.isPresent()) {
            Contract model = contract.get();
            contractRemarkList = clientRemarkService.getByContract(model);
        }
        return new ResponseEntity(contractRemarkList, HttpStatus.OK);
    }
}

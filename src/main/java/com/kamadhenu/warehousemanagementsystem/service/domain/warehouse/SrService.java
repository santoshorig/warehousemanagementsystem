package com.kamadhenu.warehousemanagementsystem.service.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.*;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrRemark;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDocument;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseRemark;
import com.kamadhenu.warehousemanagementsystem.model.domain.common.Remark;
import com.kamadhenu.warehousemanagementsystem.model.domain.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.SrInwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.service.db.client.*;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Sr domain service
 */
@Service("srService")
public class SrService {

    private final com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SrService srService;

    private final SrInwardTruckQualityCheckService srInwardTruckQualityCheckService;

    private final SrRemarkService srRemarkService;

    private final ContractCommodityInsuranceService contractCommodityInsuranceService;

    private final ClientRemarkService clientRemarkService;

    private final WarehouseRemarkService warehouseRemarkService;

    private final ContractRemarkService contractRemarkService;

    private final ClientDocumentService clientDocumentService;

    private final WarehouseDocumentService warehouseDocumentService;

    private final ContractDocumentService contractDocumentService;

    private final InwardService inwardService;

    private final InwardTruckService inwardTruckService;

    private final com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService inwardDomainService;

    @Autowired
    public SrService(
            com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SrService srService,
            SrInwardTruckQualityCheckService srInwardTruckQualityCheckService,
            SrRemarkService srRemarkService,
            ContractCommodityInsuranceService contractCommodityInsuranceService,
            ClientRemarkService clientRemarkService,
            WarehouseRemarkService warehouseRemarkService,
            ContractRemarkService contractRemarkService,
            ClientDocumentService clientDocumentService,
            WarehouseDocumentService warehouseDocumentService,
            ContractDocumentService contractDocumentService,
            InwardService inwardService,
            InwardTruckService inwardTruckService,
            com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService inwardDomainService
    ) {
        this.srService = srService;
        this.srInwardTruckQualityCheckService = srInwardTruckQualityCheckService;
        this.srRemarkService = srRemarkService;
        this.contractCommodityInsuranceService = contractCommodityInsuranceService;
        this.clientRemarkService = clientRemarkService;
        this.warehouseRemarkService = warehouseRemarkService;
        this.contractRemarkService = contractRemarkService;
        this.clientDocumentService = clientDocumentService;
        this.warehouseDocumentService = warehouseDocumentService;
        this.contractDocumentService = contractDocumentService;
        this.inwardService = inwardService;
        this.inwardTruckService = inwardTruckService;
        this.inwardDomainService = inwardDomainService;
    }

    /**
     * Get domain sr based on database sr
     *
     * @param srModel
     * @return
     */
    public Sr get(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr srModel) {
        Sr sr = new Sr();
        sr.setSr(srModel);

        List<Inward> inwardList = new ArrayList<>();
        List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList = new ArrayList<>();
        List<com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck>
                existingSrInwardTruckQualityCheckList =
                srInwardTruckQualityCheckService.getBySr(srModel);
        for (com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck existingSrInwardTruckQualityCheck :
                existingSrInwardTruckQualityCheckList) {
            SrInwardTruckQualityCheck srInwardTruckQualityCheck = new SrInwardTruckQualityCheck();
            srInwardTruckQualityCheck.setSrInwardTruckQualityCheck(existingSrInwardTruckQualityCheck);
            srInwardTruckQualityCheckList.add(srInwardTruckQualityCheck);
            inwardList.add(existingSrInwardTruckQualityCheck.getInwardTruck().getInward());
        }
        sr.setSrInwardTruckQualityCheckList(srInwardTruckQualityCheckList);

        List<SrRemark> srRemarkList = srRemarkService.getBySr(srModel);
        sr.setSrRemarkList(srRemarkList);

        List<Insurance> insuranceList = new ArrayList<>();
        List<String> ownInsurance = new ArrayList<>();
        List<ContractCommodityInsurance> contractCommodityInsuranceList =
                contractCommodityInsuranceService.getByContract(srModel.getContract());
        for (ContractCommodityInsurance contractCommodityInsurance : contractCommodityInsuranceList) {
            if (contractCommodityInsurance.getInsurance() != null) {
                insuranceList.add(contractCommodityInsurance.getInsurance());
            } else if (contractCommodityInsurance.getInsuranceOwner() != null) {
                ownInsurance.add(contractCommodityInsurance.getInsuranceOwner());
            }
        }
        sr.setInsuranceList(insuranceList);
        sr.setOwnInsuranceList(ownInsurance);


        // Get remarks
        // get client remarks
        List<Remark> remarkList = new ArrayList<>();
        List<ClientRemark> clientRemarkList = clientRemarkService.getByClient(srModel.getContract().getClient());
        for (ClientRemark clientRemark : clientRemarkList) {
            Remark remark = new Remark();
            remark.setRemarkFor("client");
            remark.setRemark(clientRemark.getRemark());
            remark.setRemarkDate(clientRemark.getRemarkDate());
            remark.setRemarkUser(clientRemark.getUser());
            remarkList.add(remark);
        }
        sr.setClientRemarkList(remarkList);

        // get warehouse remarks
        remarkList = new ArrayList<>();
        List<WarehouseRemark> warehouseRemarkList =
                warehouseRemarkService.getByWarehouse(srModel.getContract().getWarehouse());
        for (WarehouseRemark warehouseRemark : warehouseRemarkList) {
            Remark remark = new Remark();
            remark.setRemarkFor("warehouse");
            remark.setRemark(warehouseRemark.getRemark());
            remark.setRemarkDate(warehouseRemark.getRemarkDate());
            remark.setRemarkUser(warehouseRemark.getUser());
            remarkList.add(remark);
        }
        sr.setWarehouseRemarkList(remarkList);

        // get contract remarks
        remarkList = new ArrayList<>();
        List<ContractRemark> contractRemarkList = contractRemarkService.getByContract(srModel.getContract());
        for (ContractRemark contractRemark : contractRemarkList) {
            Remark remark = new Remark();
            remark.setRemarkFor("contract");
            remark.setRemark(contractRemark.getRemark());
            remark.setRemarkDate(contractRemark.getRemarkDate());
            remark.setRemarkUser(contractRemark.getUser());
            remarkList.add(remark);
        }
        sr.setContractRemarkList(remarkList);

        // Get documents
        // get client documents
        List<Document> documentList = new ArrayList<>();
        List<ClientDocument> clientDocumentList = clientDocumentService.getByClient(srModel.getContract().getClient());
        for (ClientDocument clientDocument : clientDocumentList) {
            Document document = new Document();
            document.setDocumentFor("client");
            document.setDocumentType(clientDocument.getDocumentType().getName());
            document.setDocumentId(clientDocument.getDocument().getId());
            documentList.add(document);
        }
        sr.setClientDocumentList(documentList);

        // get warehouse documents
        documentList = new ArrayList<>();
        List<WarehouseDocument> warehouseDocumentList =
                warehouseDocumentService.getByWarehouse(srModel.getContract().getWarehouse());
        for (WarehouseDocument warehouseDocument : warehouseDocumentList) {
            Document document = new Document();
            document.setDocumentFor("warehouse");
            document.setDocumentType(warehouseDocument.getDocumentType().getName());
            document.setDocumentId(warehouseDocument.getDocument().getId());
            documentList.add(document);
        }
        sr.setWarehouseDocumentList(documentList);

        // get contract documents
        documentList = new ArrayList<>();
        List<ContractDocument> contractDocumentList = contractDocumentService.getByContract(srModel.getContract());
        for (ContractDocument contractDocument : contractDocumentList) {
            Document document = new Document();
            document.setDocumentFor("contract");
            document.setDocumentType(contractDocument.getDocumentType().getName());
            document.setDocumentId(contractDocument.getDocument().getId());
            documentList.add(document);
        }
        sr.setContractDocumentList(documentList);

        // Get unique inwards
        documentList = new ArrayList<>();
        HashSet<Inward> inwardHashSet = new HashSet(inwardList);
        for (Inward existingInwardModel : inwardHashSet) {
            // inward document
            com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                    cadDocument = new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
            cadDocument.setDocumentFor("inward");
            cadDocument.setDocumentType("CAD");
            cadDocument.setDocumentId(existingInwardModel.getCadDocument().getId());
            documentList.add(cadDocument);

            com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                    cddDocument = new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
            cddDocument.setDocumentFor("inward");
            cddDocument.setDocumentType("CDD");
            cddDocument.setDocumentId(existingInwardModel.getCddDocument().getId());
            documentList.add(cddDocument);

            // truck documents
            com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Inward domainInwardModel =
                    inwardDomainService.get(existingInwardModel);
            for (com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck domainInwardTruck : domainInwardModel
                    .getInwardTruckList()) {
                com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck inwardTruck =
                        domainInwardTruck.getInwardTruck();
                if (inwardTruck.getGatePassDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Gate Pass");
                    truckDocument.setDocumentId(inwardTruck.getGatePassDocument().getId());
                    documentList.add(truckDocument);
                }
                if (inwardTruck.getWeighmentDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Weighment Document");
                    truckDocument.setDocumentId(inwardTruck.getWeighmentDocument().getId());
                    documentList.add(truckDocument);
                }
                if (inwardTruck.getMandiReceiptDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Mandi Receipt Document");
                    truckDocument.setDocumentId(inwardTruck.getMandiReceiptDocument().getId());
                    documentList.add(truckDocument);
                }
                if (inwardTruck.getQualityCheckDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Quality Check Document");
                    truckDocument.setDocumentId(inwardTruck.getQualityCheckDocument().getId());
                    documentList.add(truckDocument);
                }
            }
            sr.setInwardDocumentList(documentList);
        }

        return sr;
    }
}

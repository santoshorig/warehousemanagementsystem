package com.kamadhenu.warehousemanagementsystem.service.domain.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientAddress;
import com.kamadhenu.warehousemanagementsystem.model.domain.client.Client;
import com.kamadhenu.warehousemanagementsystem.service.db.client.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Client domain service
 */
@Service("clientService")
public class ClientService {

    @Autowired
    private ClientPartnerService clientPartnerService;

    @Autowired
    private ClientAddressService clientAddressService;

    @Autowired
    private ClientBankService clientBankService;

    @Autowired
    private ClientSignatoryService clientSignatoryService;

    @Autowired
    private ClientDocumentService clientDocumentService;

    @Autowired
    private ConstantService constantService;

    /**
     * Get domain client based on database client
     *
     * @param clientModel
     * @return
     */
    public Client get(com.kamadhenu.warehousemanagementsystem.model.db.client.Client clientModel) {
        Client client = new Client();
        client.setClient(clientModel);
        client.setClientPartner(clientPartnerService.getByClient(clientModel));
        client.setClientBank(clientBankService.getByClient(clientModel));
        client.setClientDocument(clientDocumentService.getByClient(clientModel));
        client.setClientSignatory(clientSignatoryService.getByClient(clientModel));

        List<ClientAddress> clientAddressList = clientAddressService.getByClient(clientModel);
        for (ClientAddress clientAddressModel : clientAddressList) {
            if (constantService.getCLIENT_ADDRESS_TYPES().get("office")
                    .equalsIgnoreCase(clientAddressModel.getAddressType())) {
                client.setBusinessAddress(clientAddressModel.getFullAddress());
            }
        }

        return client;
    }

}

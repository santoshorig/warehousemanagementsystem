package com.kamadhenu.warehousemanagementsystem.repository.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Client Remark repository class
 */
@Repository
public interface ClientRemarkRepository extends JpaRepository<ClientRemark, Integer> {

    /**
     * Get client remark basis of client ordered by remark date descending
     *
     * @param client
     * @return
     */
    List<ClientRemark> findByClientOrderByIdDescRemarkDateDesc(Client client);

}

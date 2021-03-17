package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Client Commodity model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@Table(name = "client_commodity")
public class ClientCommodity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "commodity")
    private Commodity commodity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    private Client client;

    @Override
    public String toString() {
        return "ClientCommodity{" +
                "id=" + id +
                '}';
    }
}

package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Contract Document model
 */
@Entity
@Audited
@Data
@ToString
@EqualsAndHashCode
@Table(name = "contract_document")
public class ContractDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "document")
    private Document document;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "contract")
    private Contract contract;
}

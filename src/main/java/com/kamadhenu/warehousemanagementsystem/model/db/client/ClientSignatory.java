package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Client Signatory model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@Table(name = "client_signatory")
public class ClientSignatory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotNull
    @Size(min = 2, max = 255)
    private String title;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String firstName;

    @Column(name = "middle_name")
    @Size(min = 0, max = 255)
    private String middleName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String lastName;

    @Column(name = "designation")
    @Size(min = 0, max = 255)
    private String designation;

    @Column(name = "mobile_number")
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = ".*(^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$)")
    private String mobileNumber;

    @Column(name = "valid_from")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validFrom;

    @Column(name = "valid_to")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validTo;

    @Column(name = "signature_document_number")
    @Size(min = 0, max = 255)
    private String signatureDocumentNumber;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_mode_of_operation")
    private ClientModeOfOperation clientModeOfOperation;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "jointly_with")
    private ClientSignatory jointlyWith;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "signature_document_type")
    private DocumentType signatureDocumentType;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "signature_document")
    private Document signatureDocument;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    /**
     * Get full name of client signatory
     *
     * @return
     */
    public String getName() {
        return title + " " + firstName + " " + middleName + " " + lastName;
    }

    /**
     * Get valid client signatory
     *
     * @return
     */
    @JsonIgnore
    public boolean getValid() {
        Boolean isValid = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            Date currentDate = sdf.parse(date);
            String date1 = sdf.format(validTo);
            Date validTo = sdf.parse(date1);
            String date2 = sdf.format(validFrom);
            Date validFrom = sdf.parse(date2);
            isValid = (validTo.compareTo(currentDate) >= 0 && validFrom.compareTo(currentDate) <= 0);
        } catch (ParseException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String toString() {
        return "ClientSignatory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", designation='" + designation + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", signatureDocumentNumber='" + signatureDocumentNumber + '\'' +
                ", clientModeOfOperation=" + clientModeOfOperation +
                ", signatureDocumentType=" + signatureDocumentType +
                ", signatureDocument=" + signatureDocument +
                ", client=" + client +
                '}';
    }
}

package com.kamadhenu.warehousemanagementsystem.model.db.client;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Client Address model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "client_address")
public class ClientAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "line_1")
    @NotNull
    @Size(min = 2, max = 255)
    private String line1;

    @Column(name = "line_2")
    @Size(min = 0, max = 255)
    private String line2;

    @Column(name = "line_3")
    @Size(min = 0, max = 255)
    private String line3;

    @Column(name = "address_type")
    @NotNull
    @Size(min = 2, max = 255)
    private String addressType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    /**
     * Create client address from existing model
     *
     * @param clientAddress
     */
    public ClientAddress(ClientAddress clientAddress) {
        this.line1 = clientAddress.getLine1();
        this.line2 = clientAddress.getLine2();
        this.line3 = clientAddress.getLine3();
        this.addressType = clientAddress.getAddressType();
        this.location = clientAddress.getLocation();
        this.client = clientAddress.getClient();
    }

    /**
     * Get full address of client signatory
     *
     * @return
     */
    public String getFullAddress() {
        return line1 + " " + line2 + " " + line3 + " - " + location.getName() + ", " + location
                .getPinCode() + " - " + location.getDistrict()
                .getName() + " - " + location.getDistrict().getState().getName();
    }

    @Override
    public String toString() {
        return "ClientAddress{" +
                "id=" + id +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", addressType='" + addressType + '\'' +
                '}';
    }
}

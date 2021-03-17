package com.kamadhenu.warehousemanagementsystem.model.db.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Document model
 */
@Entity
@Audited
@Data
@EqualsAndHashCode
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String fileName;

    @Column(name = "file_type")
    @NotNull
    @Size(min = 2, max = 255)
    private String fileType;

    @Column(name = "file_size")
    @NotNull
    private Integer fileSize;

    @Column(name = "file_content")
    @NotNull
    @Lob
    private byte[] fileContent;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}

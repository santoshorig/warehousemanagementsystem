package com.kamadhenu.warehousemanagementsystem.repository.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Document repository class
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

}

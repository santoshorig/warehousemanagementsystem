package com.kamadhenu.warehousemanagementsystem.repository.document;

import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Document Purpose repository class
 */
@Repository
public interface DocumentPurposeRepository extends JpaRepository<DocumentPurpose, Integer> {

    /**
     * Get document purpose
     *
     * @param name
     * @return
     */
    Optional<DocumentPurpose> findByName(String name);
}

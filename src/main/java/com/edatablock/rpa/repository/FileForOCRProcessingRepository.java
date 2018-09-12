package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.FileForOCRProcessing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileForOCRProcessing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileForOCRProcessingRepository extends JpaRepository<FileForOCRProcessing, Long> {

}

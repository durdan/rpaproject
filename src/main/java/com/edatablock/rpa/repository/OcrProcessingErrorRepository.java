package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.OcrProcessingError;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OcrProcessingError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcrProcessingErrorRepository extends JpaRepository<OcrProcessingError, Long> {

}

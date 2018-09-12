package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.EmailProcessingError;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmailProcessingError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailProcessingErrorRepository extends JpaRepository<EmailProcessingError, Long> {

}

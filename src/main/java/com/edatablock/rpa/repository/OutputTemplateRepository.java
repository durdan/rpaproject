package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.OutputTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OutputTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutputTemplateRepository extends JpaRepository<OutputTemplate, Long> {

}

package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.InputTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InputTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InputTemplateRepository extends JpaRepository<InputTemplate, Long> {

}

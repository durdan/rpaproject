package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.TemplateFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TemplateFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateFieldsRepository extends JpaRepository<TemplateFields, Long> {

}

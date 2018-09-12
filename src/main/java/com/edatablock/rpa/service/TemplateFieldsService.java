package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.TemplateFieldsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TemplateFields.
 */
public interface TemplateFieldsService {

    /**
     * Save a templateFields.
     *
     * @param templateFieldsDTO the entity to save
     * @return the persisted entity
     */
    TemplateFieldsDTO save(TemplateFieldsDTO templateFieldsDTO);

    /**
     * Get all the templateFields.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TemplateFieldsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" templateFields.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TemplateFieldsDTO> findOne(Long id);

    /**
     * Delete the "id" templateFields.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

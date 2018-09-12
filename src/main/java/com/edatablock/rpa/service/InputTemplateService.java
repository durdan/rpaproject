package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.InputTemplateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing InputTemplate.
 */
public interface InputTemplateService {

    /**
     * Save a inputTemplate.
     *
     * @param inputTemplateDTO the entity to save
     * @return the persisted entity
     */
    InputTemplateDTO save(InputTemplateDTO inputTemplateDTO);

    /**
     * Get all the inputTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InputTemplateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" inputTemplate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InputTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" inputTemplate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

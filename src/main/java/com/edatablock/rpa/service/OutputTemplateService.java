package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.OutputTemplateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OutputTemplate.
 */
public interface OutputTemplateService {

    /**
     * Save a outputTemplate.
     *
     * @param outputTemplateDTO the entity to save
     * @return the persisted entity
     */
    OutputTemplateDTO save(OutputTemplateDTO outputTemplateDTO);

    /**
     * Get all the outputTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OutputTemplateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" outputTemplate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OutputTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" outputTemplate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

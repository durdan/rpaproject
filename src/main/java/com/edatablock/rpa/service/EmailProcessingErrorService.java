package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.EmailProcessingErrorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EmailProcessingError.
 */
public interface EmailProcessingErrorService {

    /**
     * Save a emailProcessingError.
     *
     * @param emailProcessingErrorDTO the entity to save
     * @return the persisted entity
     */
    EmailProcessingErrorDTO save(EmailProcessingErrorDTO emailProcessingErrorDTO);

    /**
     * Get all the emailProcessingErrors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmailProcessingErrorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" emailProcessingError.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmailProcessingErrorDTO> findOne(Long id);

    /**
     * Delete the "id" emailProcessingError.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

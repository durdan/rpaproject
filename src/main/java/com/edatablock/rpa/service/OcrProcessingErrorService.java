package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.OcrProcessingErrorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OcrProcessingError.
 */
public interface OcrProcessingErrorService {

    /**
     * Save a ocrProcessingError.
     *
     * @param ocrProcessingErrorDTO the entity to save
     * @return the persisted entity
     */
    OcrProcessingErrorDTO save(OcrProcessingErrorDTO ocrProcessingErrorDTO);

    /**
     * Get all the ocrProcessingErrors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OcrProcessingErrorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ocrProcessingError.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OcrProcessingErrorDTO> findOne(Long id);

    /**
     * Delete the "id" ocrProcessingError.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

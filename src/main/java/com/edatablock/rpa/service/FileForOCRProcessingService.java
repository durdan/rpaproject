package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.FileForOCRProcessingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing FileForOCRProcessing.
 */
public interface FileForOCRProcessingService {

    /**
     * Save a fileForOCRProcessing.
     *
     * @param fileForOCRProcessingDTO the entity to save
     * @return the persisted entity
     */
    FileForOCRProcessingDTO save(FileForOCRProcessingDTO fileForOCRProcessingDTO);

    /**
     * Get all the fileForOCRProcessings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FileForOCRProcessingDTO> findAll(Pageable pageable);
    /**
     * Get all the FileForOCRProcessingDTO where Transaction is null.
     *
     * @return the list of entities
     */
    List<FileForOCRProcessingDTO> findAllWhereTransactionIsNull();


    /**
     * Get the "id" fileForOCRProcessing.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FileForOCRProcessingDTO> findOne(Long id);

    /**
     * Delete the "id" fileForOCRProcessing.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

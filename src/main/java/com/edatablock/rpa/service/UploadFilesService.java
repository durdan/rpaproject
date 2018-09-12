package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.UploadFilesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UploadFiles.
 */
public interface UploadFilesService {

    /**
     * Save a uploadFiles.
     *
     * @param uploadFilesDTO the entity to save
     * @return the persisted entity
     */
    UploadFilesDTO save(UploadFilesDTO uploadFilesDTO);

    /**
     * Get all the uploadFiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UploadFilesDTO> findAll(Pageable pageable);
    /**
     * Get all the UploadFilesDTO where FileForOCRProcessing is null.
     *
     * @return the list of entities
     */
    List<UploadFilesDTO> findAllWhereFileForOCRProcessingIsNull();


    /**
     * Get the "id" uploadFiles.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UploadFilesDTO> findOne(Long id);

    /**
     * Delete the "id" uploadFiles.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

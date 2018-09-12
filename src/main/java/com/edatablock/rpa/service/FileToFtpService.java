package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.FileToFtpDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing FileToFtp.
 */
public interface FileToFtpService {

    /**
     * Save a fileToFtp.
     *
     * @param fileToFtpDTO the entity to save
     * @return the persisted entity
     */
    FileToFtpDTO save(FileToFtpDTO fileToFtpDTO);

    /**
     * Get all the fileToFtps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FileToFtpDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fileToFtp.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FileToFtpDTO> findOne(Long id);

    /**
     * Delete the "id" fileToFtp.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

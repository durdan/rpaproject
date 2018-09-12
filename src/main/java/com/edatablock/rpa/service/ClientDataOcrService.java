package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.ClientDataOcrDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClientDataOcr.
 */
public interface ClientDataOcrService {

    /**
     * Save a clientDataOcr.
     *
     * @param clientDataOcrDTO the entity to save
     * @return the persisted entity
     */
    ClientDataOcrDTO save(ClientDataOcrDTO clientDataOcrDTO);

    /**
     * Get all the clientDataOcrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClientDataOcrDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientDataOcr.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClientDataOcrDTO> findOne(Long id);

    /**
     * Delete the "id" clientDataOcr.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

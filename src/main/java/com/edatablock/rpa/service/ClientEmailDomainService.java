package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.ClientEmailDomainDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClientEmailDomain.
 */
public interface ClientEmailDomainService {

    /**
     * Save a clientEmailDomain.
     *
     * @param clientEmailDomainDTO the entity to save
     * @return the persisted entity
     */
    ClientEmailDomainDTO save(ClientEmailDomainDTO clientEmailDomainDTO);

    /**
     * Get all the clientEmailDomains.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClientEmailDomainDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientEmailDomain.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClientEmailDomainDTO> findOne(Long id);

    /**
     * Delete the "id" clientEmailDomain.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

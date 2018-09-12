package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.EmailMessagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmailMessages.
 */
public interface EmailMessagesService {

    /**
     * Save a emailMessages.
     *
     * @param emailMessagesDTO the entity to save
     * @return the persisted entity
     */
    EmailMessagesDTO save(EmailMessagesDTO emailMessagesDTO);

    /**
     * Get all the emailMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmailMessagesDTO> findAll(Pageable pageable);
    /**
     * Get all the EmailMessagesDTO where EmailProcessingError is null.
     *
     * @return the list of entities
     */
    List<EmailMessagesDTO> findAllWhereEmailProcessingErrorIsNull();


    /**
     * Get the "id" emailMessages.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmailMessagesDTO> findOne(Long id);

    /**
     * Delete the "id" emailMessages.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

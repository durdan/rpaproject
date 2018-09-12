package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.EmailAttachmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmailAttachment.
 */
public interface EmailAttachmentService {

    /**
     * Save a emailAttachment.
     *
     * @param emailAttachmentDTO the entity to save
     * @return the persisted entity
     */
    EmailAttachmentDTO save(EmailAttachmentDTO emailAttachmentDTO);

    /**
     * Get all the emailAttachments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmailAttachmentDTO> findAll(Pageable pageable);
    /**
     * Get all the EmailAttachmentDTO where FileForOCRProcessing is null.
     *
     * @return the list of entities
     */
    List<EmailAttachmentDTO> findAllWhereFileForOCRProcessingIsNull();


    /**
     * Get the "id" emailAttachment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmailAttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" emailAttachment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

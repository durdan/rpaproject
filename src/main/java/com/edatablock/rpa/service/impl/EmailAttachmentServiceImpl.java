package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.EmailAttachmentService;
import com.edatablock.rpa.domain.EmailAttachment;
import com.edatablock.rpa.repository.EmailAttachmentRepository;
import com.edatablock.rpa.service.dto.EmailAttachmentDTO;
import com.edatablock.rpa.service.mapper.EmailAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Service Implementation for managing EmailAttachment.
 */
@Service
@Transactional
public class EmailAttachmentServiceImpl implements EmailAttachmentService {

    private final Logger log = LoggerFactory.getLogger(EmailAttachmentServiceImpl.class);

    private final EmailAttachmentRepository emailAttachmentRepository;

    private final EmailAttachmentMapper emailAttachmentMapper;

    public EmailAttachmentServiceImpl(EmailAttachmentRepository emailAttachmentRepository, EmailAttachmentMapper emailAttachmentMapper) {
        this.emailAttachmentRepository = emailAttachmentRepository;
        this.emailAttachmentMapper = emailAttachmentMapper;
    }

    /**
     * Save a emailAttachment.
     *
     * @param emailAttachmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailAttachmentDTO save(EmailAttachmentDTO emailAttachmentDTO) {
        log.debug("Request to save EmailAttachment : {}", emailAttachmentDTO);
        EmailAttachment emailAttachment = emailAttachmentMapper.toEntity(emailAttachmentDTO);
        emailAttachment = emailAttachmentRepository.save(emailAttachment);
        return emailAttachmentMapper.toDto(emailAttachment);
    }

    /**
     * Get all the emailAttachments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmailAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailAttachments");
        return emailAttachmentRepository.findAll(pageable)
            .map(emailAttachmentMapper::toDto);
    }



    /**
     *  get all the emailAttachments where FileForOCRProcessing is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EmailAttachmentDTO> findAllWhereFileForOCRProcessingIsNull() {
        log.debug("Request to get all emailAttachments where FileForOCRProcessing is null");
        return StreamSupport
            .stream(emailAttachmentRepository.findAll().spliterator(), false)
            .filter(emailAttachment -> emailAttachment.getFileForOCRProcessing() == null)
            .map(emailAttachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one emailAttachment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailAttachmentDTO> findOne(Long id) {
        log.debug("Request to get EmailAttachment : {}", id);
        return emailAttachmentRepository.findById(id)
            .map(emailAttachmentMapper::toDto);
    }

    /**
     * Delete the emailAttachment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailAttachment : {}", id);
        emailAttachmentRepository.deleteById(id);
    }
}

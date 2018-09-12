package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.EmailMessagesService;
import com.edatablock.rpa.domain.EmailMessages;
import com.edatablock.rpa.repository.EmailMessagesRepository;
import com.edatablock.rpa.service.dto.EmailMessagesDTO;
import com.edatablock.rpa.service.mapper.EmailMessagesMapper;
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
 * Service Implementation for managing EmailMessages.
 */
@Service
@Transactional
public class EmailMessagesServiceImpl implements EmailMessagesService {

    private final Logger log = LoggerFactory.getLogger(EmailMessagesServiceImpl.class);

    private final EmailMessagesRepository emailMessagesRepository;

    private final EmailMessagesMapper emailMessagesMapper;

    public EmailMessagesServiceImpl(EmailMessagesRepository emailMessagesRepository, EmailMessagesMapper emailMessagesMapper) {
        this.emailMessagesRepository = emailMessagesRepository;
        this.emailMessagesMapper = emailMessagesMapper;
    }

    /**
     * Save a emailMessages.
     *
     * @param emailMessagesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailMessagesDTO save(EmailMessagesDTO emailMessagesDTO) {
        log.debug("Request to save EmailMessages : {}", emailMessagesDTO);
        EmailMessages emailMessages = emailMessagesMapper.toEntity(emailMessagesDTO);
        emailMessages = emailMessagesRepository.save(emailMessages);
        return emailMessagesMapper.toDto(emailMessages);
    }

    /**
     * Get all the emailMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmailMessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailMessages");
        return emailMessagesRepository.findAll(pageable)
            .map(emailMessagesMapper::toDto);
    }



    /**
     *  get all the emailMessages where EmailProcessingError is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EmailMessagesDTO> findAllWhereEmailProcessingErrorIsNull() {
        log.debug("Request to get all emailMessages where EmailProcessingError is null");
        return StreamSupport
            .stream(emailMessagesRepository.findAll().spliterator(), false)
            .filter(emailMessages -> emailMessages.getEmailProcessingError() == null)
            .map(emailMessagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one emailMessages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailMessagesDTO> findOne(Long id) {
        log.debug("Request to get EmailMessages : {}", id);
        return emailMessagesRepository.findById(id)
            .map(emailMessagesMapper::toDto);
    }

    /**
     * Delete the emailMessages by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailMessages : {}", id);
        emailMessagesRepository.deleteById(id);
    }
}

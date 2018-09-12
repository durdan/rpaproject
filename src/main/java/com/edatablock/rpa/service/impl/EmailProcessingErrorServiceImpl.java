package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.EmailProcessingErrorService;
import com.edatablock.rpa.domain.EmailProcessingError;
import com.edatablock.rpa.repository.EmailProcessingErrorRepository;
import com.edatablock.rpa.service.dto.EmailProcessingErrorDTO;
import com.edatablock.rpa.service.mapper.EmailProcessingErrorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing EmailProcessingError.
 */
@Service
@Transactional
public class EmailProcessingErrorServiceImpl implements EmailProcessingErrorService {

    private final Logger log = LoggerFactory.getLogger(EmailProcessingErrorServiceImpl.class);

    private final EmailProcessingErrorRepository emailProcessingErrorRepository;

    private final EmailProcessingErrorMapper emailProcessingErrorMapper;

    public EmailProcessingErrorServiceImpl(EmailProcessingErrorRepository emailProcessingErrorRepository, EmailProcessingErrorMapper emailProcessingErrorMapper) {
        this.emailProcessingErrorRepository = emailProcessingErrorRepository;
        this.emailProcessingErrorMapper = emailProcessingErrorMapper;
    }

    /**
     * Save a emailProcessingError.
     *
     * @param emailProcessingErrorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailProcessingErrorDTO save(EmailProcessingErrorDTO emailProcessingErrorDTO) {
        log.debug("Request to save EmailProcessingError : {}", emailProcessingErrorDTO);
        EmailProcessingError emailProcessingError = emailProcessingErrorMapper.toEntity(emailProcessingErrorDTO);
        emailProcessingError = emailProcessingErrorRepository.save(emailProcessingError);
        return emailProcessingErrorMapper.toDto(emailProcessingError);
    }

    /**
     * Get all the emailProcessingErrors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmailProcessingErrorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailProcessingErrors");
        return emailProcessingErrorRepository.findAll(pageable)
            .map(emailProcessingErrorMapper::toDto);
    }


    /**
     * Get one emailProcessingError by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailProcessingErrorDTO> findOne(Long id) {
        log.debug("Request to get EmailProcessingError : {}", id);
        return emailProcessingErrorRepository.findById(id)
            .map(emailProcessingErrorMapper::toDto);
    }

    /**
     * Delete the emailProcessingError by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailProcessingError : {}", id);
        emailProcessingErrorRepository.deleteById(id);
    }
}

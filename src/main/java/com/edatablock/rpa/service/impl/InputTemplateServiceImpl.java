package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.InputTemplateService;
import com.edatablock.rpa.domain.InputTemplate;
import com.edatablock.rpa.repository.InputTemplateRepository;
import com.edatablock.rpa.service.dto.InputTemplateDTO;
import com.edatablock.rpa.service.mapper.InputTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing InputTemplate.
 */
@Service
@Transactional
public class InputTemplateServiceImpl implements InputTemplateService {

    private final Logger log = LoggerFactory.getLogger(InputTemplateServiceImpl.class);

    private final InputTemplateRepository inputTemplateRepository;

    private final InputTemplateMapper inputTemplateMapper;

    public InputTemplateServiceImpl(InputTemplateRepository inputTemplateRepository, InputTemplateMapper inputTemplateMapper) {
        this.inputTemplateRepository = inputTemplateRepository;
        this.inputTemplateMapper = inputTemplateMapper;
    }

    /**
     * Save a inputTemplate.
     *
     * @param inputTemplateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InputTemplateDTO save(InputTemplateDTO inputTemplateDTO) {
        log.debug("Request to save InputTemplate : {}", inputTemplateDTO);
        InputTemplate inputTemplate = inputTemplateMapper.toEntity(inputTemplateDTO);
        inputTemplate = inputTemplateRepository.save(inputTemplate);
        return inputTemplateMapper.toDto(inputTemplate);
    }

    /**
     * Get all the inputTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InputTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InputTemplates");
        return inputTemplateRepository.findAll(pageable)
            .map(inputTemplateMapper::toDto);
    }


    /**
     * Get one inputTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InputTemplateDTO> findOne(Long id) {
        log.debug("Request to get InputTemplate : {}", id);
        return inputTemplateRepository.findById(id)
            .map(inputTemplateMapper::toDto);
    }

    /**
     * Delete the inputTemplate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InputTemplate : {}", id);
        inputTemplateRepository.deleteById(id);
    }
}

package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.TemplateFieldsService;
import com.edatablock.rpa.domain.TemplateFields;
import com.edatablock.rpa.repository.TemplateFieldsRepository;
import com.edatablock.rpa.service.dto.TemplateFieldsDTO;
import com.edatablock.rpa.service.mapper.TemplateFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing TemplateFields.
 */
@Service
@Transactional
public class TemplateFieldsServiceImpl implements TemplateFieldsService {

    private final Logger log = LoggerFactory.getLogger(TemplateFieldsServiceImpl.class);

    private final TemplateFieldsRepository templateFieldsRepository;

    private final TemplateFieldsMapper templateFieldsMapper;

    public TemplateFieldsServiceImpl(TemplateFieldsRepository templateFieldsRepository, TemplateFieldsMapper templateFieldsMapper) {
        this.templateFieldsRepository = templateFieldsRepository;
        this.templateFieldsMapper = templateFieldsMapper;
    }

    /**
     * Save a templateFields.
     *
     * @param templateFieldsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TemplateFieldsDTO save(TemplateFieldsDTO templateFieldsDTO) {
        log.debug("Request to save TemplateFields : {}", templateFieldsDTO);
        TemplateFields templateFields = templateFieldsMapper.toEntity(templateFieldsDTO);
        templateFields = templateFieldsRepository.save(templateFields);
        return templateFieldsMapper.toDto(templateFields);
    }

    /**
     * Get all the templateFields.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TemplateFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateFields");
        return templateFieldsRepository.findAll(pageable)
            .map(templateFieldsMapper::toDto);
    }


    /**
     * Get one templateFields by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TemplateFieldsDTO> findOne(Long id) {
        log.debug("Request to get TemplateFields : {}", id);
        return templateFieldsRepository.findById(id)
            .map(templateFieldsMapper::toDto);
    }

    /**
     * Delete the templateFields by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemplateFields : {}", id);
        templateFieldsRepository.deleteById(id);
    }
}

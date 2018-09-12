package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.OutputTemplateService;
import com.edatablock.rpa.domain.OutputTemplate;
import com.edatablock.rpa.repository.OutputTemplateRepository;
import com.edatablock.rpa.service.dto.OutputTemplateDTO;
import com.edatablock.rpa.service.mapper.OutputTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing OutputTemplate.
 */
@Service
@Transactional
public class OutputTemplateServiceImpl implements OutputTemplateService {

    private final Logger log = LoggerFactory.getLogger(OutputTemplateServiceImpl.class);

    private final OutputTemplateRepository outputTemplateRepository;

    private final OutputTemplateMapper outputTemplateMapper;

    public OutputTemplateServiceImpl(OutputTemplateRepository outputTemplateRepository, OutputTemplateMapper outputTemplateMapper) {
        this.outputTemplateRepository = outputTemplateRepository;
        this.outputTemplateMapper = outputTemplateMapper;
    }

    /**
     * Save a outputTemplate.
     *
     * @param outputTemplateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OutputTemplateDTO save(OutputTemplateDTO outputTemplateDTO) {
        log.debug("Request to save OutputTemplate : {}", outputTemplateDTO);
        OutputTemplate outputTemplate = outputTemplateMapper.toEntity(outputTemplateDTO);
        outputTemplate = outputTemplateRepository.save(outputTemplate);
        return outputTemplateMapper.toDto(outputTemplate);
    }

    /**
     * Get all the outputTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OutputTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OutputTemplates");
        return outputTemplateRepository.findAll(pageable)
            .map(outputTemplateMapper::toDto);
    }


    /**
     * Get one outputTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OutputTemplateDTO> findOne(Long id) {
        log.debug("Request to get OutputTemplate : {}", id);
        return outputTemplateRepository.findById(id)
            .map(outputTemplateMapper::toDto);
    }

    /**
     * Delete the outputTemplate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OutputTemplate : {}", id);
        outputTemplateRepository.deleteById(id);
    }
}

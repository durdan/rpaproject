package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.OcrProcessingErrorService;
import com.edatablock.rpa.domain.OcrProcessingError;
import com.edatablock.rpa.repository.OcrProcessingErrorRepository;
import com.edatablock.rpa.service.dto.OcrProcessingErrorDTO;
import com.edatablock.rpa.service.mapper.OcrProcessingErrorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing OcrProcessingError.
 */
@Service
@Transactional
public class OcrProcessingErrorServiceImpl implements OcrProcessingErrorService {

    private final Logger log = LoggerFactory.getLogger(OcrProcessingErrorServiceImpl.class);

    private final OcrProcessingErrorRepository ocrProcessingErrorRepository;

    private final OcrProcessingErrorMapper ocrProcessingErrorMapper;

    public OcrProcessingErrorServiceImpl(OcrProcessingErrorRepository ocrProcessingErrorRepository, OcrProcessingErrorMapper ocrProcessingErrorMapper) {
        this.ocrProcessingErrorRepository = ocrProcessingErrorRepository;
        this.ocrProcessingErrorMapper = ocrProcessingErrorMapper;
    }

    /**
     * Save a ocrProcessingError.
     *
     * @param ocrProcessingErrorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OcrProcessingErrorDTO save(OcrProcessingErrorDTO ocrProcessingErrorDTO) {
        log.debug("Request to save OcrProcessingError : {}", ocrProcessingErrorDTO);
        OcrProcessingError ocrProcessingError = ocrProcessingErrorMapper.toEntity(ocrProcessingErrorDTO);
        ocrProcessingError = ocrProcessingErrorRepository.save(ocrProcessingError);
        return ocrProcessingErrorMapper.toDto(ocrProcessingError);
    }

    /**
     * Get all the ocrProcessingErrors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OcrProcessingErrorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OcrProcessingErrors");
        return ocrProcessingErrorRepository.findAll(pageable)
            .map(ocrProcessingErrorMapper::toDto);
    }


    /**
     * Get one ocrProcessingError by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OcrProcessingErrorDTO> findOne(Long id) {
        log.debug("Request to get OcrProcessingError : {}", id);
        return ocrProcessingErrorRepository.findById(id)
            .map(ocrProcessingErrorMapper::toDto);
    }

    /**
     * Delete the ocrProcessingError by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OcrProcessingError : {}", id);
        ocrProcessingErrorRepository.deleteById(id);
    }
}

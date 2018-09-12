package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.FileForOCRProcessingService;
import com.edatablock.rpa.domain.FileForOCRProcessing;
import com.edatablock.rpa.repository.FileForOCRProcessingRepository;
import com.edatablock.rpa.service.dto.FileForOCRProcessingDTO;
import com.edatablock.rpa.service.mapper.FileForOCRProcessingMapper;
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
 * Service Implementation for managing FileForOCRProcessing.
 */
@Service
@Transactional
public class FileForOCRProcessingServiceImpl implements FileForOCRProcessingService {

    private final Logger log = LoggerFactory.getLogger(FileForOCRProcessingServiceImpl.class);

    private final FileForOCRProcessingRepository fileForOCRProcessingRepository;

    private final FileForOCRProcessingMapper fileForOCRProcessingMapper;

    public FileForOCRProcessingServiceImpl(FileForOCRProcessingRepository fileForOCRProcessingRepository, FileForOCRProcessingMapper fileForOCRProcessingMapper) {
        this.fileForOCRProcessingRepository = fileForOCRProcessingRepository;
        this.fileForOCRProcessingMapper = fileForOCRProcessingMapper;
    }

    /**
     * Save a fileForOCRProcessing.
     *
     * @param fileForOCRProcessingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileForOCRProcessingDTO save(FileForOCRProcessingDTO fileForOCRProcessingDTO) {
        log.debug("Request to save FileForOCRProcessing : {}", fileForOCRProcessingDTO);
        FileForOCRProcessing fileForOCRProcessing = fileForOCRProcessingMapper.toEntity(fileForOCRProcessingDTO);
        fileForOCRProcessing = fileForOCRProcessingRepository.save(fileForOCRProcessing);
        return fileForOCRProcessingMapper.toDto(fileForOCRProcessing);
    }

    /**
     * Get all the fileForOCRProcessings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileForOCRProcessingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileForOCRProcessings");
        return fileForOCRProcessingRepository.findAll(pageable)
            .map(fileForOCRProcessingMapper::toDto);
    }



    /**
     *  get all the fileForOCRProcessings where Transaction is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<FileForOCRProcessingDTO> findAllWhereTransactionIsNull() {
        log.debug("Request to get all fileForOCRProcessings where Transaction is null");
        return StreamSupport
            .stream(fileForOCRProcessingRepository.findAll().spliterator(), false)
            .filter(fileForOCRProcessing -> fileForOCRProcessing.getTransaction() == null)
            .map(fileForOCRProcessingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one fileForOCRProcessing by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileForOCRProcessingDTO> findOne(Long id) {
        log.debug("Request to get FileForOCRProcessing : {}", id);
        return fileForOCRProcessingRepository.findById(id)
            .map(fileForOCRProcessingMapper::toDto);
    }

    /**
     * Delete the fileForOCRProcessing by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileForOCRProcessing : {}", id);
        fileForOCRProcessingRepository.deleteById(id);
    }
}

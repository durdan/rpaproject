package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.UploadFilesService;
import com.edatablock.rpa.domain.UploadFiles;
import com.edatablock.rpa.repository.UploadFilesRepository;
import com.edatablock.rpa.service.dto.UploadFilesDTO;
import com.edatablock.rpa.service.mapper.UploadFilesMapper;
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
 * Service Implementation for managing UploadFiles.
 */
@Service
@Transactional
public class UploadFilesServiceImpl implements UploadFilesService {

    private final Logger log = LoggerFactory.getLogger(UploadFilesServiceImpl.class);

    private final UploadFilesRepository uploadFilesRepository;

    private final UploadFilesMapper uploadFilesMapper;

    public UploadFilesServiceImpl(UploadFilesRepository uploadFilesRepository, UploadFilesMapper uploadFilesMapper) {
        this.uploadFilesRepository = uploadFilesRepository;
        this.uploadFilesMapper = uploadFilesMapper;
    }

    /**
     * Save a uploadFiles.
     *
     * @param uploadFilesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UploadFilesDTO save(UploadFilesDTO uploadFilesDTO) {
        log.debug("Request to save UploadFiles : {}", uploadFilesDTO);
        UploadFiles uploadFiles = uploadFilesMapper.toEntity(uploadFilesDTO);
        uploadFiles = uploadFilesRepository.save(uploadFiles);
        return uploadFilesMapper.toDto(uploadFiles);
    }

    /**
     * Get all the uploadFiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UploadFilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UploadFiles");
        return uploadFilesRepository.findAll(pageable)
            .map(uploadFilesMapper::toDto);
    }



    /**
     *  get all the uploadFiles where FileForOCRProcessing is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UploadFilesDTO> findAllWhereFileForOCRProcessingIsNull() {
        log.debug("Request to get all uploadFiles where FileForOCRProcessing is null");
        return StreamSupport
            .stream(uploadFilesRepository.findAll().spliterator(), false)
            .filter(uploadFiles -> uploadFiles.getFileForOCRProcessing() == null)
            .map(uploadFilesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one uploadFiles by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UploadFilesDTO> findOne(Long id) {
        log.debug("Request to get UploadFiles : {}", id);
        return uploadFilesRepository.findById(id)
            .map(uploadFilesMapper::toDto);
    }

    /**
     * Delete the uploadFiles by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UploadFiles : {}", id);
        uploadFilesRepository.deleteById(id);
    }
}

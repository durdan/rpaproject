package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.FileToFtpService;
import com.edatablock.rpa.domain.FileToFtp;
import com.edatablock.rpa.repository.FileToFtpRepository;
import com.edatablock.rpa.service.dto.FileToFtpDTO;
import com.edatablock.rpa.service.mapper.FileToFtpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing FileToFtp.
 */
@Service
@Transactional
public class FileToFtpServiceImpl implements FileToFtpService {

    private final Logger log = LoggerFactory.getLogger(FileToFtpServiceImpl.class);

    private final FileToFtpRepository fileToFtpRepository;

    private final FileToFtpMapper fileToFtpMapper;

    public FileToFtpServiceImpl(FileToFtpRepository fileToFtpRepository, FileToFtpMapper fileToFtpMapper) {
        this.fileToFtpRepository = fileToFtpRepository;
        this.fileToFtpMapper = fileToFtpMapper;
    }

    /**
     * Save a fileToFtp.
     *
     * @param fileToFtpDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileToFtpDTO save(FileToFtpDTO fileToFtpDTO) {
        log.debug("Request to save FileToFtp : {}", fileToFtpDTO);
        FileToFtp fileToFtp = fileToFtpMapper.toEntity(fileToFtpDTO);
        fileToFtp = fileToFtpRepository.save(fileToFtp);
        return fileToFtpMapper.toDto(fileToFtp);
    }

    /**
     * Get all the fileToFtps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileToFtpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileToFtps");
        return fileToFtpRepository.findAll(pageable)
            .map(fileToFtpMapper::toDto);
    }


    /**
     * Get one fileToFtp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileToFtpDTO> findOne(Long id) {
        log.debug("Request to get FileToFtp : {}", id);
        return fileToFtpRepository.findById(id)
            .map(fileToFtpMapper::toDto);
    }

    /**
     * Delete the fileToFtp by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileToFtp : {}", id);
        fileToFtpRepository.deleteById(id);
    }
}

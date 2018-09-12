package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.ClientEmailDomainService;
import com.edatablock.rpa.domain.ClientEmailDomain;
import com.edatablock.rpa.repository.ClientEmailDomainRepository;
import com.edatablock.rpa.service.dto.ClientEmailDomainDTO;
import com.edatablock.rpa.service.mapper.ClientEmailDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ClientEmailDomain.
 */
@Service
@Transactional
public class ClientEmailDomainServiceImpl implements ClientEmailDomainService {

    private final Logger log = LoggerFactory.getLogger(ClientEmailDomainServiceImpl.class);

    private final ClientEmailDomainRepository clientEmailDomainRepository;

    private final ClientEmailDomainMapper clientEmailDomainMapper;

    public ClientEmailDomainServiceImpl(ClientEmailDomainRepository clientEmailDomainRepository, ClientEmailDomainMapper clientEmailDomainMapper) {
        this.clientEmailDomainRepository = clientEmailDomainRepository;
        this.clientEmailDomainMapper = clientEmailDomainMapper;
    }

    /**
     * Save a clientEmailDomain.
     *
     * @param clientEmailDomainDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientEmailDomainDTO save(ClientEmailDomainDTO clientEmailDomainDTO) {
        log.debug("Request to save ClientEmailDomain : {}", clientEmailDomainDTO);
        ClientEmailDomain clientEmailDomain = clientEmailDomainMapper.toEntity(clientEmailDomainDTO);
        clientEmailDomain = clientEmailDomainRepository.save(clientEmailDomain);
        return clientEmailDomainMapper.toDto(clientEmailDomain);
    }

    /**
     * Get all the clientEmailDomains.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientEmailDomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientEmailDomains");
        return clientEmailDomainRepository.findAll(pageable)
            .map(clientEmailDomainMapper::toDto);
    }


    /**
     * Get one clientEmailDomain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientEmailDomainDTO> findOne(Long id) {
        log.debug("Request to get ClientEmailDomain : {}", id);
        return clientEmailDomainRepository.findById(id)
            .map(clientEmailDomainMapper::toDto);
    }

    /**
     * Delete the clientEmailDomain by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientEmailDomain : {}", id);
        clientEmailDomainRepository.deleteById(id);
    }
}

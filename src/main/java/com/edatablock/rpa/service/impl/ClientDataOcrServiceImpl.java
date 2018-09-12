package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.ClientDataOcrService;
import com.edatablock.rpa.domain.ClientDataOcr;
import com.edatablock.rpa.repository.ClientDataOcrRepository;
import com.edatablock.rpa.service.dto.ClientDataOcrDTO;
import com.edatablock.rpa.service.mapper.ClientDataOcrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ClientDataOcr.
 */
@Service
@Transactional
public class ClientDataOcrServiceImpl implements ClientDataOcrService {

    private final Logger log = LoggerFactory.getLogger(ClientDataOcrServiceImpl.class);

    private final ClientDataOcrRepository clientDataOcrRepository;

    private final ClientDataOcrMapper clientDataOcrMapper;

    public ClientDataOcrServiceImpl(ClientDataOcrRepository clientDataOcrRepository, ClientDataOcrMapper clientDataOcrMapper) {
        this.clientDataOcrRepository = clientDataOcrRepository;
        this.clientDataOcrMapper = clientDataOcrMapper;
    }

    /**
     * Save a clientDataOcr.
     *
     * @param clientDataOcrDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientDataOcrDTO save(ClientDataOcrDTO clientDataOcrDTO) {
        log.debug("Request to save ClientDataOcr : {}", clientDataOcrDTO);
        ClientDataOcr clientDataOcr = clientDataOcrMapper.toEntity(clientDataOcrDTO);
        clientDataOcr = clientDataOcrRepository.save(clientDataOcr);
        return clientDataOcrMapper.toDto(clientDataOcr);
    }

    /**
     * Get all the clientDataOcrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientDataOcrDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientDataOcrs");
        return clientDataOcrRepository.findAll(pageable)
            .map(clientDataOcrMapper::toDto);
    }


    /**
     * Get one clientDataOcr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDataOcrDTO> findOne(Long id) {
        log.debug("Request to get ClientDataOcr : {}", id);
        return clientDataOcrRepository.findById(id)
            .map(clientDataOcrMapper::toDto);
    }

    /**
     * Delete the clientDataOcr by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientDataOcr : {}", id);
        clientDataOcrRepository.deleteById(id);
    }
}

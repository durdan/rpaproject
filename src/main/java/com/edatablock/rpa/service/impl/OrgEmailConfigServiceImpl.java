package com.edatablock.rpa.service.impl;

import com.edatablock.rpa.service.OrgEmailConfigService;
import com.edatablock.rpa.domain.OrgEmailConfig;
import com.edatablock.rpa.repository.OrgEmailConfigRepository;
import com.edatablock.rpa.service.dto.OrgEmailConfigDTO;
import com.edatablock.rpa.service.mapper.OrgEmailConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing OrgEmailConfig.
 */
@Service
@Transactional
public class OrgEmailConfigServiceImpl implements OrgEmailConfigService {

    private final Logger log = LoggerFactory.getLogger(OrgEmailConfigServiceImpl.class);

    private final OrgEmailConfigRepository orgEmailConfigRepository;

    private final OrgEmailConfigMapper orgEmailConfigMapper;

    public OrgEmailConfigServiceImpl(OrgEmailConfigRepository orgEmailConfigRepository, OrgEmailConfigMapper orgEmailConfigMapper) {
        this.orgEmailConfigRepository = orgEmailConfigRepository;
        this.orgEmailConfigMapper = orgEmailConfigMapper;
    }

    /**
     * Save a orgEmailConfig.
     *
     * @param orgEmailConfigDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrgEmailConfigDTO save(OrgEmailConfigDTO orgEmailConfigDTO) {
        log.debug("Request to save OrgEmailConfig : {}", orgEmailConfigDTO);
        OrgEmailConfig orgEmailConfig = orgEmailConfigMapper.toEntity(orgEmailConfigDTO);
        orgEmailConfig = orgEmailConfigRepository.save(orgEmailConfig);
        return orgEmailConfigMapper.toDto(orgEmailConfig);
    }

    /**
     * Get all the orgEmailConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrgEmailConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrgEmailConfigs");
        return orgEmailConfigRepository.findAll(pageable)
            .map(orgEmailConfigMapper::toDto);
    }


    /**
     * Get one orgEmailConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrgEmailConfigDTO> findOne(Long id) {
        log.debug("Request to get OrgEmailConfig : {}", id);
        return orgEmailConfigRepository.findById(id)
            .map(orgEmailConfigMapper::toDto);
    }

    /**
     * Delete the orgEmailConfig by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrgEmailConfig : {}", id);
        orgEmailConfigRepository.deleteById(id);
    }
}

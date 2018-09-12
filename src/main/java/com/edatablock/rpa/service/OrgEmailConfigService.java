package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.OrgEmailConfigDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OrgEmailConfig.
 */
public interface OrgEmailConfigService {

    /**
     * Save a orgEmailConfig.
     *
     * @param orgEmailConfigDTO the entity to save
     * @return the persisted entity
     */
    OrgEmailConfigDTO save(OrgEmailConfigDTO orgEmailConfigDTO);

    /**
     * Get all the orgEmailConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrgEmailConfigDTO> findAll(Pageable pageable);


    /**
     * Get the "id" orgEmailConfig.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrgEmailConfigDTO> findOne(Long id);

    /**
     * Delete the "id" orgEmailConfig.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

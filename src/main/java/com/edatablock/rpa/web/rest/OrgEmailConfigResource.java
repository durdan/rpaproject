package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.OrgEmailConfigService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.OrgEmailConfigDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrgEmailConfig.
 */
@RestController
@RequestMapping("/api")
public class OrgEmailConfigResource {

    private final Logger log = LoggerFactory.getLogger(OrgEmailConfigResource.class);

    private static final String ENTITY_NAME = "orgEmailConfig";

    private final OrgEmailConfigService orgEmailConfigService;

    public OrgEmailConfigResource(OrgEmailConfigService orgEmailConfigService) {
        this.orgEmailConfigService = orgEmailConfigService;
    }

    /**
     * POST  /org-email-configs : Create a new orgEmailConfig.
     *
     * @param orgEmailConfigDTO the orgEmailConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orgEmailConfigDTO, or with status 400 (Bad Request) if the orgEmailConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/org-email-configs")
    @Timed
    public ResponseEntity<OrgEmailConfigDTO> createOrgEmailConfig(@RequestBody OrgEmailConfigDTO orgEmailConfigDTO) throws URISyntaxException {
        log.debug("REST request to save OrgEmailConfig : {}", orgEmailConfigDTO);
        if (orgEmailConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new orgEmailConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgEmailConfigDTO result = orgEmailConfigService.save(orgEmailConfigDTO);
        return ResponseEntity.created(new URI("/api/org-email-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /org-email-configs : Updates an existing orgEmailConfig.
     *
     * @param orgEmailConfigDTO the orgEmailConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orgEmailConfigDTO,
     * or with status 400 (Bad Request) if the orgEmailConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the orgEmailConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/org-email-configs")
    @Timed
    public ResponseEntity<OrgEmailConfigDTO> updateOrgEmailConfig(@RequestBody OrgEmailConfigDTO orgEmailConfigDTO) throws URISyntaxException {
        log.debug("REST request to update OrgEmailConfig : {}", orgEmailConfigDTO);
        if (orgEmailConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrgEmailConfigDTO result = orgEmailConfigService.save(orgEmailConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orgEmailConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /org-email-configs : get all the orgEmailConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orgEmailConfigs in body
     */
    @GetMapping("/org-email-configs")
    @Timed
    public ResponseEntity<List<OrgEmailConfigDTO>> getAllOrgEmailConfigs(Pageable pageable) {
        log.debug("REST request to get a page of OrgEmailConfigs");
        Page<OrgEmailConfigDTO> page = orgEmailConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/org-email-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /org-email-configs/:id : get the "id" orgEmailConfig.
     *
     * @param id the id of the orgEmailConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orgEmailConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/org-email-configs/{id}")
    @Timed
    public ResponseEntity<OrgEmailConfigDTO> getOrgEmailConfig(@PathVariable Long id) {
        log.debug("REST request to get OrgEmailConfig : {}", id);
        Optional<OrgEmailConfigDTO> orgEmailConfigDTO = orgEmailConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgEmailConfigDTO);
    }

    /**
     * DELETE  /org-email-configs/:id : delete the "id" orgEmailConfig.
     *
     * @param id the id of the orgEmailConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/org-email-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrgEmailConfig(@PathVariable Long id) {
        log.debug("REST request to delete OrgEmailConfig : {}", id);
        orgEmailConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

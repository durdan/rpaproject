package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.TemplateFieldsService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.TemplateFieldsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TemplateFields.
 */
@RestController
@RequestMapping("/api")
public class TemplateFieldsResource {

    private final Logger log = LoggerFactory.getLogger(TemplateFieldsResource.class);

    private static final String ENTITY_NAME = "templateFields";

    private final TemplateFieldsService templateFieldsService;

    public TemplateFieldsResource(TemplateFieldsService templateFieldsService) {
        this.templateFieldsService = templateFieldsService;
    }

    /**
     * POST  /template-fields : Create a new templateFields.
     *
     * @param templateFieldsDTO the templateFieldsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new templateFieldsDTO, or with status 400 (Bad Request) if the templateFields has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/template-fields")
    @Timed
    public ResponseEntity<TemplateFieldsDTO> createTemplateFields(@Valid @RequestBody TemplateFieldsDTO templateFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save TemplateFields : {}", templateFieldsDTO);
        if (templateFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateFieldsDTO result = templateFieldsService.save(templateFieldsDTO);
        return ResponseEntity.created(new URI("/api/template-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /template-fields : Updates an existing templateFields.
     *
     * @param templateFieldsDTO the templateFieldsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated templateFieldsDTO,
     * or with status 400 (Bad Request) if the templateFieldsDTO is not valid,
     * or with status 500 (Internal Server Error) if the templateFieldsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/template-fields")
    @Timed
    public ResponseEntity<TemplateFieldsDTO> updateTemplateFields(@Valid @RequestBody TemplateFieldsDTO templateFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update TemplateFields : {}", templateFieldsDTO);
        if (templateFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TemplateFieldsDTO result = templateFieldsService.save(templateFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, templateFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /template-fields : get all the templateFields.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of templateFields in body
     */
    @GetMapping("/template-fields")
    @Timed
    public ResponseEntity<List<TemplateFieldsDTO>> getAllTemplateFields(Pageable pageable) {
        log.debug("REST request to get a page of TemplateFields");
        Page<TemplateFieldsDTO> page = templateFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/template-fields");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /template-fields/:id : get the "id" templateFields.
     *
     * @param id the id of the templateFieldsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the templateFieldsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/template-fields/{id}")
    @Timed
    public ResponseEntity<TemplateFieldsDTO> getTemplateFields(@PathVariable Long id) {
        log.debug("REST request to get TemplateFields : {}", id);
        Optional<TemplateFieldsDTO> templateFieldsDTO = templateFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateFieldsDTO);
    }

    /**
     * DELETE  /template-fields/:id : delete the "id" templateFields.
     *
     * @param id the id of the templateFieldsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/template-fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplateFields(@PathVariable Long id) {
        log.debug("REST request to delete TemplateFields : {}", id);
        templateFieldsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.InputTemplateService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.InputTemplateDTO;
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
 * REST controller for managing InputTemplate.
 */
@RestController
@RequestMapping("/api")
public class InputTemplateResource {

    private final Logger log = LoggerFactory.getLogger(InputTemplateResource.class);

    private static final String ENTITY_NAME = "inputTemplate";

    private final InputTemplateService inputTemplateService;

    public InputTemplateResource(InputTemplateService inputTemplateService) {
        this.inputTemplateService = inputTemplateService;
    }

    /**
     * POST  /input-templates : Create a new inputTemplate.
     *
     * @param inputTemplateDTO the inputTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inputTemplateDTO, or with status 400 (Bad Request) if the inputTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/input-templates")
    @Timed
    public ResponseEntity<InputTemplateDTO> createInputTemplate(@Valid @RequestBody InputTemplateDTO inputTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save InputTemplate : {}", inputTemplateDTO);
        if (inputTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new inputTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InputTemplateDTO result = inputTemplateService.save(inputTemplateDTO);
        return ResponseEntity.created(new URI("/api/input-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /input-templates : Updates an existing inputTemplate.
     *
     * @param inputTemplateDTO the inputTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inputTemplateDTO,
     * or with status 400 (Bad Request) if the inputTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the inputTemplateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/input-templates")
    @Timed
    public ResponseEntity<InputTemplateDTO> updateInputTemplate(@Valid @RequestBody InputTemplateDTO inputTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update InputTemplate : {}", inputTemplateDTO);
        if (inputTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InputTemplateDTO result = inputTemplateService.save(inputTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inputTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /input-templates : get all the inputTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inputTemplates in body
     */
    @GetMapping("/input-templates")
    @Timed
    public ResponseEntity<List<InputTemplateDTO>> getAllInputTemplates(Pageable pageable) {
        log.debug("REST request to get a page of InputTemplates");
        Page<InputTemplateDTO> page = inputTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/input-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /input-templates/:id : get the "id" inputTemplate.
     *
     * @param id the id of the inputTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inputTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/input-templates/{id}")
    @Timed
    public ResponseEntity<InputTemplateDTO> getInputTemplate(@PathVariable Long id) {
        log.debug("REST request to get InputTemplate : {}", id);
        Optional<InputTemplateDTO> inputTemplateDTO = inputTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inputTemplateDTO);
    }

    /**
     * DELETE  /input-templates/:id : delete the "id" inputTemplate.
     *
     * @param id the id of the inputTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/input-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteInputTemplate(@PathVariable Long id) {
        log.debug("REST request to delete InputTemplate : {}", id);
        inputTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

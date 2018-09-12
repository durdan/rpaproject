package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.OutputTemplateService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.OutputTemplateDTO;
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
 * REST controller for managing OutputTemplate.
 */
@RestController
@RequestMapping("/api")
public class OutputTemplateResource {

    private final Logger log = LoggerFactory.getLogger(OutputTemplateResource.class);

    private static final String ENTITY_NAME = "outputTemplate";

    private final OutputTemplateService outputTemplateService;

    public OutputTemplateResource(OutputTemplateService outputTemplateService) {
        this.outputTemplateService = outputTemplateService;
    }

    /**
     * POST  /output-templates : Create a new outputTemplate.
     *
     * @param outputTemplateDTO the outputTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new outputTemplateDTO, or with status 400 (Bad Request) if the outputTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/output-templates")
    @Timed
    public ResponseEntity<OutputTemplateDTO> createOutputTemplate(@Valid @RequestBody OutputTemplateDTO outputTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save OutputTemplate : {}", outputTemplateDTO);
        if (outputTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new outputTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OutputTemplateDTO result = outputTemplateService.save(outputTemplateDTO);
        return ResponseEntity.created(new URI("/api/output-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /output-templates : Updates an existing outputTemplate.
     *
     * @param outputTemplateDTO the outputTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated outputTemplateDTO,
     * or with status 400 (Bad Request) if the outputTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the outputTemplateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/output-templates")
    @Timed
    public ResponseEntity<OutputTemplateDTO> updateOutputTemplate(@Valid @RequestBody OutputTemplateDTO outputTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update OutputTemplate : {}", outputTemplateDTO);
        if (outputTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OutputTemplateDTO result = outputTemplateService.save(outputTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, outputTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /output-templates : get all the outputTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of outputTemplates in body
     */
    @GetMapping("/output-templates")
    @Timed
    public ResponseEntity<List<OutputTemplateDTO>> getAllOutputTemplates(Pageable pageable) {
        log.debug("REST request to get a page of OutputTemplates");
        Page<OutputTemplateDTO> page = outputTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/output-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /output-templates/:id : get the "id" outputTemplate.
     *
     * @param id the id of the outputTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the outputTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/output-templates/{id}")
    @Timed
    public ResponseEntity<OutputTemplateDTO> getOutputTemplate(@PathVariable Long id) {
        log.debug("REST request to get OutputTemplate : {}", id);
        Optional<OutputTemplateDTO> outputTemplateDTO = outputTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(outputTemplateDTO);
    }

    /**
     * DELETE  /output-templates/:id : delete the "id" outputTemplate.
     *
     * @param id the id of the outputTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/output-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteOutputTemplate(@PathVariable Long id) {
        log.debug("REST request to delete OutputTemplate : {}", id);
        outputTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

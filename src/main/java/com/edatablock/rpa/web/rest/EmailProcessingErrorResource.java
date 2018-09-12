package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.EmailProcessingErrorService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.EmailProcessingErrorDTO;
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
 * REST controller for managing EmailProcessingError.
 */
@RestController
@RequestMapping("/api")
public class EmailProcessingErrorResource {

    private final Logger log = LoggerFactory.getLogger(EmailProcessingErrorResource.class);

    private static final String ENTITY_NAME = "emailProcessingError";

    private final EmailProcessingErrorService emailProcessingErrorService;

    public EmailProcessingErrorResource(EmailProcessingErrorService emailProcessingErrorService) {
        this.emailProcessingErrorService = emailProcessingErrorService;
    }

    /**
     * POST  /email-processing-errors : Create a new emailProcessingError.
     *
     * @param emailProcessingErrorDTO the emailProcessingErrorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailProcessingErrorDTO, or with status 400 (Bad Request) if the emailProcessingError has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-processing-errors")
    @Timed
    public ResponseEntity<EmailProcessingErrorDTO> createEmailProcessingError(@Valid @RequestBody EmailProcessingErrorDTO emailProcessingErrorDTO) throws URISyntaxException {
        log.debug("REST request to save EmailProcessingError : {}", emailProcessingErrorDTO);
        if (emailProcessingErrorDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailProcessingError cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailProcessingErrorDTO result = emailProcessingErrorService.save(emailProcessingErrorDTO);
        return ResponseEntity.created(new URI("/api/email-processing-errors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-processing-errors : Updates an existing emailProcessingError.
     *
     * @param emailProcessingErrorDTO the emailProcessingErrorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailProcessingErrorDTO,
     * or with status 400 (Bad Request) if the emailProcessingErrorDTO is not valid,
     * or with status 500 (Internal Server Error) if the emailProcessingErrorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-processing-errors")
    @Timed
    public ResponseEntity<EmailProcessingErrorDTO> updateEmailProcessingError(@Valid @RequestBody EmailProcessingErrorDTO emailProcessingErrorDTO) throws URISyntaxException {
        log.debug("REST request to update EmailProcessingError : {}", emailProcessingErrorDTO);
        if (emailProcessingErrorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailProcessingErrorDTO result = emailProcessingErrorService.save(emailProcessingErrorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailProcessingErrorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-processing-errors : get all the emailProcessingErrors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emailProcessingErrors in body
     */
    @GetMapping("/email-processing-errors")
    @Timed
    public ResponseEntity<List<EmailProcessingErrorDTO>> getAllEmailProcessingErrors(Pageable pageable) {
        log.debug("REST request to get a page of EmailProcessingErrors");
        Page<EmailProcessingErrorDTO> page = emailProcessingErrorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/email-processing-errors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /email-processing-errors/:id : get the "id" emailProcessingError.
     *
     * @param id the id of the emailProcessingErrorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailProcessingErrorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/email-processing-errors/{id}")
    @Timed
    public ResponseEntity<EmailProcessingErrorDTO> getEmailProcessingError(@PathVariable Long id) {
        log.debug("REST request to get EmailProcessingError : {}", id);
        Optional<EmailProcessingErrorDTO> emailProcessingErrorDTO = emailProcessingErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailProcessingErrorDTO);
    }

    /**
     * DELETE  /email-processing-errors/:id : delete the "id" emailProcessingError.
     *
     * @param id the id of the emailProcessingErrorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-processing-errors/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailProcessingError(@PathVariable Long id) {
        log.debug("REST request to delete EmailProcessingError : {}", id);
        emailProcessingErrorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

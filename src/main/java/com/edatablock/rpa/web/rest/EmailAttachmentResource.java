package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.EmailAttachmentService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.EmailAttachmentDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing EmailAttachment.
 */
@RestController
@RequestMapping("/api")
public class EmailAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(EmailAttachmentResource.class);

    private static final String ENTITY_NAME = "emailAttachment";

    private final EmailAttachmentService emailAttachmentService;

    public EmailAttachmentResource(EmailAttachmentService emailAttachmentService) {
        this.emailAttachmentService = emailAttachmentService;
    }

    /**
     * POST  /email-attachments : Create a new emailAttachment.
     *
     * @param emailAttachmentDTO the emailAttachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailAttachmentDTO, or with status 400 (Bad Request) if the emailAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-attachments")
    @Timed
    public ResponseEntity<EmailAttachmentDTO> createEmailAttachment(@Valid @RequestBody EmailAttachmentDTO emailAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save EmailAttachment : {}", emailAttachmentDTO);
        if (emailAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailAttachmentDTO result = emailAttachmentService.save(emailAttachmentDTO);
        return ResponseEntity.created(new URI("/api/email-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-attachments : Updates an existing emailAttachment.
     *
     * @param emailAttachmentDTO the emailAttachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailAttachmentDTO,
     * or with status 400 (Bad Request) if the emailAttachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the emailAttachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-attachments")
    @Timed
    public ResponseEntity<EmailAttachmentDTO> updateEmailAttachment(@Valid @RequestBody EmailAttachmentDTO emailAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update EmailAttachment : {}", emailAttachmentDTO);
        if (emailAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailAttachmentDTO result = emailAttachmentService.save(emailAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-attachments : get all the emailAttachments.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of emailAttachments in body
     */
    @GetMapping("/email-attachments")
    @Timed
    public ResponseEntity<List<EmailAttachmentDTO>> getAllEmailAttachments(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("fileforocrprocessing-is-null".equals(filter)) {
            log.debug("REST request to get all EmailAttachments where fileForOCRProcessing is null");
            return new ResponseEntity<>(emailAttachmentService.findAllWhereFileForOCRProcessingIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of EmailAttachments");
        Page<EmailAttachmentDTO> page = emailAttachmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/email-attachments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /email-attachments/:id : get the "id" emailAttachment.
     *
     * @param id the id of the emailAttachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailAttachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/email-attachments/{id}")
    @Timed
    public ResponseEntity<EmailAttachmentDTO> getEmailAttachment(@PathVariable Long id) {
        log.debug("REST request to get EmailAttachment : {}", id);
        Optional<EmailAttachmentDTO> emailAttachmentDTO = emailAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailAttachmentDTO);
    }

    /**
     * DELETE  /email-attachments/:id : delete the "id" emailAttachment.
     *
     * @param id the id of the emailAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-attachments/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailAttachment(@PathVariable Long id) {
        log.debug("REST request to delete EmailAttachment : {}", id);
        emailAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

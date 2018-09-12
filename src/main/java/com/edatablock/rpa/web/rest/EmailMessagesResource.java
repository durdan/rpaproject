package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.EmailMessagesService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.EmailMessagesDTO;
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
 * REST controller for managing EmailMessages.
 */
@RestController
@RequestMapping("/api")
public class EmailMessagesResource {

    private final Logger log = LoggerFactory.getLogger(EmailMessagesResource.class);

    private static final String ENTITY_NAME = "emailMessages";

    private final EmailMessagesService emailMessagesService;

    public EmailMessagesResource(EmailMessagesService emailMessagesService) {
        this.emailMessagesService = emailMessagesService;
    }

    /**
     * POST  /email-messages : Create a new emailMessages.
     *
     * @param emailMessagesDTO the emailMessagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailMessagesDTO, or with status 400 (Bad Request) if the emailMessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-messages")
    @Timed
    public ResponseEntity<EmailMessagesDTO> createEmailMessages(@Valid @RequestBody EmailMessagesDTO emailMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save EmailMessages : {}", emailMessagesDTO);
        if (emailMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailMessagesDTO result = emailMessagesService.save(emailMessagesDTO);
        return ResponseEntity.created(new URI("/api/email-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-messages : Updates an existing emailMessages.
     *
     * @param emailMessagesDTO the emailMessagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailMessagesDTO,
     * or with status 400 (Bad Request) if the emailMessagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the emailMessagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-messages")
    @Timed
    public ResponseEntity<EmailMessagesDTO> updateEmailMessages(@Valid @RequestBody EmailMessagesDTO emailMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update EmailMessages : {}", emailMessagesDTO);
        if (emailMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailMessagesDTO result = emailMessagesService.save(emailMessagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailMessagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-messages : get all the emailMessages.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of emailMessages in body
     */
    @GetMapping("/email-messages")
    @Timed
    public ResponseEntity<List<EmailMessagesDTO>> getAllEmailMessages(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("emailprocessingerror-is-null".equals(filter)) {
            log.debug("REST request to get all EmailMessagess where emailProcessingError is null");
            return new ResponseEntity<>(emailMessagesService.findAllWhereEmailProcessingErrorIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of EmailMessages");
        Page<EmailMessagesDTO> page = emailMessagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/email-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /email-messages/:id : get the "id" emailMessages.
     *
     * @param id the id of the emailMessagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailMessagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/email-messages/{id}")
    @Timed
    public ResponseEntity<EmailMessagesDTO> getEmailMessages(@PathVariable Long id) {
        log.debug("REST request to get EmailMessages : {}", id);
        Optional<EmailMessagesDTO> emailMessagesDTO = emailMessagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailMessagesDTO);
    }

    /**
     * DELETE  /email-messages/:id : delete the "id" emailMessages.
     *
     * @param id the id of the emailMessagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailMessages(@PathVariable Long id) {
        log.debug("REST request to delete EmailMessages : {}", id);
        emailMessagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

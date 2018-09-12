package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.ClientEmailDomainService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.ClientEmailDomainDTO;
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
 * REST controller for managing ClientEmailDomain.
 */
@RestController
@RequestMapping("/api")
public class ClientEmailDomainResource {

    private final Logger log = LoggerFactory.getLogger(ClientEmailDomainResource.class);

    private static final String ENTITY_NAME = "clientEmailDomain";

    private final ClientEmailDomainService clientEmailDomainService;

    public ClientEmailDomainResource(ClientEmailDomainService clientEmailDomainService) {
        this.clientEmailDomainService = clientEmailDomainService;
    }

    /**
     * POST  /client-email-domains : Create a new clientEmailDomain.
     *
     * @param clientEmailDomainDTO the clientEmailDomainDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientEmailDomainDTO, or with status 400 (Bad Request) if the clientEmailDomain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-email-domains")
    @Timed
    public ResponseEntity<ClientEmailDomainDTO> createClientEmailDomain(@Valid @RequestBody ClientEmailDomainDTO clientEmailDomainDTO) throws URISyntaxException {
        log.debug("REST request to save ClientEmailDomain : {}", clientEmailDomainDTO);
        if (clientEmailDomainDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientEmailDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientEmailDomainDTO result = clientEmailDomainService.save(clientEmailDomainDTO);
        return ResponseEntity.created(new URI("/api/client-email-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-email-domains : Updates an existing clientEmailDomain.
     *
     * @param clientEmailDomainDTO the clientEmailDomainDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientEmailDomainDTO,
     * or with status 400 (Bad Request) if the clientEmailDomainDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientEmailDomainDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-email-domains")
    @Timed
    public ResponseEntity<ClientEmailDomainDTO> updateClientEmailDomain(@Valid @RequestBody ClientEmailDomainDTO clientEmailDomainDTO) throws URISyntaxException {
        log.debug("REST request to update ClientEmailDomain : {}", clientEmailDomainDTO);
        if (clientEmailDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientEmailDomainDTO result = clientEmailDomainService.save(clientEmailDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientEmailDomainDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-email-domains : get all the clientEmailDomains.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientEmailDomains in body
     */
    @GetMapping("/client-email-domains")
    @Timed
    public ResponseEntity<List<ClientEmailDomainDTO>> getAllClientEmailDomains(Pageable pageable) {
        log.debug("REST request to get a page of ClientEmailDomains");
        Page<ClientEmailDomainDTO> page = clientEmailDomainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-email-domains");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-email-domains/:id : get the "id" clientEmailDomain.
     *
     * @param id the id of the clientEmailDomainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientEmailDomainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/client-email-domains/{id}")
    @Timed
    public ResponseEntity<ClientEmailDomainDTO> getClientEmailDomain(@PathVariable Long id) {
        log.debug("REST request to get ClientEmailDomain : {}", id);
        Optional<ClientEmailDomainDTO> clientEmailDomainDTO = clientEmailDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientEmailDomainDTO);
    }

    /**
     * DELETE  /client-email-domains/:id : delete the "id" clientEmailDomain.
     *
     * @param id the id of the clientEmailDomainDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-email-domains/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientEmailDomain(@PathVariable Long id) {
        log.debug("REST request to delete ClientEmailDomain : {}", id);
        clientEmailDomainService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.ClientDataOcrService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.ClientDataOcrDTO;
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
 * REST controller for managing ClientDataOcr.
 */
@RestController
@RequestMapping("/api")
public class ClientDataOcrResource {

    private final Logger log = LoggerFactory.getLogger(ClientDataOcrResource.class);

    private static final String ENTITY_NAME = "clientDataOcr";

    private final ClientDataOcrService clientDataOcrService;

    public ClientDataOcrResource(ClientDataOcrService clientDataOcrService) {
        this.clientDataOcrService = clientDataOcrService;
    }

    /**
     * POST  /client-data-ocrs : Create a new clientDataOcr.
     *
     * @param clientDataOcrDTO the clientDataOcrDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientDataOcrDTO, or with status 400 (Bad Request) if the clientDataOcr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-data-ocrs")
    @Timed
    public ResponseEntity<ClientDataOcrDTO> createClientDataOcr(@RequestBody ClientDataOcrDTO clientDataOcrDTO) throws URISyntaxException {
        log.debug("REST request to save ClientDataOcr : {}", clientDataOcrDTO);
        if (clientDataOcrDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientDataOcr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientDataOcrDTO result = clientDataOcrService.save(clientDataOcrDTO);
        return ResponseEntity.created(new URI("/api/client-data-ocrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-data-ocrs : Updates an existing clientDataOcr.
     *
     * @param clientDataOcrDTO the clientDataOcrDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientDataOcrDTO,
     * or with status 400 (Bad Request) if the clientDataOcrDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientDataOcrDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-data-ocrs")
    @Timed
    public ResponseEntity<ClientDataOcrDTO> updateClientDataOcr(@RequestBody ClientDataOcrDTO clientDataOcrDTO) throws URISyntaxException {
        log.debug("REST request to update ClientDataOcr : {}", clientDataOcrDTO);
        if (clientDataOcrDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientDataOcrDTO result = clientDataOcrService.save(clientDataOcrDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientDataOcrDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-data-ocrs : get all the clientDataOcrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientDataOcrs in body
     */
    @GetMapping("/client-data-ocrs")
    @Timed
    public ResponseEntity<List<ClientDataOcrDTO>> getAllClientDataOcrs(Pageable pageable) {
        log.debug("REST request to get a page of ClientDataOcrs");
        Page<ClientDataOcrDTO> page = clientDataOcrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-data-ocrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-data-ocrs/:id : get the "id" clientDataOcr.
     *
     * @param id the id of the clientDataOcrDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientDataOcrDTO, or with status 404 (Not Found)
     */
    @GetMapping("/client-data-ocrs/{id}")
    @Timed
    public ResponseEntity<ClientDataOcrDTO> getClientDataOcr(@PathVariable Long id) {
        log.debug("REST request to get ClientDataOcr : {}", id);
        Optional<ClientDataOcrDTO> clientDataOcrDTO = clientDataOcrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientDataOcrDTO);
    }

    /**
     * DELETE  /client-data-ocrs/:id : delete the "id" clientDataOcr.
     *
     * @param id the id of the clientDataOcrDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-data-ocrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientDataOcr(@PathVariable Long id) {
        log.debug("REST request to delete ClientDataOcr : {}", id);
        clientDataOcrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

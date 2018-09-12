package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.OcrProcessingErrorService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.OcrProcessingErrorDTO;
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
 * REST controller for managing OcrProcessingError.
 */
@RestController
@RequestMapping("/api")
public class OcrProcessingErrorResource {

    private final Logger log = LoggerFactory.getLogger(OcrProcessingErrorResource.class);

    private static final String ENTITY_NAME = "ocrProcessingError";

    private final OcrProcessingErrorService ocrProcessingErrorService;

    public OcrProcessingErrorResource(OcrProcessingErrorService ocrProcessingErrorService) {
        this.ocrProcessingErrorService = ocrProcessingErrorService;
    }

    /**
     * POST  /ocr-processing-errors : Create a new ocrProcessingError.
     *
     * @param ocrProcessingErrorDTO the ocrProcessingErrorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ocrProcessingErrorDTO, or with status 400 (Bad Request) if the ocrProcessingError has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ocr-processing-errors")
    @Timed
    public ResponseEntity<OcrProcessingErrorDTO> createOcrProcessingError(@RequestBody OcrProcessingErrorDTO ocrProcessingErrorDTO) throws URISyntaxException {
        log.debug("REST request to save OcrProcessingError : {}", ocrProcessingErrorDTO);
        if (ocrProcessingErrorDTO.getId() != null) {
            throw new BadRequestAlertException("A new ocrProcessingError cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OcrProcessingErrorDTO result = ocrProcessingErrorService.save(ocrProcessingErrorDTO);
        return ResponseEntity.created(new URI("/api/ocr-processing-errors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ocr-processing-errors : Updates an existing ocrProcessingError.
     *
     * @param ocrProcessingErrorDTO the ocrProcessingErrorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ocrProcessingErrorDTO,
     * or with status 400 (Bad Request) if the ocrProcessingErrorDTO is not valid,
     * or with status 500 (Internal Server Error) if the ocrProcessingErrorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ocr-processing-errors")
    @Timed
    public ResponseEntity<OcrProcessingErrorDTO> updateOcrProcessingError(@RequestBody OcrProcessingErrorDTO ocrProcessingErrorDTO) throws URISyntaxException {
        log.debug("REST request to update OcrProcessingError : {}", ocrProcessingErrorDTO);
        if (ocrProcessingErrorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OcrProcessingErrorDTO result = ocrProcessingErrorService.save(ocrProcessingErrorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ocrProcessingErrorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ocr-processing-errors : get all the ocrProcessingErrors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ocrProcessingErrors in body
     */
    @GetMapping("/ocr-processing-errors")
    @Timed
    public ResponseEntity<List<OcrProcessingErrorDTO>> getAllOcrProcessingErrors(Pageable pageable) {
        log.debug("REST request to get a page of OcrProcessingErrors");
        Page<OcrProcessingErrorDTO> page = ocrProcessingErrorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ocr-processing-errors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ocr-processing-errors/:id : get the "id" ocrProcessingError.
     *
     * @param id the id of the ocrProcessingErrorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ocrProcessingErrorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ocr-processing-errors/{id}")
    @Timed
    public ResponseEntity<OcrProcessingErrorDTO> getOcrProcessingError(@PathVariable Long id) {
        log.debug("REST request to get OcrProcessingError : {}", id);
        Optional<OcrProcessingErrorDTO> ocrProcessingErrorDTO = ocrProcessingErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ocrProcessingErrorDTO);
    }

    /**
     * DELETE  /ocr-processing-errors/:id : delete the "id" ocrProcessingError.
     *
     * @param id the id of the ocrProcessingErrorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ocr-processing-errors/{id}")
    @Timed
    public ResponseEntity<Void> deleteOcrProcessingError(@PathVariable Long id) {
        log.debug("REST request to delete OcrProcessingError : {}", id);
        ocrProcessingErrorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

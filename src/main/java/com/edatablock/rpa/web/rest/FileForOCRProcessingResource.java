package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.FileForOCRProcessingService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.FileForOCRProcessingDTO;
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
 * REST controller for managing FileForOCRProcessing.
 */
@RestController
@RequestMapping("/api")
public class FileForOCRProcessingResource {

    private final Logger log = LoggerFactory.getLogger(FileForOCRProcessingResource.class);

    private static final String ENTITY_NAME = "fileForOCRProcessing";

    private final FileForOCRProcessingService fileForOCRProcessingService;

    public FileForOCRProcessingResource(FileForOCRProcessingService fileForOCRProcessingService) {
        this.fileForOCRProcessingService = fileForOCRProcessingService;
    }

    /**
     * POST  /file-for-ocr-processings : Create a new fileForOCRProcessing.
     *
     * @param fileForOCRProcessingDTO the fileForOCRProcessingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileForOCRProcessingDTO, or with status 400 (Bad Request) if the fileForOCRProcessing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-for-ocr-processings")
    @Timed
    public ResponseEntity<FileForOCRProcessingDTO> createFileForOCRProcessing(@Valid @RequestBody FileForOCRProcessingDTO fileForOCRProcessingDTO) throws URISyntaxException {
        log.debug("REST request to save FileForOCRProcessing : {}", fileForOCRProcessingDTO);
        if (fileForOCRProcessingDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileForOCRProcessing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileForOCRProcessingDTO result = fileForOCRProcessingService.save(fileForOCRProcessingDTO);
        return ResponseEntity.created(new URI("/api/file-for-ocr-processings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-for-ocr-processings : Updates an existing fileForOCRProcessing.
     *
     * @param fileForOCRProcessingDTO the fileForOCRProcessingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileForOCRProcessingDTO,
     * or with status 400 (Bad Request) if the fileForOCRProcessingDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileForOCRProcessingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-for-ocr-processings")
    @Timed
    public ResponseEntity<FileForOCRProcessingDTO> updateFileForOCRProcessing(@Valid @RequestBody FileForOCRProcessingDTO fileForOCRProcessingDTO) throws URISyntaxException {
        log.debug("REST request to update FileForOCRProcessing : {}", fileForOCRProcessingDTO);
        if (fileForOCRProcessingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileForOCRProcessingDTO result = fileForOCRProcessingService.save(fileForOCRProcessingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileForOCRProcessingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-for-ocr-processings : get all the fileForOCRProcessings.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of fileForOCRProcessings in body
     */
    @GetMapping("/file-for-ocr-processings")
    @Timed
    public ResponseEntity<List<FileForOCRProcessingDTO>> getAllFileForOCRProcessings(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("transaction-is-null".equals(filter)) {
            log.debug("REST request to get all FileForOCRProcessings where transaction is null");
            return new ResponseEntity<>(fileForOCRProcessingService.findAllWhereTransactionIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of FileForOCRProcessings");
        Page<FileForOCRProcessingDTO> page = fileForOCRProcessingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-for-ocr-processings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-for-ocr-processings/:id : get the "id" fileForOCRProcessing.
     *
     * @param id the id of the fileForOCRProcessingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileForOCRProcessingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/file-for-ocr-processings/{id}")
    @Timed
    public ResponseEntity<FileForOCRProcessingDTO> getFileForOCRProcessing(@PathVariable Long id) {
        log.debug("REST request to get FileForOCRProcessing : {}", id);
        Optional<FileForOCRProcessingDTO> fileForOCRProcessingDTO = fileForOCRProcessingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileForOCRProcessingDTO);
    }

    /**
     * DELETE  /file-for-ocr-processings/:id : delete the "id" fileForOCRProcessing.
     *
     * @param id the id of the fileForOCRProcessingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-for-ocr-processings/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileForOCRProcessing(@PathVariable Long id) {
        log.debug("REST request to delete FileForOCRProcessing : {}", id);
        fileForOCRProcessingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

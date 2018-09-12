package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.FileToFtpService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.FileToFtpDTO;
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
 * REST controller for managing FileToFtp.
 */
@RestController
@RequestMapping("/api")
public class FileToFtpResource {

    private final Logger log = LoggerFactory.getLogger(FileToFtpResource.class);

    private static final String ENTITY_NAME = "fileToFtp";

    private final FileToFtpService fileToFtpService;

    public FileToFtpResource(FileToFtpService fileToFtpService) {
        this.fileToFtpService = fileToFtpService;
    }

    /**
     * POST  /file-to-ftps : Create a new fileToFtp.
     *
     * @param fileToFtpDTO the fileToFtpDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileToFtpDTO, or with status 400 (Bad Request) if the fileToFtp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-to-ftps")
    @Timed
    public ResponseEntity<FileToFtpDTO> createFileToFtp(@RequestBody FileToFtpDTO fileToFtpDTO) throws URISyntaxException {
        log.debug("REST request to save FileToFtp : {}", fileToFtpDTO);
        if (fileToFtpDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileToFtp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileToFtpDTO result = fileToFtpService.save(fileToFtpDTO);
        return ResponseEntity.created(new URI("/api/file-to-ftps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-to-ftps : Updates an existing fileToFtp.
     *
     * @param fileToFtpDTO the fileToFtpDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileToFtpDTO,
     * or with status 400 (Bad Request) if the fileToFtpDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileToFtpDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-to-ftps")
    @Timed
    public ResponseEntity<FileToFtpDTO> updateFileToFtp(@RequestBody FileToFtpDTO fileToFtpDTO) throws URISyntaxException {
        log.debug("REST request to update FileToFtp : {}", fileToFtpDTO);
        if (fileToFtpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileToFtpDTO result = fileToFtpService.save(fileToFtpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileToFtpDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-to-ftps : get all the fileToFtps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fileToFtps in body
     */
    @GetMapping("/file-to-ftps")
    @Timed
    public ResponseEntity<List<FileToFtpDTO>> getAllFileToFtps(Pageable pageable) {
        log.debug("REST request to get a page of FileToFtps");
        Page<FileToFtpDTO> page = fileToFtpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-to-ftps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-to-ftps/:id : get the "id" fileToFtp.
     *
     * @param id the id of the fileToFtpDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileToFtpDTO, or with status 404 (Not Found)
     */
    @GetMapping("/file-to-ftps/{id}")
    @Timed
    public ResponseEntity<FileToFtpDTO> getFileToFtp(@PathVariable Long id) {
        log.debug("REST request to get FileToFtp : {}", id);
        Optional<FileToFtpDTO> fileToFtpDTO = fileToFtpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileToFtpDTO);
    }

    /**
     * DELETE  /file-to-ftps/:id : delete the "id" fileToFtp.
     *
     * @param id the id of the fileToFtpDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-to-ftps/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileToFtp(@PathVariable Long id) {
        log.debug("REST request to delete FileToFtp : {}", id);
        fileToFtpService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

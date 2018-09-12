package com.edatablock.rpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edatablock.rpa.service.UploadFilesService;
import com.edatablock.rpa.web.rest.errors.BadRequestAlertException;
import com.edatablock.rpa.web.rest.util.HeaderUtil;
import com.edatablock.rpa.web.rest.util.PaginationUtil;
import com.edatablock.rpa.service.dto.UploadFilesDTO;
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
 * REST controller for managing UploadFiles.
 */
@RestController
@RequestMapping("/api")
public class UploadFilesResource {

    private final Logger log = LoggerFactory.getLogger(UploadFilesResource.class);

    private static final String ENTITY_NAME = "uploadFiles";

    private final UploadFilesService uploadFilesService;

    public UploadFilesResource(UploadFilesService uploadFilesService) {
        this.uploadFilesService = uploadFilesService;
    }

    /**
     * POST  /upload-files : Create a new uploadFiles.
     *
     * @param uploadFilesDTO the uploadFilesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadFilesDTO, or with status 400 (Bad Request) if the uploadFiles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/upload-files")
    @Timed
    public ResponseEntity<UploadFilesDTO> createUploadFiles(@Valid @RequestBody UploadFilesDTO uploadFilesDTO) throws URISyntaxException {
        log.debug("REST request to save UploadFiles : {}", uploadFilesDTO);
        if (uploadFilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new uploadFiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadFilesDTO result = uploadFilesService.save(uploadFilesDTO);
        return ResponseEntity.created(new URI("/api/upload-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /upload-files : Updates an existing uploadFiles.
     *
     * @param uploadFilesDTO the uploadFilesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uploadFilesDTO,
     * or with status 400 (Bad Request) if the uploadFilesDTO is not valid,
     * or with status 500 (Internal Server Error) if the uploadFilesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/upload-files")
    @Timed
    public ResponseEntity<UploadFilesDTO> updateUploadFiles(@Valid @RequestBody UploadFilesDTO uploadFilesDTO) throws URISyntaxException {
        log.debug("REST request to update UploadFiles : {}", uploadFilesDTO);
        if (uploadFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadFilesDTO result = uploadFilesService.save(uploadFilesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uploadFilesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /upload-files : get all the uploadFiles.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of uploadFiles in body
     */
    @GetMapping("/upload-files")
    @Timed
    public ResponseEntity<List<UploadFilesDTO>> getAllUploadFiles(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("fileforocrprocessing-is-null".equals(filter)) {
            log.debug("REST request to get all UploadFiless where fileForOCRProcessing is null");
            return new ResponseEntity<>(uploadFilesService.findAllWhereFileForOCRProcessingIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of UploadFiles");
        Page<UploadFilesDTO> page = uploadFilesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/upload-files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /upload-files/:id : get the "id" uploadFiles.
     *
     * @param id the id of the uploadFilesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uploadFilesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/upload-files/{id}")
    @Timed
    public ResponseEntity<UploadFilesDTO> getUploadFiles(@PathVariable Long id) {
        log.debug("REST request to get UploadFiles : {}", id);
        Optional<UploadFilesDTO> uploadFilesDTO = uploadFilesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadFilesDTO);
    }

    /**
     * DELETE  /upload-files/:id : delete the "id" uploadFiles.
     *
     * @param id the id of the uploadFilesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/upload-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteUploadFiles(@PathVariable Long id) {
        log.debug("REST request to delete UploadFiles : {}", id);
        uploadFilesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

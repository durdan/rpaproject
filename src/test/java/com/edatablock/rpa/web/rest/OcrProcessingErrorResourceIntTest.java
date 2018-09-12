package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.OcrProcessingError;
import com.edatablock.rpa.repository.OcrProcessingErrorRepository;
import com.edatablock.rpa.service.OcrProcessingErrorService;
import com.edatablock.rpa.service.dto.OcrProcessingErrorDTO;
import com.edatablock.rpa.service.mapper.OcrProcessingErrorMapper;
import com.edatablock.rpa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.edatablock.rpa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OcrProcessingErrorResource REST controller.
 *
 * @see OcrProcessingErrorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class OcrProcessingErrorResourceIntTest {

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTACHMENT_ID = 1;
    private static final Integer UPDATED_ATTACHMENT_ID = 2;

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ERROR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_TYPE = "BBBBBBBBBB";

    @Autowired
    private OcrProcessingErrorRepository ocrProcessingErrorRepository;


    @Autowired
    private OcrProcessingErrorMapper ocrProcessingErrorMapper;
    

    @Autowired
    private OcrProcessingErrorService ocrProcessingErrorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOcrProcessingErrorMockMvc;

    private OcrProcessingError ocrProcessingError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OcrProcessingErrorResource ocrProcessingErrorResource = new OcrProcessingErrorResource(ocrProcessingErrorService);
        this.restOcrProcessingErrorMockMvc = MockMvcBuilders.standaloneSetup(ocrProcessingErrorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OcrProcessingError createEntity(EntityManager em) {
        OcrProcessingError ocrProcessingError = new OcrProcessingError()
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .messageId(DEFAULT_MESSAGE_ID)
            .attachmentId(DEFAULT_ATTACHMENT_ID)
            .fileId(DEFAULT_FILE_ID)
            .errorMessage(DEFAULT_ERROR_MESSAGE)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .errorType(DEFAULT_ERROR_TYPE);
        return ocrProcessingError;
    }

    @Before
    public void initTest() {
        ocrProcessingError = createEntity(em);
    }

    @Test
    @Transactional
    public void createOcrProcessingError() throws Exception {
        int databaseSizeBeforeCreate = ocrProcessingErrorRepository.findAll().size();

        // Create the OcrProcessingError
        OcrProcessingErrorDTO ocrProcessingErrorDTO = ocrProcessingErrorMapper.toDto(ocrProcessingError);
        restOcrProcessingErrorMockMvc.perform(post("/api/ocr-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocrProcessingErrorDTO)))
            .andExpect(status().isCreated());

        // Validate the OcrProcessingError in the database
        List<OcrProcessingError> ocrProcessingErrorList = ocrProcessingErrorRepository.findAll();
        assertThat(ocrProcessingErrorList).hasSize(databaseSizeBeforeCreate + 1);
        OcrProcessingError testOcrProcessingError = ocrProcessingErrorList.get(ocrProcessingErrorList.size() - 1);
        assertThat(testOcrProcessingError.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testOcrProcessingError.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testOcrProcessingError.getAttachmentId()).isEqualTo(DEFAULT_ATTACHMENT_ID);
        assertThat(testOcrProcessingError.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testOcrProcessingError.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
        assertThat(testOcrProcessingError.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testOcrProcessingError.getErrorType()).isEqualTo(DEFAULT_ERROR_TYPE);
    }

    @Test
    @Transactional
    public void createOcrProcessingErrorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ocrProcessingErrorRepository.findAll().size();

        // Create the OcrProcessingError with an existing ID
        ocrProcessingError.setId(1L);
        OcrProcessingErrorDTO ocrProcessingErrorDTO = ocrProcessingErrorMapper.toDto(ocrProcessingError);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcrProcessingErrorMockMvc.perform(post("/api/ocr-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocrProcessingErrorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OcrProcessingError in the database
        List<OcrProcessingError> ocrProcessingErrorList = ocrProcessingErrorRepository.findAll();
        assertThat(ocrProcessingErrorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOcrProcessingErrors() throws Exception {
        // Initialize the database
        ocrProcessingErrorRepository.saveAndFlush(ocrProcessingError);

        // Get all the ocrProcessingErrorList
        restOcrProcessingErrorMockMvc.perform(get("/api/ocr-processing-errors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocrProcessingError.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].attachmentId").value(hasItem(DEFAULT_ATTACHMENT_ID)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.toString())))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].errorType").value(hasItem(DEFAULT_ERROR_TYPE.toString())));
    }
    

    @Test
    @Transactional
    public void getOcrProcessingError() throws Exception {
        // Initialize the database
        ocrProcessingErrorRepository.saveAndFlush(ocrProcessingError);

        // Get the ocrProcessingError
        restOcrProcessingErrorMockMvc.perform(get("/api/ocr-processing-errors/{id}", ocrProcessingError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ocrProcessingError.getId().intValue()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.attachmentId").value(DEFAULT_ATTACHMENT_ID))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID.toString()))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.errorType").value(DEFAULT_ERROR_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOcrProcessingError() throws Exception {
        // Get the ocrProcessingError
        restOcrProcessingErrorMockMvc.perform(get("/api/ocr-processing-errors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOcrProcessingError() throws Exception {
        // Initialize the database
        ocrProcessingErrorRepository.saveAndFlush(ocrProcessingError);

        int databaseSizeBeforeUpdate = ocrProcessingErrorRepository.findAll().size();

        // Update the ocrProcessingError
        OcrProcessingError updatedOcrProcessingError = ocrProcessingErrorRepository.findById(ocrProcessingError.getId()).get();
        // Disconnect from session so that the updates on updatedOcrProcessingError are not directly saved in db
        em.detach(updatedOcrProcessingError);
        updatedOcrProcessingError
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .messageId(UPDATED_MESSAGE_ID)
            .attachmentId(UPDATED_ATTACHMENT_ID)
            .fileId(UPDATED_FILE_ID)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .errorType(UPDATED_ERROR_TYPE);
        OcrProcessingErrorDTO ocrProcessingErrorDTO = ocrProcessingErrorMapper.toDto(updatedOcrProcessingError);

        restOcrProcessingErrorMockMvc.perform(put("/api/ocr-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocrProcessingErrorDTO)))
            .andExpect(status().isOk());

        // Validate the OcrProcessingError in the database
        List<OcrProcessingError> ocrProcessingErrorList = ocrProcessingErrorRepository.findAll();
        assertThat(ocrProcessingErrorList).hasSize(databaseSizeBeforeUpdate);
        OcrProcessingError testOcrProcessingError = ocrProcessingErrorList.get(ocrProcessingErrorList.size() - 1);
        assertThat(testOcrProcessingError.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testOcrProcessingError.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testOcrProcessingError.getAttachmentId()).isEqualTo(UPDATED_ATTACHMENT_ID);
        assertThat(testOcrProcessingError.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testOcrProcessingError.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testOcrProcessingError.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testOcrProcessingError.getErrorType()).isEqualTo(UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOcrProcessingError() throws Exception {
        int databaseSizeBeforeUpdate = ocrProcessingErrorRepository.findAll().size();

        // Create the OcrProcessingError
        OcrProcessingErrorDTO ocrProcessingErrorDTO = ocrProcessingErrorMapper.toDto(ocrProcessingError);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restOcrProcessingErrorMockMvc.perform(put("/api/ocr-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocrProcessingErrorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OcrProcessingError in the database
        List<OcrProcessingError> ocrProcessingErrorList = ocrProcessingErrorRepository.findAll();
        assertThat(ocrProcessingErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOcrProcessingError() throws Exception {
        // Initialize the database
        ocrProcessingErrorRepository.saveAndFlush(ocrProcessingError);

        int databaseSizeBeforeDelete = ocrProcessingErrorRepository.findAll().size();

        // Get the ocrProcessingError
        restOcrProcessingErrorMockMvc.perform(delete("/api/ocr-processing-errors/{id}", ocrProcessingError.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OcrProcessingError> ocrProcessingErrorList = ocrProcessingErrorRepository.findAll();
        assertThat(ocrProcessingErrorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcrProcessingError.class);
        OcrProcessingError ocrProcessingError1 = new OcrProcessingError();
        ocrProcessingError1.setId(1L);
        OcrProcessingError ocrProcessingError2 = new OcrProcessingError();
        ocrProcessingError2.setId(ocrProcessingError1.getId());
        assertThat(ocrProcessingError1).isEqualTo(ocrProcessingError2);
        ocrProcessingError2.setId(2L);
        assertThat(ocrProcessingError1).isNotEqualTo(ocrProcessingError2);
        ocrProcessingError1.setId(null);
        assertThat(ocrProcessingError1).isNotEqualTo(ocrProcessingError2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcrProcessingErrorDTO.class);
        OcrProcessingErrorDTO ocrProcessingErrorDTO1 = new OcrProcessingErrorDTO();
        ocrProcessingErrorDTO1.setId(1L);
        OcrProcessingErrorDTO ocrProcessingErrorDTO2 = new OcrProcessingErrorDTO();
        assertThat(ocrProcessingErrorDTO1).isNotEqualTo(ocrProcessingErrorDTO2);
        ocrProcessingErrorDTO2.setId(ocrProcessingErrorDTO1.getId());
        assertThat(ocrProcessingErrorDTO1).isEqualTo(ocrProcessingErrorDTO2);
        ocrProcessingErrorDTO2.setId(2L);
        assertThat(ocrProcessingErrorDTO1).isNotEqualTo(ocrProcessingErrorDTO2);
        ocrProcessingErrorDTO1.setId(null);
        assertThat(ocrProcessingErrorDTO1).isNotEqualTo(ocrProcessingErrorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ocrProcessingErrorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ocrProcessingErrorMapper.fromId(null)).isNull();
    }
}

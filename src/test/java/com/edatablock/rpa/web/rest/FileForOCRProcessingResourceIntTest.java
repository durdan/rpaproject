package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.FileForOCRProcessing;
import com.edatablock.rpa.repository.FileForOCRProcessingRepository;
import com.edatablock.rpa.service.FileForOCRProcessingService;
import com.edatablock.rpa.service.dto.FileForOCRProcessingDTO;
import com.edatablock.rpa.service.mapper.FileForOCRProcessingMapper;
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
 * Test class for the FileForOCRProcessingResource REST controller.
 *
 * @see FileForOCRProcessingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class FileForOCRProcessingResourceIntTest {

    private static final String DEFAULT_FILE_INPUT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_INPUT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RETRY = "AAAAAAAAAA";
    private static final String UPDATED_RETRY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FileForOCRProcessingRepository fileForOCRProcessingRepository;


    @Autowired
    private FileForOCRProcessingMapper fileForOCRProcessingMapper;
    

    @Autowired
    private FileForOCRProcessingService fileForOCRProcessingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileForOCRProcessingMockMvc;

    private FileForOCRProcessing fileForOCRProcessing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileForOCRProcessingResource fileForOCRProcessingResource = new FileForOCRProcessingResource(fileForOCRProcessingService);
        this.restFileForOCRProcessingMockMvc = MockMvcBuilders.standaloneSetup(fileForOCRProcessingResource)
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
    public static FileForOCRProcessing createEntity(EntityManager em) {
        FileForOCRProcessing fileForOCRProcessing = new FileForOCRProcessing()
            .fileInputType(DEFAULT_FILE_INPUT_TYPE)
            .status(DEFAULT_STATUS)
            .messageId(DEFAULT_MESSAGE_ID)
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .retry(DEFAULT_RETRY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .createdBy(DEFAULT_CREATED_BY)
            .updateTimeStamp(DEFAULT_UPDATE_TIME_STAMP)
            .updateBy(DEFAULT_UPDATE_BY);
        return fileForOCRProcessing;
    }

    @Before
    public void initTest() {
        fileForOCRProcessing = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileForOCRProcessing() throws Exception {
        int databaseSizeBeforeCreate = fileForOCRProcessingRepository.findAll().size();

        // Create the FileForOCRProcessing
        FileForOCRProcessingDTO fileForOCRProcessingDTO = fileForOCRProcessingMapper.toDto(fileForOCRProcessing);
        restFileForOCRProcessingMockMvc.perform(post("/api/file-for-ocr-processings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileForOCRProcessingDTO)))
            .andExpect(status().isCreated());

        // Validate the FileForOCRProcessing in the database
        List<FileForOCRProcessing> fileForOCRProcessingList = fileForOCRProcessingRepository.findAll();
        assertThat(fileForOCRProcessingList).hasSize(databaseSizeBeforeCreate + 1);
        FileForOCRProcessing testFileForOCRProcessing = fileForOCRProcessingList.get(fileForOCRProcessingList.size() - 1);
        assertThat(testFileForOCRProcessing.getFileInputType()).isEqualTo(DEFAULT_FILE_INPUT_TYPE);
        assertThat(testFileForOCRProcessing.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFileForOCRProcessing.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testFileForOCRProcessing.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testFileForOCRProcessing.getRetry()).isEqualTo(DEFAULT_RETRY);
        assertThat(testFileForOCRProcessing.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testFileForOCRProcessing.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFileForOCRProcessing.getUpdateTimeStamp()).isEqualTo(DEFAULT_UPDATE_TIME_STAMP);
        assertThat(testFileForOCRProcessing.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createFileForOCRProcessingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileForOCRProcessingRepository.findAll().size();

        // Create the FileForOCRProcessing with an existing ID
        fileForOCRProcessing.setId(1L);
        FileForOCRProcessingDTO fileForOCRProcessingDTO = fileForOCRProcessingMapper.toDto(fileForOCRProcessing);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileForOCRProcessingMockMvc.perform(post("/api/file-for-ocr-processings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileForOCRProcessingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileForOCRProcessing in the database
        List<FileForOCRProcessing> fileForOCRProcessingList = fileForOCRProcessingRepository.findAll();
        assertThat(fileForOCRProcessingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFileInputTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileForOCRProcessingRepository.findAll().size();
        // set the field null
        fileForOCRProcessing.setFileInputType(null);

        // Create the FileForOCRProcessing, which fails.
        FileForOCRProcessingDTO fileForOCRProcessingDTO = fileForOCRProcessingMapper.toDto(fileForOCRProcessing);

        restFileForOCRProcessingMockMvc.perform(post("/api/file-for-ocr-processings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileForOCRProcessingDTO)))
            .andExpect(status().isBadRequest());

        List<FileForOCRProcessing> fileForOCRProcessingList = fileForOCRProcessingRepository.findAll();
        assertThat(fileForOCRProcessingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileForOCRProcessings() throws Exception {
        // Initialize the database
        fileForOCRProcessingRepository.saveAndFlush(fileForOCRProcessing);

        // Get all the fileForOCRProcessingList
        restFileForOCRProcessingMockMvc.perform(get("/api/file-for-ocr-processings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileForOCRProcessing.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileInputType").value(hasItem(DEFAULT_FILE_INPUT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].retry").value(hasItem(DEFAULT_RETRY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTimeStamp").value(hasItem(DEFAULT_UPDATE_TIME_STAMP.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())));
    }
    

    @Test
    @Transactional
    public void getFileForOCRProcessing() throws Exception {
        // Initialize the database
        fileForOCRProcessingRepository.saveAndFlush(fileForOCRProcessing);

        // Get the fileForOCRProcessing
        restFileForOCRProcessingMockMvc.perform(get("/api/file-for-ocr-processings/{id}", fileForOCRProcessing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileForOCRProcessing.getId().intValue()))
            .andExpect(jsonPath("$.fileInputType").value(DEFAULT_FILE_INPUT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.retry").value(DEFAULT_RETRY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updateTimeStamp").value(DEFAULT_UPDATE_TIME_STAMP.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFileForOCRProcessing() throws Exception {
        // Get the fileForOCRProcessing
        restFileForOCRProcessingMockMvc.perform(get("/api/file-for-ocr-processings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileForOCRProcessing() throws Exception {
        // Initialize the database
        fileForOCRProcessingRepository.saveAndFlush(fileForOCRProcessing);

        int databaseSizeBeforeUpdate = fileForOCRProcessingRepository.findAll().size();

        // Update the fileForOCRProcessing
        FileForOCRProcessing updatedFileForOCRProcessing = fileForOCRProcessingRepository.findById(fileForOCRProcessing.getId()).get();
        // Disconnect from session so that the updates on updatedFileForOCRProcessing are not directly saved in db
        em.detach(updatedFileForOCRProcessing);
        updatedFileForOCRProcessing
            .fileInputType(UPDATED_FILE_INPUT_TYPE)
            .status(UPDATED_STATUS)
            .messageId(UPDATED_MESSAGE_ID)
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .retry(UPDATED_RETRY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .updateTimeStamp(UPDATED_UPDATE_TIME_STAMP)
            .updateBy(UPDATED_UPDATE_BY);
        FileForOCRProcessingDTO fileForOCRProcessingDTO = fileForOCRProcessingMapper.toDto(updatedFileForOCRProcessing);

        restFileForOCRProcessingMockMvc.perform(put("/api/file-for-ocr-processings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileForOCRProcessingDTO)))
            .andExpect(status().isOk());

        // Validate the FileForOCRProcessing in the database
        List<FileForOCRProcessing> fileForOCRProcessingList = fileForOCRProcessingRepository.findAll();
        assertThat(fileForOCRProcessingList).hasSize(databaseSizeBeforeUpdate);
        FileForOCRProcessing testFileForOCRProcessing = fileForOCRProcessingList.get(fileForOCRProcessingList.size() - 1);
        assertThat(testFileForOCRProcessing.getFileInputType()).isEqualTo(UPDATED_FILE_INPUT_TYPE);
        assertThat(testFileForOCRProcessing.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFileForOCRProcessing.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testFileForOCRProcessing.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testFileForOCRProcessing.getRetry()).isEqualTo(UPDATED_RETRY);
        assertThat(testFileForOCRProcessing.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testFileForOCRProcessing.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFileForOCRProcessing.getUpdateTimeStamp()).isEqualTo(UPDATED_UPDATE_TIME_STAMP);
        assertThat(testFileForOCRProcessing.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingFileForOCRProcessing() throws Exception {
        int databaseSizeBeforeUpdate = fileForOCRProcessingRepository.findAll().size();

        // Create the FileForOCRProcessing
        FileForOCRProcessingDTO fileForOCRProcessingDTO = fileForOCRProcessingMapper.toDto(fileForOCRProcessing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restFileForOCRProcessingMockMvc.perform(put("/api/file-for-ocr-processings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileForOCRProcessingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileForOCRProcessing in the database
        List<FileForOCRProcessing> fileForOCRProcessingList = fileForOCRProcessingRepository.findAll();
        assertThat(fileForOCRProcessingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileForOCRProcessing() throws Exception {
        // Initialize the database
        fileForOCRProcessingRepository.saveAndFlush(fileForOCRProcessing);

        int databaseSizeBeforeDelete = fileForOCRProcessingRepository.findAll().size();

        // Get the fileForOCRProcessing
        restFileForOCRProcessingMockMvc.perform(delete("/api/file-for-ocr-processings/{id}", fileForOCRProcessing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileForOCRProcessing> fileForOCRProcessingList = fileForOCRProcessingRepository.findAll();
        assertThat(fileForOCRProcessingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileForOCRProcessing.class);
        FileForOCRProcessing fileForOCRProcessing1 = new FileForOCRProcessing();
        fileForOCRProcessing1.setId(1L);
        FileForOCRProcessing fileForOCRProcessing2 = new FileForOCRProcessing();
        fileForOCRProcessing2.setId(fileForOCRProcessing1.getId());
        assertThat(fileForOCRProcessing1).isEqualTo(fileForOCRProcessing2);
        fileForOCRProcessing2.setId(2L);
        assertThat(fileForOCRProcessing1).isNotEqualTo(fileForOCRProcessing2);
        fileForOCRProcessing1.setId(null);
        assertThat(fileForOCRProcessing1).isNotEqualTo(fileForOCRProcessing2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileForOCRProcessingDTO.class);
        FileForOCRProcessingDTO fileForOCRProcessingDTO1 = new FileForOCRProcessingDTO();
        fileForOCRProcessingDTO1.setId(1L);
        FileForOCRProcessingDTO fileForOCRProcessingDTO2 = new FileForOCRProcessingDTO();
        assertThat(fileForOCRProcessingDTO1).isNotEqualTo(fileForOCRProcessingDTO2);
        fileForOCRProcessingDTO2.setId(fileForOCRProcessingDTO1.getId());
        assertThat(fileForOCRProcessingDTO1).isEqualTo(fileForOCRProcessingDTO2);
        fileForOCRProcessingDTO2.setId(2L);
        assertThat(fileForOCRProcessingDTO1).isNotEqualTo(fileForOCRProcessingDTO2);
        fileForOCRProcessingDTO1.setId(null);
        assertThat(fileForOCRProcessingDTO1).isNotEqualTo(fileForOCRProcessingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fileForOCRProcessingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fileForOCRProcessingMapper.fromId(null)).isNull();
    }
}

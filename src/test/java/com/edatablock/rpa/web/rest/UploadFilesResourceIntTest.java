package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.UploadFiles;
import com.edatablock.rpa.repository.UploadFilesRepository;
import com.edatablock.rpa.service.UploadFilesService;
import com.edatablock.rpa.service.dto.UploadFilesDTO;
import com.edatablock.rpa.service.mapper.UploadFilesMapper;
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
 * Test class for the UploadFilesResource REST controller.
 *
 * @see UploadFilesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class UploadFilesResourceIntTest {

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_UPLOAD_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOAD_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPLOAD_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_LOCATION = "BBBBBBBBBB";

    @Autowired
    private UploadFilesRepository uploadFilesRepository;


    @Autowired
    private UploadFilesMapper uploadFilesMapper;
    

    @Autowired
    private UploadFilesService uploadFilesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUploadFilesMockMvc;

    private UploadFiles uploadFiles;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadFilesResource uploadFilesResource = new UploadFilesResource(uploadFilesService);
        this.restUploadFilesMockMvc = MockMvcBuilders.standaloneSetup(uploadFilesResource)
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
    public static UploadFiles createEntity(EntityManager em) {
        UploadFiles uploadFiles = new UploadFiles()
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .fileName(DEFAULT_FILE_NAME)
            .fileExtension(DEFAULT_FILE_EXTENSION)
            .uploadBy(DEFAULT_UPLOAD_BY)
            .uploadDateTime(DEFAULT_UPLOAD_DATE_TIME)
            .uploadLocation(DEFAULT_UPLOAD_LOCATION);
        return uploadFiles;
    }

    @Before
    public void initTest() {
        uploadFiles = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadFiles() throws Exception {
        int databaseSizeBeforeCreate = uploadFilesRepository.findAll().size();

        // Create the UploadFiles
        UploadFilesDTO uploadFilesDTO = uploadFilesMapper.toDto(uploadFiles);
        restUploadFilesMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFilesDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadFiles in the database
        List<UploadFiles> uploadFilesList = uploadFilesRepository.findAll();
        assertThat(uploadFilesList).hasSize(databaseSizeBeforeCreate + 1);
        UploadFiles testUploadFiles = uploadFilesList.get(uploadFilesList.size() - 1);
        assertThat(testUploadFiles.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testUploadFiles.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testUploadFiles.getFileExtension()).isEqualTo(DEFAULT_FILE_EXTENSION);
        assertThat(testUploadFiles.getUploadBy()).isEqualTo(DEFAULT_UPLOAD_BY);
        assertThat(testUploadFiles.getUploadDateTime()).isEqualTo(DEFAULT_UPLOAD_DATE_TIME);
        assertThat(testUploadFiles.getUploadLocation()).isEqualTo(DEFAULT_UPLOAD_LOCATION);
    }

    @Test
    @Transactional
    public void createUploadFilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadFilesRepository.findAll().size();

        // Create the UploadFiles with an existing ID
        uploadFiles.setId(1L);
        UploadFilesDTO uploadFilesDTO = uploadFilesMapper.toDto(uploadFiles);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadFilesMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadFiles in the database
        List<UploadFiles> uploadFilesList = uploadFilesRepository.findAll();
        assertThat(uploadFilesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadFilesRepository.findAll().size();
        // set the field null
        uploadFiles.setFileName(null);

        // Create the UploadFiles, which fails.
        UploadFilesDTO uploadFilesDTO = uploadFilesMapper.toDto(uploadFiles);

        restUploadFilesMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFilesDTO)))
            .andExpect(status().isBadRequest());

        List<UploadFiles> uploadFilesList = uploadFilesRepository.findAll();
        assertThat(uploadFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileExtensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadFilesRepository.findAll().size();
        // set the field null
        uploadFiles.setFileExtension(null);

        // Create the UploadFiles, which fails.
        UploadFilesDTO uploadFilesDTO = uploadFilesMapper.toDto(uploadFiles);

        restUploadFilesMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFilesDTO)))
            .andExpect(status().isBadRequest());

        List<UploadFiles> uploadFilesList = uploadFilesRepository.findAll();
        assertThat(uploadFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUploadFiles() throws Exception {
        // Initialize the database
        uploadFilesRepository.saveAndFlush(uploadFiles);

        // Get all the uploadFilesList
        restUploadFilesMockMvc.perform(get("/api/upload-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadFiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION.toString())))
            .andExpect(jsonPath("$.[*].uploadBy").value(hasItem(DEFAULT_UPLOAD_BY.toString())))
            .andExpect(jsonPath("$.[*].uploadDateTime").value(hasItem(DEFAULT_UPLOAD_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].uploadLocation").value(hasItem(DEFAULT_UPLOAD_LOCATION.toString())));
    }
    

    @Test
    @Transactional
    public void getUploadFiles() throws Exception {
        // Initialize the database
        uploadFilesRepository.saveAndFlush(uploadFiles);

        // Get the uploadFiles
        restUploadFilesMockMvc.perform(get("/api/upload-files/{id}", uploadFiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadFiles.getId().intValue()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileExtension").value(DEFAULT_FILE_EXTENSION.toString()))
            .andExpect(jsonPath("$.uploadBy").value(DEFAULT_UPLOAD_BY.toString()))
            .andExpect(jsonPath("$.uploadDateTime").value(DEFAULT_UPLOAD_DATE_TIME.toString()))
            .andExpect(jsonPath("$.uploadLocation").value(DEFAULT_UPLOAD_LOCATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUploadFiles() throws Exception {
        // Get the uploadFiles
        restUploadFilesMockMvc.perform(get("/api/upload-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadFiles() throws Exception {
        // Initialize the database
        uploadFilesRepository.saveAndFlush(uploadFiles);

        int databaseSizeBeforeUpdate = uploadFilesRepository.findAll().size();

        // Update the uploadFiles
        UploadFiles updatedUploadFiles = uploadFilesRepository.findById(uploadFiles.getId()).get();
        // Disconnect from session so that the updates on updatedUploadFiles are not directly saved in db
        em.detach(updatedUploadFiles);
        updatedUploadFiles
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .uploadBy(UPDATED_UPLOAD_BY)
            .uploadDateTime(UPDATED_UPLOAD_DATE_TIME)
            .uploadLocation(UPDATED_UPLOAD_LOCATION);
        UploadFilesDTO uploadFilesDTO = uploadFilesMapper.toDto(updatedUploadFiles);

        restUploadFilesMockMvc.perform(put("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFilesDTO)))
            .andExpect(status().isOk());

        // Validate the UploadFiles in the database
        List<UploadFiles> uploadFilesList = uploadFilesRepository.findAll();
        assertThat(uploadFilesList).hasSize(databaseSizeBeforeUpdate);
        UploadFiles testUploadFiles = uploadFilesList.get(uploadFilesList.size() - 1);
        assertThat(testUploadFiles.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testUploadFiles.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testUploadFiles.getFileExtension()).isEqualTo(UPDATED_FILE_EXTENSION);
        assertThat(testUploadFiles.getUploadBy()).isEqualTo(UPDATED_UPLOAD_BY);
        assertThat(testUploadFiles.getUploadDateTime()).isEqualTo(UPDATED_UPLOAD_DATE_TIME);
        assertThat(testUploadFiles.getUploadLocation()).isEqualTo(UPDATED_UPLOAD_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadFiles() throws Exception {
        int databaseSizeBeforeUpdate = uploadFilesRepository.findAll().size();

        // Create the UploadFiles
        UploadFilesDTO uploadFilesDTO = uploadFilesMapper.toDto(uploadFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restUploadFilesMockMvc.perform(put("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadFilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadFiles in the database
        List<UploadFiles> uploadFilesList = uploadFilesRepository.findAll();
        assertThat(uploadFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadFiles() throws Exception {
        // Initialize the database
        uploadFilesRepository.saveAndFlush(uploadFiles);

        int databaseSizeBeforeDelete = uploadFilesRepository.findAll().size();

        // Get the uploadFiles
        restUploadFilesMockMvc.perform(delete("/api/upload-files/{id}", uploadFiles.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UploadFiles> uploadFilesList = uploadFilesRepository.findAll();
        assertThat(uploadFilesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadFiles.class);
        UploadFiles uploadFiles1 = new UploadFiles();
        uploadFiles1.setId(1L);
        UploadFiles uploadFiles2 = new UploadFiles();
        uploadFiles2.setId(uploadFiles1.getId());
        assertThat(uploadFiles1).isEqualTo(uploadFiles2);
        uploadFiles2.setId(2L);
        assertThat(uploadFiles1).isNotEqualTo(uploadFiles2);
        uploadFiles1.setId(null);
        assertThat(uploadFiles1).isNotEqualTo(uploadFiles2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadFilesDTO.class);
        UploadFilesDTO uploadFilesDTO1 = new UploadFilesDTO();
        uploadFilesDTO1.setId(1L);
        UploadFilesDTO uploadFilesDTO2 = new UploadFilesDTO();
        assertThat(uploadFilesDTO1).isNotEqualTo(uploadFilesDTO2);
        uploadFilesDTO2.setId(uploadFilesDTO1.getId());
        assertThat(uploadFilesDTO1).isEqualTo(uploadFilesDTO2);
        uploadFilesDTO2.setId(2L);
        assertThat(uploadFilesDTO1).isNotEqualTo(uploadFilesDTO2);
        uploadFilesDTO1.setId(null);
        assertThat(uploadFilesDTO1).isNotEqualTo(uploadFilesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uploadFilesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uploadFilesMapper.fromId(null)).isNull();
    }
}

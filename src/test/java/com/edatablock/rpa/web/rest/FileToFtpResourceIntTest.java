package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.FileToFtp;
import com.edatablock.rpa.repository.FileToFtpRepository;
import com.edatablock.rpa.service.FileToFtpService;
import com.edatablock.rpa.service.dto.FileToFtpDTO;
import com.edatablock.rpa.service.mapper.FileToFtpMapper;
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
import java.util.List;


import static com.edatablock.rpa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FileToFtpResource REST controller.
 *
 * @see FileToFtpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class FileToFtpResourceIntTest {

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    @Autowired
    private FileToFtpRepository fileToFtpRepository;


    @Autowired
    private FileToFtpMapper fileToFtpMapper;
    

    @Autowired
    private FileToFtpService fileToFtpService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileToFtpMockMvc;

    private FileToFtp fileToFtp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileToFtpResource fileToFtpResource = new FileToFtpResource(fileToFtpService);
        this.restFileToFtpMockMvc = MockMvcBuilders.standaloneSetup(fileToFtpResource)
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
    public static FileToFtp createEntity(EntityManager em) {
        FileToFtp fileToFtp = new FileToFtp()
            .messageId(DEFAULT_MESSAGE_ID)
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .status(DEFAULT_STATUS)
            .fileType(DEFAULT_FILE_TYPE);
        return fileToFtp;
    }

    @Before
    public void initTest() {
        fileToFtp = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileToFtp() throws Exception {
        int databaseSizeBeforeCreate = fileToFtpRepository.findAll().size();

        // Create the FileToFtp
        FileToFtpDTO fileToFtpDTO = fileToFtpMapper.toDto(fileToFtp);
        restFileToFtpMockMvc.perform(post("/api/file-to-ftps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileToFtpDTO)))
            .andExpect(status().isCreated());

        // Validate the FileToFtp in the database
        List<FileToFtp> fileToFtpList = fileToFtpRepository.findAll();
        assertThat(fileToFtpList).hasSize(databaseSizeBeforeCreate + 1);
        FileToFtp testFileToFtp = fileToFtpList.get(fileToFtpList.size() - 1);
        assertThat(testFileToFtp.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testFileToFtp.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testFileToFtp.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFileToFtp.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
    }

    @Test
    @Transactional
    public void createFileToFtpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileToFtpRepository.findAll().size();

        // Create the FileToFtp with an existing ID
        fileToFtp.setId(1L);
        FileToFtpDTO fileToFtpDTO = fileToFtpMapper.toDto(fileToFtp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileToFtpMockMvc.perform(post("/api/file-to-ftps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileToFtpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileToFtp in the database
        List<FileToFtp> fileToFtpList = fileToFtpRepository.findAll();
        assertThat(fileToFtpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFileToFtps() throws Exception {
        // Initialize the database
        fileToFtpRepository.saveAndFlush(fileToFtp);

        // Get all the fileToFtpList
        restFileToFtpMockMvc.perform(get("/api/file-to-ftps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileToFtp.getId().intValue())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())));
    }
    

    @Test
    @Transactional
    public void getFileToFtp() throws Exception {
        // Initialize the database
        fileToFtpRepository.saveAndFlush(fileToFtp);

        // Get the fileToFtp
        restFileToFtpMockMvc.perform(get("/api/file-to-ftps/{id}", fileToFtp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileToFtp.getId().intValue()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFileToFtp() throws Exception {
        // Get the fileToFtp
        restFileToFtpMockMvc.perform(get("/api/file-to-ftps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileToFtp() throws Exception {
        // Initialize the database
        fileToFtpRepository.saveAndFlush(fileToFtp);

        int databaseSizeBeforeUpdate = fileToFtpRepository.findAll().size();

        // Update the fileToFtp
        FileToFtp updatedFileToFtp = fileToFtpRepository.findById(fileToFtp.getId()).get();
        // Disconnect from session so that the updates on updatedFileToFtp are not directly saved in db
        em.detach(updatedFileToFtp);
        updatedFileToFtp
            .messageId(UPDATED_MESSAGE_ID)
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .status(UPDATED_STATUS)
            .fileType(UPDATED_FILE_TYPE);
        FileToFtpDTO fileToFtpDTO = fileToFtpMapper.toDto(updatedFileToFtp);

        restFileToFtpMockMvc.perform(put("/api/file-to-ftps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileToFtpDTO)))
            .andExpect(status().isOk());

        // Validate the FileToFtp in the database
        List<FileToFtp> fileToFtpList = fileToFtpRepository.findAll();
        assertThat(fileToFtpList).hasSize(databaseSizeBeforeUpdate);
        FileToFtp testFileToFtp = fileToFtpList.get(fileToFtpList.size() - 1);
        assertThat(testFileToFtp.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testFileToFtp.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testFileToFtp.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFileToFtp.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFileToFtp() throws Exception {
        int databaseSizeBeforeUpdate = fileToFtpRepository.findAll().size();

        // Create the FileToFtp
        FileToFtpDTO fileToFtpDTO = fileToFtpMapper.toDto(fileToFtp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restFileToFtpMockMvc.perform(put("/api/file-to-ftps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileToFtpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileToFtp in the database
        List<FileToFtp> fileToFtpList = fileToFtpRepository.findAll();
        assertThat(fileToFtpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileToFtp() throws Exception {
        // Initialize the database
        fileToFtpRepository.saveAndFlush(fileToFtp);

        int databaseSizeBeforeDelete = fileToFtpRepository.findAll().size();

        // Get the fileToFtp
        restFileToFtpMockMvc.perform(delete("/api/file-to-ftps/{id}", fileToFtp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileToFtp> fileToFtpList = fileToFtpRepository.findAll();
        assertThat(fileToFtpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileToFtp.class);
        FileToFtp fileToFtp1 = new FileToFtp();
        fileToFtp1.setId(1L);
        FileToFtp fileToFtp2 = new FileToFtp();
        fileToFtp2.setId(fileToFtp1.getId());
        assertThat(fileToFtp1).isEqualTo(fileToFtp2);
        fileToFtp2.setId(2L);
        assertThat(fileToFtp1).isNotEqualTo(fileToFtp2);
        fileToFtp1.setId(null);
        assertThat(fileToFtp1).isNotEqualTo(fileToFtp2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileToFtpDTO.class);
        FileToFtpDTO fileToFtpDTO1 = new FileToFtpDTO();
        fileToFtpDTO1.setId(1L);
        FileToFtpDTO fileToFtpDTO2 = new FileToFtpDTO();
        assertThat(fileToFtpDTO1).isNotEqualTo(fileToFtpDTO2);
        fileToFtpDTO2.setId(fileToFtpDTO1.getId());
        assertThat(fileToFtpDTO1).isEqualTo(fileToFtpDTO2);
        fileToFtpDTO2.setId(2L);
        assertThat(fileToFtpDTO1).isNotEqualTo(fileToFtpDTO2);
        fileToFtpDTO1.setId(null);
        assertThat(fileToFtpDTO1).isNotEqualTo(fileToFtpDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fileToFtpMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fileToFtpMapper.fromId(null)).isNull();
    }
}

package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.EmailAttachment;
import com.edatablock.rpa.repository.EmailAttachmentRepository;
import com.edatablock.rpa.service.EmailAttachmentService;
import com.edatablock.rpa.service.dto.EmailAttachmentDTO;
import com.edatablock.rpa.service.mapper.EmailAttachmentMapper;
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
 * Test class for the EmailAttachmentResource REST controller.
 *
 * @see EmailAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class EmailAttachmentResourceIntTest {

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_LOCATION = "BBBBBBBBBB";

    @Autowired
    private EmailAttachmentRepository emailAttachmentRepository;


    @Autowired
    private EmailAttachmentMapper emailAttachmentMapper;
    

    @Autowired
    private EmailAttachmentService emailAttachmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailAttachmentMockMvc;

    private EmailAttachment emailAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailAttachmentResource emailAttachmentResource = new EmailAttachmentResource(emailAttachmentService);
        this.restEmailAttachmentMockMvc = MockMvcBuilders.standaloneSetup(emailAttachmentResource)
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
    public static EmailAttachment createEntity(EntityManager em) {
        EmailAttachment emailAttachment = new EmailAttachment()
            .messageId(DEFAULT_MESSAGE_ID)
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .fileName(DEFAULT_FILE_NAME)
            .fileExtension(DEFAULT_FILE_EXTENSION)
            .fileLocation(DEFAULT_FILE_LOCATION);
        return emailAttachment;
    }

    @Before
    public void initTest() {
        emailAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailAttachment() throws Exception {
        int databaseSizeBeforeCreate = emailAttachmentRepository.findAll().size();

        // Create the EmailAttachment
        EmailAttachmentDTO emailAttachmentDTO = emailAttachmentMapper.toDto(emailAttachment);
        restEmailAttachmentMockMvc.perform(post("/api/email-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailAttachment in the database
        List<EmailAttachment> emailAttachmentList = emailAttachmentRepository.findAll();
        assertThat(emailAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        EmailAttachment testEmailAttachment = emailAttachmentList.get(emailAttachmentList.size() - 1);
        assertThat(testEmailAttachment.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testEmailAttachment.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testEmailAttachment.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testEmailAttachment.getFileExtension()).isEqualTo(DEFAULT_FILE_EXTENSION);
        assertThat(testEmailAttachment.getFileLocation()).isEqualTo(DEFAULT_FILE_LOCATION);
    }

    @Test
    @Transactional
    public void createEmailAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailAttachmentRepository.findAll().size();

        // Create the EmailAttachment with an existing ID
        emailAttachment.setId(1L);
        EmailAttachmentDTO emailAttachmentDTO = emailAttachmentMapper.toDto(emailAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailAttachmentMockMvc.perform(post("/api/email-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailAttachment in the database
        List<EmailAttachment> emailAttachmentList = emailAttachmentRepository.findAll();
        assertThat(emailAttachmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAttachmentRepository.findAll().size();
        // set the field null
        emailAttachment.setFileName(null);

        // Create the EmailAttachment, which fails.
        EmailAttachmentDTO emailAttachmentDTO = emailAttachmentMapper.toDto(emailAttachment);

        restEmailAttachmentMockMvc.perform(post("/api/email-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAttachmentDTO)))
            .andExpect(status().isBadRequest());

        List<EmailAttachment> emailAttachmentList = emailAttachmentRepository.findAll();
        assertThat(emailAttachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileExtensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAttachmentRepository.findAll().size();
        // set the field null
        emailAttachment.setFileExtension(null);

        // Create the EmailAttachment, which fails.
        EmailAttachmentDTO emailAttachmentDTO = emailAttachmentMapper.toDto(emailAttachment);

        restEmailAttachmentMockMvc.perform(post("/api/email-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAttachmentDTO)))
            .andExpect(status().isBadRequest());

        List<EmailAttachment> emailAttachmentList = emailAttachmentRepository.findAll();
        assertThat(emailAttachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailAttachments() throws Exception {
        // Initialize the database
        emailAttachmentRepository.saveAndFlush(emailAttachment);

        // Get all the emailAttachmentList
        restEmailAttachmentMockMvc.perform(get("/api/email-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION.toString())))
            .andExpect(jsonPath("$.[*].fileLocation").value(hasItem(DEFAULT_FILE_LOCATION.toString())));
    }
    

    @Test
    @Transactional
    public void getEmailAttachment() throws Exception {
        // Initialize the database
        emailAttachmentRepository.saveAndFlush(emailAttachment);

        // Get the emailAttachment
        restEmailAttachmentMockMvc.perform(get("/api/email-attachments/{id}", emailAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailAttachment.getId().intValue()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileExtension").value(DEFAULT_FILE_EXTENSION.toString()))
            .andExpect(jsonPath("$.fileLocation").value(DEFAULT_FILE_LOCATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmailAttachment() throws Exception {
        // Get the emailAttachment
        restEmailAttachmentMockMvc.perform(get("/api/email-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailAttachment() throws Exception {
        // Initialize the database
        emailAttachmentRepository.saveAndFlush(emailAttachment);

        int databaseSizeBeforeUpdate = emailAttachmentRepository.findAll().size();

        // Update the emailAttachment
        EmailAttachment updatedEmailAttachment = emailAttachmentRepository.findById(emailAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedEmailAttachment are not directly saved in db
        em.detach(updatedEmailAttachment);
        updatedEmailAttachment
            .messageId(UPDATED_MESSAGE_ID)
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .fileLocation(UPDATED_FILE_LOCATION);
        EmailAttachmentDTO emailAttachmentDTO = emailAttachmentMapper.toDto(updatedEmailAttachment);

        restEmailAttachmentMockMvc.perform(put("/api/email-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the EmailAttachment in the database
        List<EmailAttachment> emailAttachmentList = emailAttachmentRepository.findAll();
        assertThat(emailAttachmentList).hasSize(databaseSizeBeforeUpdate);
        EmailAttachment testEmailAttachment = emailAttachmentList.get(emailAttachmentList.size() - 1);
        assertThat(testEmailAttachment.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testEmailAttachment.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testEmailAttachment.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testEmailAttachment.getFileExtension()).isEqualTo(UPDATED_FILE_EXTENSION);
        assertThat(testEmailAttachment.getFileLocation()).isEqualTo(UPDATED_FILE_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailAttachment() throws Exception {
        int databaseSizeBeforeUpdate = emailAttachmentRepository.findAll().size();

        // Create the EmailAttachment
        EmailAttachmentDTO emailAttachmentDTO = emailAttachmentMapper.toDto(emailAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restEmailAttachmentMockMvc.perform(put("/api/email-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailAttachment in the database
        List<EmailAttachment> emailAttachmentList = emailAttachmentRepository.findAll();
        assertThat(emailAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailAttachment() throws Exception {
        // Initialize the database
        emailAttachmentRepository.saveAndFlush(emailAttachment);

        int databaseSizeBeforeDelete = emailAttachmentRepository.findAll().size();

        // Get the emailAttachment
        restEmailAttachmentMockMvc.perform(delete("/api/email-attachments/{id}", emailAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailAttachment> emailAttachmentList = emailAttachmentRepository.findAll();
        assertThat(emailAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailAttachment.class);
        EmailAttachment emailAttachment1 = new EmailAttachment();
        emailAttachment1.setId(1L);
        EmailAttachment emailAttachment2 = new EmailAttachment();
        emailAttachment2.setId(emailAttachment1.getId());
        assertThat(emailAttachment1).isEqualTo(emailAttachment2);
        emailAttachment2.setId(2L);
        assertThat(emailAttachment1).isNotEqualTo(emailAttachment2);
        emailAttachment1.setId(null);
        assertThat(emailAttachment1).isNotEqualTo(emailAttachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailAttachmentDTO.class);
        EmailAttachmentDTO emailAttachmentDTO1 = new EmailAttachmentDTO();
        emailAttachmentDTO1.setId(1L);
        EmailAttachmentDTO emailAttachmentDTO2 = new EmailAttachmentDTO();
        assertThat(emailAttachmentDTO1).isNotEqualTo(emailAttachmentDTO2);
        emailAttachmentDTO2.setId(emailAttachmentDTO1.getId());
        assertThat(emailAttachmentDTO1).isEqualTo(emailAttachmentDTO2);
        emailAttachmentDTO2.setId(2L);
        assertThat(emailAttachmentDTO1).isNotEqualTo(emailAttachmentDTO2);
        emailAttachmentDTO1.setId(null);
        assertThat(emailAttachmentDTO1).isNotEqualTo(emailAttachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailAttachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailAttachmentMapper.fromId(null)).isNull();
    }
}

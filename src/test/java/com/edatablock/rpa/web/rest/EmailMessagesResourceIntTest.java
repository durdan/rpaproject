package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.EmailMessages;
import com.edatablock.rpa.repository.EmailMessagesRepository;
import com.edatablock.rpa.service.EmailMessagesService;
import com.edatablock.rpa.service.dto.EmailMessagesDTO;
import com.edatablock.rpa.service.mapper.EmailMessagesMapper;
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
 * Test class for the EmailMessagesResource REST controller.
 *
 * @see EmailMessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class EmailMessagesResourceIntTest {

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_BODY = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVE_FROM = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVE_FROM = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECEIVED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECEIVED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NUMBER_OF_ATTACHMENTS = 1;
    private static final Integer UPDATED_NUMBER_OF_ATTACHMENTS = 2;

    private static final String DEFAULT_ATTACHMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENTS = "BBBBBBBBBB";

    @Autowired
    private EmailMessagesRepository emailMessagesRepository;


    @Autowired
    private EmailMessagesMapper emailMessagesMapper;
    

    @Autowired
    private EmailMessagesService emailMessagesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailMessagesMockMvc;

    private EmailMessages emailMessages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailMessagesResource emailMessagesResource = new EmailMessagesResource(emailMessagesService);
        this.restEmailMessagesMockMvc = MockMvcBuilders.standaloneSetup(emailMessagesResource)
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
    public static EmailMessages createEntity(EntityManager em) {
        EmailMessages emailMessages = new EmailMessages()
            .messageId(DEFAULT_MESSAGE_ID)
            .emailSubject(DEFAULT_EMAIL_SUBJECT)
            .emailBody(DEFAULT_EMAIL_BODY)
            .status(DEFAULT_STATUS)
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .receiveFrom(DEFAULT_RECEIVE_FROM)
            .receivedTime(DEFAULT_RECEIVED_TIME)
            .numberOfAttachments(DEFAULT_NUMBER_OF_ATTACHMENTS)
            .attachments(DEFAULT_ATTACHMENTS);
        return emailMessages;
    }

    @Before
    public void initTest() {
        emailMessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailMessages() throws Exception {
        int databaseSizeBeforeCreate = emailMessagesRepository.findAll().size();

        // Create the EmailMessages
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);
        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailMessages in the database
        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        EmailMessages testEmailMessages = emailMessagesList.get(emailMessagesList.size() - 1);
        assertThat(testEmailMessages.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testEmailMessages.getEmailSubject()).isEqualTo(DEFAULT_EMAIL_SUBJECT);
        assertThat(testEmailMessages.getEmailBody()).isEqualTo(DEFAULT_EMAIL_BODY);
        assertThat(testEmailMessages.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmailMessages.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testEmailMessages.getReceiveFrom()).isEqualTo(DEFAULT_RECEIVE_FROM);
        assertThat(testEmailMessages.getReceivedTime()).isEqualTo(DEFAULT_RECEIVED_TIME);
        assertThat(testEmailMessages.getNumberOfAttachments()).isEqualTo(DEFAULT_NUMBER_OF_ATTACHMENTS);
        assertThat(testEmailMessages.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
    }

    @Test
    @Transactional
    public void createEmailMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailMessagesRepository.findAll().size();

        // Create the EmailMessages with an existing ID
        emailMessages.setId(1L);
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailMessages in the database
        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMessageIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailMessagesRepository.findAll().size();
        // set the field null
        emailMessages.setMessageId(null);

        // Create the EmailMessages, which fails.
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailMessagesRepository.findAll().size();
        // set the field null
        emailMessages.setEmailSubject(null);

        // Create the EmailMessages, which fails.
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiveFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailMessagesRepository.findAll().size();
        // set the field null
        emailMessages.setReceiveFrom(null);

        // Create the EmailMessages, which fails.
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceivedTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailMessagesRepository.findAll().size();
        // set the field null
        emailMessages.setReceivedTime(null);

        // Create the EmailMessages, which fails.
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfAttachmentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailMessagesRepository.findAll().size();
        // set the field null
        emailMessages.setNumberOfAttachments(null);

        // Create the EmailMessages, which fails.
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttachmentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailMessagesRepository.findAll().size();
        // set the field null
        emailMessages.setAttachments(null);

        // Create the EmailMessages, which fails.
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        restEmailMessagesMockMvc.perform(post("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailMessages() throws Exception {
        // Initialize the database
        emailMessagesRepository.saveAndFlush(emailMessages);

        // Get all the emailMessagesList
        restEmailMessagesMockMvc.perform(get("/api/email-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].emailSubject").value(hasItem(DEFAULT_EMAIL_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].emailBody").value(hasItem(DEFAULT_EMAIL_BODY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].receiveFrom").value(hasItem(DEFAULT_RECEIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].receivedTime").value(hasItem(DEFAULT_RECEIVED_TIME.toString())))
            .andExpect(jsonPath("$.[*].numberOfAttachments").value(hasItem(DEFAULT_NUMBER_OF_ATTACHMENTS)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(DEFAULT_ATTACHMENTS.toString())));
    }
    

    @Test
    @Transactional
    public void getEmailMessages() throws Exception {
        // Initialize the database
        emailMessagesRepository.saveAndFlush(emailMessages);

        // Get the emailMessages
        restEmailMessagesMockMvc.perform(get("/api/email-messages/{id}", emailMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailMessages.getId().intValue()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.emailSubject").value(DEFAULT_EMAIL_SUBJECT.toString()))
            .andExpect(jsonPath("$.emailBody").value(DEFAULT_EMAIL_BODY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.receiveFrom").value(DEFAULT_RECEIVE_FROM.toString()))
            .andExpect(jsonPath("$.receivedTime").value(DEFAULT_RECEIVED_TIME.toString()))
            .andExpect(jsonPath("$.numberOfAttachments").value(DEFAULT_NUMBER_OF_ATTACHMENTS))
            .andExpect(jsonPath("$.attachments").value(DEFAULT_ATTACHMENTS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmailMessages() throws Exception {
        // Get the emailMessages
        restEmailMessagesMockMvc.perform(get("/api/email-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailMessages() throws Exception {
        // Initialize the database
        emailMessagesRepository.saveAndFlush(emailMessages);

        int databaseSizeBeforeUpdate = emailMessagesRepository.findAll().size();

        // Update the emailMessages
        EmailMessages updatedEmailMessages = emailMessagesRepository.findById(emailMessages.getId()).get();
        // Disconnect from session so that the updates on updatedEmailMessages are not directly saved in db
        em.detach(updatedEmailMessages);
        updatedEmailMessages
            .messageId(UPDATED_MESSAGE_ID)
            .emailSubject(UPDATED_EMAIL_SUBJECT)
            .emailBody(UPDATED_EMAIL_BODY)
            .status(UPDATED_STATUS)
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .receiveFrom(UPDATED_RECEIVE_FROM)
            .receivedTime(UPDATED_RECEIVED_TIME)
            .numberOfAttachments(UPDATED_NUMBER_OF_ATTACHMENTS)
            .attachments(UPDATED_ATTACHMENTS);
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(updatedEmailMessages);

        restEmailMessagesMockMvc.perform(put("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isOk());

        // Validate the EmailMessages in the database
        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeUpdate);
        EmailMessages testEmailMessages = emailMessagesList.get(emailMessagesList.size() - 1);
        assertThat(testEmailMessages.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testEmailMessages.getEmailSubject()).isEqualTo(UPDATED_EMAIL_SUBJECT);
        assertThat(testEmailMessages.getEmailBody()).isEqualTo(UPDATED_EMAIL_BODY);
        assertThat(testEmailMessages.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmailMessages.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testEmailMessages.getReceiveFrom()).isEqualTo(UPDATED_RECEIVE_FROM);
        assertThat(testEmailMessages.getReceivedTime()).isEqualTo(UPDATED_RECEIVED_TIME);
        assertThat(testEmailMessages.getNumberOfAttachments()).isEqualTo(UPDATED_NUMBER_OF_ATTACHMENTS);
        assertThat(testEmailMessages.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailMessages() throws Exception {
        int databaseSizeBeforeUpdate = emailMessagesRepository.findAll().size();

        // Create the EmailMessages
        EmailMessagesDTO emailMessagesDTO = emailMessagesMapper.toDto(emailMessages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restEmailMessagesMockMvc.perform(put("/api/email-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailMessages in the database
        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailMessages() throws Exception {
        // Initialize the database
        emailMessagesRepository.saveAndFlush(emailMessages);

        int databaseSizeBeforeDelete = emailMessagesRepository.findAll().size();

        // Get the emailMessages
        restEmailMessagesMockMvc.perform(delete("/api/email-messages/{id}", emailMessages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailMessages> emailMessagesList = emailMessagesRepository.findAll();
        assertThat(emailMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailMessages.class);
        EmailMessages emailMessages1 = new EmailMessages();
        emailMessages1.setId(1L);
        EmailMessages emailMessages2 = new EmailMessages();
        emailMessages2.setId(emailMessages1.getId());
        assertThat(emailMessages1).isEqualTo(emailMessages2);
        emailMessages2.setId(2L);
        assertThat(emailMessages1).isNotEqualTo(emailMessages2);
        emailMessages1.setId(null);
        assertThat(emailMessages1).isNotEqualTo(emailMessages2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailMessagesDTO.class);
        EmailMessagesDTO emailMessagesDTO1 = new EmailMessagesDTO();
        emailMessagesDTO1.setId(1L);
        EmailMessagesDTO emailMessagesDTO2 = new EmailMessagesDTO();
        assertThat(emailMessagesDTO1).isNotEqualTo(emailMessagesDTO2);
        emailMessagesDTO2.setId(emailMessagesDTO1.getId());
        assertThat(emailMessagesDTO1).isEqualTo(emailMessagesDTO2);
        emailMessagesDTO2.setId(2L);
        assertThat(emailMessagesDTO1).isNotEqualTo(emailMessagesDTO2);
        emailMessagesDTO1.setId(null);
        assertThat(emailMessagesDTO1).isNotEqualTo(emailMessagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailMessagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailMessagesMapper.fromId(null)).isNull();
    }
}

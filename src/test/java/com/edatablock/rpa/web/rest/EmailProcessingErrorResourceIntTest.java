package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.EmailProcessingError;
import com.edatablock.rpa.repository.EmailProcessingErrorRepository;
import com.edatablock.rpa.service.EmailProcessingErrorService;
import com.edatablock.rpa.service.dto.EmailProcessingErrorDTO;
import com.edatablock.rpa.service.mapper.EmailProcessingErrorMapper;
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
 * Test class for the EmailProcessingErrorResource REST controller.
 *
 * @see EmailProcessingErrorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class EmailProcessingErrorResourceIntTest {

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVE_FROM = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVE_FROM = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECEIVED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECEIVED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmailProcessingErrorRepository emailProcessingErrorRepository;


    @Autowired
    private EmailProcessingErrorMapper emailProcessingErrorMapper;
    

    @Autowired
    private EmailProcessingErrorService emailProcessingErrorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailProcessingErrorMockMvc;

    private EmailProcessingError emailProcessingError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailProcessingErrorResource emailProcessingErrorResource = new EmailProcessingErrorResource(emailProcessingErrorService);
        this.restEmailProcessingErrorMockMvc = MockMvcBuilders.standaloneSetup(emailProcessingErrorResource)
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
    public static EmailProcessingError createEntity(EntityManager em) {
        EmailProcessingError emailProcessingError = new EmailProcessingError()
            .errorMessage(DEFAULT_ERROR_MESSAGE)
            .messageID(DEFAULT_MESSAGE_ID)
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .receiveFrom(DEFAULT_RECEIVE_FROM)
            .receivedTime(DEFAULT_RECEIVED_TIME);
        return emailProcessingError;
    }

    @Before
    public void initTest() {
        emailProcessingError = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailProcessingError() throws Exception {
        int databaseSizeBeforeCreate = emailProcessingErrorRepository.findAll().size();

        // Create the EmailProcessingError
        EmailProcessingErrorDTO emailProcessingErrorDTO = emailProcessingErrorMapper.toDto(emailProcessingError);
        restEmailProcessingErrorMockMvc.perform(post("/api/email-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailProcessingErrorDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailProcessingError in the database
        List<EmailProcessingError> emailProcessingErrorList = emailProcessingErrorRepository.findAll();
        assertThat(emailProcessingErrorList).hasSize(databaseSizeBeforeCreate + 1);
        EmailProcessingError testEmailProcessingError = emailProcessingErrorList.get(emailProcessingErrorList.size() - 1);
        assertThat(testEmailProcessingError.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
        assertThat(testEmailProcessingError.getMessageID()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testEmailProcessingError.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testEmailProcessingError.getReceiveFrom()).isEqualTo(DEFAULT_RECEIVE_FROM);
        assertThat(testEmailProcessingError.getReceivedTime()).isEqualTo(DEFAULT_RECEIVED_TIME);
    }

    @Test
    @Transactional
    public void createEmailProcessingErrorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailProcessingErrorRepository.findAll().size();

        // Create the EmailProcessingError with an existing ID
        emailProcessingError.setId(1L);
        EmailProcessingErrorDTO emailProcessingErrorDTO = emailProcessingErrorMapper.toDto(emailProcessingError);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailProcessingErrorMockMvc.perform(post("/api/email-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailProcessingErrorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailProcessingError in the database
        List<EmailProcessingError> emailProcessingErrorList = emailProcessingErrorRepository.findAll();
        assertThat(emailProcessingErrorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMessageIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailProcessingErrorRepository.findAll().size();
        // set the field null
        emailProcessingError.setMessageID(null);

        // Create the EmailProcessingError, which fails.
        EmailProcessingErrorDTO emailProcessingErrorDTO = emailProcessingErrorMapper.toDto(emailProcessingError);

        restEmailProcessingErrorMockMvc.perform(post("/api/email-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailProcessingErrorDTO)))
            .andExpect(status().isBadRequest());

        List<EmailProcessingError> emailProcessingErrorList = emailProcessingErrorRepository.findAll();
        assertThat(emailProcessingErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiveFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailProcessingErrorRepository.findAll().size();
        // set the field null
        emailProcessingError.setReceiveFrom(null);

        // Create the EmailProcessingError, which fails.
        EmailProcessingErrorDTO emailProcessingErrorDTO = emailProcessingErrorMapper.toDto(emailProcessingError);

        restEmailProcessingErrorMockMvc.perform(post("/api/email-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailProcessingErrorDTO)))
            .andExpect(status().isBadRequest());

        List<EmailProcessingError> emailProcessingErrorList = emailProcessingErrorRepository.findAll();
        assertThat(emailProcessingErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailProcessingErrors() throws Exception {
        // Initialize the database
        emailProcessingErrorRepository.saveAndFlush(emailProcessingError);

        // Get all the emailProcessingErrorList
        restEmailProcessingErrorMockMvc.perform(get("/api/email-processing-errors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailProcessingError.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].messageID").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].receiveFrom").value(hasItem(DEFAULT_RECEIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].receivedTime").value(hasItem(DEFAULT_RECEIVED_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getEmailProcessingError() throws Exception {
        // Initialize the database
        emailProcessingErrorRepository.saveAndFlush(emailProcessingError);

        // Get the emailProcessingError
        restEmailProcessingErrorMockMvc.perform(get("/api/email-processing-errors/{id}", emailProcessingError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailProcessingError.getId().intValue()))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE.toString()))
            .andExpect(jsonPath("$.messageID").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.receiveFrom").value(DEFAULT_RECEIVE_FROM.toString()))
            .andExpect(jsonPath("$.receivedTime").value(DEFAULT_RECEIVED_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmailProcessingError() throws Exception {
        // Get the emailProcessingError
        restEmailProcessingErrorMockMvc.perform(get("/api/email-processing-errors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailProcessingError() throws Exception {
        // Initialize the database
        emailProcessingErrorRepository.saveAndFlush(emailProcessingError);

        int databaseSizeBeforeUpdate = emailProcessingErrorRepository.findAll().size();

        // Update the emailProcessingError
        EmailProcessingError updatedEmailProcessingError = emailProcessingErrorRepository.findById(emailProcessingError.getId()).get();
        // Disconnect from session so that the updates on updatedEmailProcessingError are not directly saved in db
        em.detach(updatedEmailProcessingError);
        updatedEmailProcessingError
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .messageID(UPDATED_MESSAGE_ID)
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .receiveFrom(UPDATED_RECEIVE_FROM)
            .receivedTime(UPDATED_RECEIVED_TIME);
        EmailProcessingErrorDTO emailProcessingErrorDTO = emailProcessingErrorMapper.toDto(updatedEmailProcessingError);

        restEmailProcessingErrorMockMvc.perform(put("/api/email-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailProcessingErrorDTO)))
            .andExpect(status().isOk());

        // Validate the EmailProcessingError in the database
        List<EmailProcessingError> emailProcessingErrorList = emailProcessingErrorRepository.findAll();
        assertThat(emailProcessingErrorList).hasSize(databaseSizeBeforeUpdate);
        EmailProcessingError testEmailProcessingError = emailProcessingErrorList.get(emailProcessingErrorList.size() - 1);
        assertThat(testEmailProcessingError.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testEmailProcessingError.getMessageID()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testEmailProcessingError.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testEmailProcessingError.getReceiveFrom()).isEqualTo(UPDATED_RECEIVE_FROM);
        assertThat(testEmailProcessingError.getReceivedTime()).isEqualTo(UPDATED_RECEIVED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailProcessingError() throws Exception {
        int databaseSizeBeforeUpdate = emailProcessingErrorRepository.findAll().size();

        // Create the EmailProcessingError
        EmailProcessingErrorDTO emailProcessingErrorDTO = emailProcessingErrorMapper.toDto(emailProcessingError);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restEmailProcessingErrorMockMvc.perform(put("/api/email-processing-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailProcessingErrorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailProcessingError in the database
        List<EmailProcessingError> emailProcessingErrorList = emailProcessingErrorRepository.findAll();
        assertThat(emailProcessingErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailProcessingError() throws Exception {
        // Initialize the database
        emailProcessingErrorRepository.saveAndFlush(emailProcessingError);

        int databaseSizeBeforeDelete = emailProcessingErrorRepository.findAll().size();

        // Get the emailProcessingError
        restEmailProcessingErrorMockMvc.perform(delete("/api/email-processing-errors/{id}", emailProcessingError.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailProcessingError> emailProcessingErrorList = emailProcessingErrorRepository.findAll();
        assertThat(emailProcessingErrorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailProcessingError.class);
        EmailProcessingError emailProcessingError1 = new EmailProcessingError();
        emailProcessingError1.setId(1L);
        EmailProcessingError emailProcessingError2 = new EmailProcessingError();
        emailProcessingError2.setId(emailProcessingError1.getId());
        assertThat(emailProcessingError1).isEqualTo(emailProcessingError2);
        emailProcessingError2.setId(2L);
        assertThat(emailProcessingError1).isNotEqualTo(emailProcessingError2);
        emailProcessingError1.setId(null);
        assertThat(emailProcessingError1).isNotEqualTo(emailProcessingError2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailProcessingErrorDTO.class);
        EmailProcessingErrorDTO emailProcessingErrorDTO1 = new EmailProcessingErrorDTO();
        emailProcessingErrorDTO1.setId(1L);
        EmailProcessingErrorDTO emailProcessingErrorDTO2 = new EmailProcessingErrorDTO();
        assertThat(emailProcessingErrorDTO1).isNotEqualTo(emailProcessingErrorDTO2);
        emailProcessingErrorDTO2.setId(emailProcessingErrorDTO1.getId());
        assertThat(emailProcessingErrorDTO1).isEqualTo(emailProcessingErrorDTO2);
        emailProcessingErrorDTO2.setId(2L);
        assertThat(emailProcessingErrorDTO1).isNotEqualTo(emailProcessingErrorDTO2);
        emailProcessingErrorDTO1.setId(null);
        assertThat(emailProcessingErrorDTO1).isNotEqualTo(emailProcessingErrorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailProcessingErrorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailProcessingErrorMapper.fromId(null)).isNull();
    }
}

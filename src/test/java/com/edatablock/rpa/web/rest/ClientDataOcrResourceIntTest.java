package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.ClientDataOcr;
import com.edatablock.rpa.repository.ClientDataOcrRepository;
import com.edatablock.rpa.service.ClientDataOcrService;
import com.edatablock.rpa.service.dto.ClientDataOcrDTO;
import com.edatablock.rpa.service.mapper.ClientDataOcrMapper;
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
 * Test class for the ClientDataOcrResource REST controller.
 *
 * @see ClientDataOcrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class ClientDataOcrResourceIntTest {

    private static final String DEFAULT_KEY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_KEY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTACHMENT_ID = 1;
    private static final Integer UPDATED_ATTACHMENT_ID = 2;

    @Autowired
    private ClientDataOcrRepository clientDataOcrRepository;


    @Autowired
    private ClientDataOcrMapper clientDataOcrMapper;
    

    @Autowired
    private ClientDataOcrService clientDataOcrService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientDataOcrMockMvc;

    private ClientDataOcr clientDataOcr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientDataOcrResource clientDataOcrResource = new ClientDataOcrResource(clientDataOcrService);
        this.restClientDataOcrMockMvc = MockMvcBuilders.standaloneSetup(clientDataOcrResource)
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
    public static ClientDataOcr createEntity(EntityManager em) {
        ClientDataOcr clientDataOcr = new ClientDataOcr()
            .keyName(DEFAULT_KEY_NAME)
            .value(DEFAULT_VALUE)
            .messageId(DEFAULT_MESSAGE_ID)
            .clientEmailAddress(DEFAULT_CLIENT_EMAIL_ADDRESS)
            .attachmentId(DEFAULT_ATTACHMENT_ID);
        return clientDataOcr;
    }

    @Before
    public void initTest() {
        clientDataOcr = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientDataOcr() throws Exception {
        int databaseSizeBeforeCreate = clientDataOcrRepository.findAll().size();

        // Create the ClientDataOcr
        ClientDataOcrDTO clientDataOcrDTO = clientDataOcrMapper.toDto(clientDataOcr);
        restClientDataOcrMockMvc.perform(post("/api/client-data-ocrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDataOcrDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientDataOcr in the database
        List<ClientDataOcr> clientDataOcrList = clientDataOcrRepository.findAll();
        assertThat(clientDataOcrList).hasSize(databaseSizeBeforeCreate + 1);
        ClientDataOcr testClientDataOcr = clientDataOcrList.get(clientDataOcrList.size() - 1);
        assertThat(testClientDataOcr.getKeyName()).isEqualTo(DEFAULT_KEY_NAME);
        assertThat(testClientDataOcr.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testClientDataOcr.getMessageId()).isEqualTo(DEFAULT_MESSAGE_ID);
        assertThat(testClientDataOcr.getClientEmailAddress()).isEqualTo(DEFAULT_CLIENT_EMAIL_ADDRESS);
        assertThat(testClientDataOcr.getAttachmentId()).isEqualTo(DEFAULT_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void createClientDataOcrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientDataOcrRepository.findAll().size();

        // Create the ClientDataOcr with an existing ID
        clientDataOcr.setId(1L);
        ClientDataOcrDTO clientDataOcrDTO = clientDataOcrMapper.toDto(clientDataOcr);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientDataOcrMockMvc.perform(post("/api/client-data-ocrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDataOcrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientDataOcr in the database
        List<ClientDataOcr> clientDataOcrList = clientDataOcrRepository.findAll();
        assertThat(clientDataOcrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientDataOcrs() throws Exception {
        // Initialize the database
        clientDataOcrRepository.saveAndFlush(clientDataOcr);

        // Get all the clientDataOcrList
        restClientDataOcrMockMvc.perform(get("/api/client-data-ocrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientDataOcr.getId().intValue())))
            .andExpect(jsonPath("$.[*].keyName").value(hasItem(DEFAULT_KEY_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].messageId").value(hasItem(DEFAULT_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].clientEmailAddress").value(hasItem(DEFAULT_CLIENT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].attachmentId").value(hasItem(DEFAULT_ATTACHMENT_ID)));
    }
    

    @Test
    @Transactional
    public void getClientDataOcr() throws Exception {
        // Initialize the database
        clientDataOcrRepository.saveAndFlush(clientDataOcr);

        // Get the clientDataOcr
        restClientDataOcrMockMvc.perform(get("/api/client-data-ocrs/{id}", clientDataOcr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientDataOcr.getId().intValue()))
            .andExpect(jsonPath("$.keyName").value(DEFAULT_KEY_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.messageId").value(DEFAULT_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.clientEmailAddress").value(DEFAULT_CLIENT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.attachmentId").value(DEFAULT_ATTACHMENT_ID));
    }
    @Test
    @Transactional
    public void getNonExistingClientDataOcr() throws Exception {
        // Get the clientDataOcr
        restClientDataOcrMockMvc.perform(get("/api/client-data-ocrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientDataOcr() throws Exception {
        // Initialize the database
        clientDataOcrRepository.saveAndFlush(clientDataOcr);

        int databaseSizeBeforeUpdate = clientDataOcrRepository.findAll().size();

        // Update the clientDataOcr
        ClientDataOcr updatedClientDataOcr = clientDataOcrRepository.findById(clientDataOcr.getId()).get();
        // Disconnect from session so that the updates on updatedClientDataOcr are not directly saved in db
        em.detach(updatedClientDataOcr);
        updatedClientDataOcr
            .keyName(UPDATED_KEY_NAME)
            .value(UPDATED_VALUE)
            .messageId(UPDATED_MESSAGE_ID)
            .clientEmailAddress(UPDATED_CLIENT_EMAIL_ADDRESS)
            .attachmentId(UPDATED_ATTACHMENT_ID);
        ClientDataOcrDTO clientDataOcrDTO = clientDataOcrMapper.toDto(updatedClientDataOcr);

        restClientDataOcrMockMvc.perform(put("/api/client-data-ocrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDataOcrDTO)))
            .andExpect(status().isOk());

        // Validate the ClientDataOcr in the database
        List<ClientDataOcr> clientDataOcrList = clientDataOcrRepository.findAll();
        assertThat(clientDataOcrList).hasSize(databaseSizeBeforeUpdate);
        ClientDataOcr testClientDataOcr = clientDataOcrList.get(clientDataOcrList.size() - 1);
        assertThat(testClientDataOcr.getKeyName()).isEqualTo(UPDATED_KEY_NAME);
        assertThat(testClientDataOcr.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClientDataOcr.getMessageId()).isEqualTo(UPDATED_MESSAGE_ID);
        assertThat(testClientDataOcr.getClientEmailAddress()).isEqualTo(UPDATED_CLIENT_EMAIL_ADDRESS);
        assertThat(testClientDataOcr.getAttachmentId()).isEqualTo(UPDATED_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClientDataOcr() throws Exception {
        int databaseSizeBeforeUpdate = clientDataOcrRepository.findAll().size();

        // Create the ClientDataOcr
        ClientDataOcrDTO clientDataOcrDTO = clientDataOcrMapper.toDto(clientDataOcr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restClientDataOcrMockMvc.perform(put("/api/client-data-ocrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientDataOcrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientDataOcr in the database
        List<ClientDataOcr> clientDataOcrList = clientDataOcrRepository.findAll();
        assertThat(clientDataOcrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientDataOcr() throws Exception {
        // Initialize the database
        clientDataOcrRepository.saveAndFlush(clientDataOcr);

        int databaseSizeBeforeDelete = clientDataOcrRepository.findAll().size();

        // Get the clientDataOcr
        restClientDataOcrMockMvc.perform(delete("/api/client-data-ocrs/{id}", clientDataOcr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientDataOcr> clientDataOcrList = clientDataOcrRepository.findAll();
        assertThat(clientDataOcrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDataOcr.class);
        ClientDataOcr clientDataOcr1 = new ClientDataOcr();
        clientDataOcr1.setId(1L);
        ClientDataOcr clientDataOcr2 = new ClientDataOcr();
        clientDataOcr2.setId(clientDataOcr1.getId());
        assertThat(clientDataOcr1).isEqualTo(clientDataOcr2);
        clientDataOcr2.setId(2L);
        assertThat(clientDataOcr1).isNotEqualTo(clientDataOcr2);
        clientDataOcr1.setId(null);
        assertThat(clientDataOcr1).isNotEqualTo(clientDataOcr2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDataOcrDTO.class);
        ClientDataOcrDTO clientDataOcrDTO1 = new ClientDataOcrDTO();
        clientDataOcrDTO1.setId(1L);
        ClientDataOcrDTO clientDataOcrDTO2 = new ClientDataOcrDTO();
        assertThat(clientDataOcrDTO1).isNotEqualTo(clientDataOcrDTO2);
        clientDataOcrDTO2.setId(clientDataOcrDTO1.getId());
        assertThat(clientDataOcrDTO1).isEqualTo(clientDataOcrDTO2);
        clientDataOcrDTO2.setId(2L);
        assertThat(clientDataOcrDTO1).isNotEqualTo(clientDataOcrDTO2);
        clientDataOcrDTO1.setId(null);
        assertThat(clientDataOcrDTO1).isNotEqualTo(clientDataOcrDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientDataOcrMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientDataOcrMapper.fromId(null)).isNull();
    }
}

package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.ClientEmailDomain;
import com.edatablock.rpa.repository.ClientEmailDomainRepository;
import com.edatablock.rpa.service.ClientEmailDomainService;
import com.edatablock.rpa.service.dto.ClientEmailDomainDTO;
import com.edatablock.rpa.service.mapper.ClientEmailDomainMapper;
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
 * Test class for the ClientEmailDomainResource REST controller.
 *
 * @see ClientEmailDomainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class ClientEmailDomainResourceIntTest {

    private static final String DEFAULT_EMAIL_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_DOMAIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    @Autowired
    private ClientEmailDomainRepository clientEmailDomainRepository;


    @Autowired
    private ClientEmailDomainMapper clientEmailDomainMapper;
    

    @Autowired
    private ClientEmailDomainService clientEmailDomainService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientEmailDomainMockMvc;

    private ClientEmailDomain clientEmailDomain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientEmailDomainResource clientEmailDomainResource = new ClientEmailDomainResource(clientEmailDomainService);
        this.restClientEmailDomainMockMvc = MockMvcBuilders.standaloneSetup(clientEmailDomainResource)
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
    public static ClientEmailDomain createEntity(EntityManager em) {
        ClientEmailDomain clientEmailDomain = new ClientEmailDomain()
            .emailDomain(DEFAULT_EMAIL_DOMAIN)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return clientEmailDomain;
    }

    @Before
    public void initTest() {
        clientEmailDomain = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientEmailDomain() throws Exception {
        int databaseSizeBeforeCreate = clientEmailDomainRepository.findAll().size();

        // Create the ClientEmailDomain
        ClientEmailDomainDTO clientEmailDomainDTO = clientEmailDomainMapper.toDto(clientEmailDomain);
        restClientEmailDomainMockMvc.perform(post("/api/client-email-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmailDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientEmailDomain in the database
        List<ClientEmailDomain> clientEmailDomainList = clientEmailDomainRepository.findAll();
        assertThat(clientEmailDomainList).hasSize(databaseSizeBeforeCreate + 1);
        ClientEmailDomain testClientEmailDomain = clientEmailDomainList.get(clientEmailDomainList.size() - 1);
        assertThat(testClientEmailDomain.getEmailDomain()).isEqualTo(DEFAULT_EMAIL_DOMAIN);
        assertThat(testClientEmailDomain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClientEmailDomain.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createClientEmailDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientEmailDomainRepository.findAll().size();

        // Create the ClientEmailDomain with an existing ID
        clientEmailDomain.setId(1L);
        ClientEmailDomainDTO clientEmailDomainDTO = clientEmailDomainMapper.toDto(clientEmailDomain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientEmailDomainMockMvc.perform(post("/api/client-email-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmailDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientEmailDomain in the database
        List<ClientEmailDomain> clientEmailDomainList = clientEmailDomainRepository.findAll();
        assertThat(clientEmailDomainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientEmailDomainRepository.findAll().size();
        // set the field null
        clientEmailDomain.setEmailDomain(null);

        // Create the ClientEmailDomain, which fails.
        ClientEmailDomainDTO clientEmailDomainDTO = clientEmailDomainMapper.toDto(clientEmailDomain);

        restClientEmailDomainMockMvc.perform(post("/api/client-email-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmailDomainDTO)))
            .andExpect(status().isBadRequest());

        List<ClientEmailDomain> clientEmailDomainList = clientEmailDomainRepository.findAll();
        assertThat(clientEmailDomainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientEmailDomains() throws Exception {
        // Initialize the database
        clientEmailDomainRepository.saveAndFlush(clientEmailDomain);

        // Get all the clientEmailDomainList
        restClientEmailDomainMockMvc.perform(get("/api/client-email-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientEmailDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailDomain").value(hasItem(DEFAULT_EMAIL_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }
    

    @Test
    @Transactional
    public void getClientEmailDomain() throws Exception {
        // Initialize the database
        clientEmailDomainRepository.saveAndFlush(clientEmailDomain);

        // Get the clientEmailDomain
        restClientEmailDomainMockMvc.perform(get("/api/client-email-domains/{id}", clientEmailDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientEmailDomain.getId().intValue()))
            .andExpect(jsonPath("$.emailDomain").value(DEFAULT_EMAIL_DOMAIN.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }
    @Test
    @Transactional
    public void getNonExistingClientEmailDomain() throws Exception {
        // Get the clientEmailDomain
        restClientEmailDomainMockMvc.perform(get("/api/client-email-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientEmailDomain() throws Exception {
        // Initialize the database
        clientEmailDomainRepository.saveAndFlush(clientEmailDomain);

        int databaseSizeBeforeUpdate = clientEmailDomainRepository.findAll().size();

        // Update the clientEmailDomain
        ClientEmailDomain updatedClientEmailDomain = clientEmailDomainRepository.findById(clientEmailDomain.getId()).get();
        // Disconnect from session so that the updates on updatedClientEmailDomain are not directly saved in db
        em.detach(updatedClientEmailDomain);
        updatedClientEmailDomain
            .emailDomain(UPDATED_EMAIL_DOMAIN)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        ClientEmailDomainDTO clientEmailDomainDTO = clientEmailDomainMapper.toDto(updatedClientEmailDomain);

        restClientEmailDomainMockMvc.perform(put("/api/client-email-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmailDomainDTO)))
            .andExpect(status().isOk());

        // Validate the ClientEmailDomain in the database
        List<ClientEmailDomain> clientEmailDomainList = clientEmailDomainRepository.findAll();
        assertThat(clientEmailDomainList).hasSize(databaseSizeBeforeUpdate);
        ClientEmailDomain testClientEmailDomain = clientEmailDomainList.get(clientEmailDomainList.size() - 1);
        assertThat(testClientEmailDomain.getEmailDomain()).isEqualTo(UPDATED_EMAIL_DOMAIN);
        assertThat(testClientEmailDomain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientEmailDomain.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingClientEmailDomain() throws Exception {
        int databaseSizeBeforeUpdate = clientEmailDomainRepository.findAll().size();

        // Create the ClientEmailDomain
        ClientEmailDomainDTO clientEmailDomainDTO = clientEmailDomainMapper.toDto(clientEmailDomain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restClientEmailDomainMockMvc.perform(put("/api/client-email-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmailDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientEmailDomain in the database
        List<ClientEmailDomain> clientEmailDomainList = clientEmailDomainRepository.findAll();
        assertThat(clientEmailDomainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientEmailDomain() throws Exception {
        // Initialize the database
        clientEmailDomainRepository.saveAndFlush(clientEmailDomain);

        int databaseSizeBeforeDelete = clientEmailDomainRepository.findAll().size();

        // Get the clientEmailDomain
        restClientEmailDomainMockMvc.perform(delete("/api/client-email-domains/{id}", clientEmailDomain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientEmailDomain> clientEmailDomainList = clientEmailDomainRepository.findAll();
        assertThat(clientEmailDomainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientEmailDomain.class);
        ClientEmailDomain clientEmailDomain1 = new ClientEmailDomain();
        clientEmailDomain1.setId(1L);
        ClientEmailDomain clientEmailDomain2 = new ClientEmailDomain();
        clientEmailDomain2.setId(clientEmailDomain1.getId());
        assertThat(clientEmailDomain1).isEqualTo(clientEmailDomain2);
        clientEmailDomain2.setId(2L);
        assertThat(clientEmailDomain1).isNotEqualTo(clientEmailDomain2);
        clientEmailDomain1.setId(null);
        assertThat(clientEmailDomain1).isNotEqualTo(clientEmailDomain2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientEmailDomainDTO.class);
        ClientEmailDomainDTO clientEmailDomainDTO1 = new ClientEmailDomainDTO();
        clientEmailDomainDTO1.setId(1L);
        ClientEmailDomainDTO clientEmailDomainDTO2 = new ClientEmailDomainDTO();
        assertThat(clientEmailDomainDTO1).isNotEqualTo(clientEmailDomainDTO2);
        clientEmailDomainDTO2.setId(clientEmailDomainDTO1.getId());
        assertThat(clientEmailDomainDTO1).isEqualTo(clientEmailDomainDTO2);
        clientEmailDomainDTO2.setId(2L);
        assertThat(clientEmailDomainDTO1).isNotEqualTo(clientEmailDomainDTO2);
        clientEmailDomainDTO1.setId(null);
        assertThat(clientEmailDomainDTO1).isNotEqualTo(clientEmailDomainDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientEmailDomainMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientEmailDomainMapper.fromId(null)).isNull();
    }
}

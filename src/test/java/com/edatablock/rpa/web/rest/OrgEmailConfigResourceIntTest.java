package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.OrgEmailConfig;
import com.edatablock.rpa.repository.OrgEmailConfigRepository;
import com.edatablock.rpa.service.OrgEmailConfigService;
import com.edatablock.rpa.service.dto.OrgEmailConfigDTO;
import com.edatablock.rpa.service.mapper.OrgEmailConfigMapper;
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
 * Test class for the OrgEmailConfigResource REST controller.
 *
 * @see OrgEmailConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class OrgEmailConfigResourceIntTest {

    private static final String DEFAULT_EMAIL_SERVER_HOST = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SERVER_HOST = "BBBBBBBBBB";

    private static final Integer DEFAULT_EMAIL_SERVER_PORT = 1;
    private static final Integer UPDATED_EMAIL_SERVER_PORT = 2;

    private static final String DEFAULT_EMAIL_SERVER_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SERVER_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SERVER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SERVER_PASSWORD = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private OrgEmailConfigRepository orgEmailConfigRepository;


    @Autowired
    private OrgEmailConfigMapper orgEmailConfigMapper;
    

    @Autowired
    private OrgEmailConfigService orgEmailConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrgEmailConfigMockMvc;

    private OrgEmailConfig orgEmailConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrgEmailConfigResource orgEmailConfigResource = new OrgEmailConfigResource(orgEmailConfigService);
        this.restOrgEmailConfigMockMvc = MockMvcBuilders.standaloneSetup(orgEmailConfigResource)
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
    public static OrgEmailConfig createEntity(EntityManager em) {
        OrgEmailConfig orgEmailConfig = new OrgEmailConfig()
            .emailServerHost(DEFAULT_EMAIL_SERVER_HOST)
            .emailServerPort(DEFAULT_EMAIL_SERVER_PORT)
            .emailServerUserId(DEFAULT_EMAIL_SERVER_USER_ID)
            .emailServerPassword(DEFAULT_EMAIL_SERVER_PASSWORD)
            .createDate(DEFAULT_CREATE_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return orgEmailConfig;
    }

    @Before
    public void initTest() {
        orgEmailConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrgEmailConfig() throws Exception {
        int databaseSizeBeforeCreate = orgEmailConfigRepository.findAll().size();

        // Create the OrgEmailConfig
        OrgEmailConfigDTO orgEmailConfigDTO = orgEmailConfigMapper.toDto(orgEmailConfig);
        restOrgEmailConfigMockMvc.perform(post("/api/org-email-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgEmailConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the OrgEmailConfig in the database
        List<OrgEmailConfig> orgEmailConfigList = orgEmailConfigRepository.findAll();
        assertThat(orgEmailConfigList).hasSize(databaseSizeBeforeCreate + 1);
        OrgEmailConfig testOrgEmailConfig = orgEmailConfigList.get(orgEmailConfigList.size() - 1);
        assertThat(testOrgEmailConfig.getEmailServerHost()).isEqualTo(DEFAULT_EMAIL_SERVER_HOST);
        assertThat(testOrgEmailConfig.getEmailServerPort()).isEqualTo(DEFAULT_EMAIL_SERVER_PORT);
        assertThat(testOrgEmailConfig.getEmailServerUserId()).isEqualTo(DEFAULT_EMAIL_SERVER_USER_ID);
        assertThat(testOrgEmailConfig.getEmailServerPassword()).isEqualTo(DEFAULT_EMAIL_SERVER_PASSWORD);
        assertThat(testOrgEmailConfig.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testOrgEmailConfig.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrgEmailConfig.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testOrgEmailConfig.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createOrgEmailConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orgEmailConfigRepository.findAll().size();

        // Create the OrgEmailConfig with an existing ID
        orgEmailConfig.setId(1L);
        OrgEmailConfigDTO orgEmailConfigDTO = orgEmailConfigMapper.toDto(orgEmailConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgEmailConfigMockMvc.perform(post("/api/org-email-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgEmailConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgEmailConfig in the database
        List<OrgEmailConfig> orgEmailConfigList = orgEmailConfigRepository.findAll();
        assertThat(orgEmailConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrgEmailConfigs() throws Exception {
        // Initialize the database
        orgEmailConfigRepository.saveAndFlush(orgEmailConfig);

        // Get all the orgEmailConfigList
        restOrgEmailConfigMockMvc.perform(get("/api/org-email-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgEmailConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailServerHost").value(hasItem(DEFAULT_EMAIL_SERVER_HOST.toString())))
            .andExpect(jsonPath("$.[*].emailServerPort").value(hasItem(DEFAULT_EMAIL_SERVER_PORT)))
            .andExpect(jsonPath("$.[*].emailServerUserId").value(hasItem(DEFAULT_EMAIL_SERVER_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].emailServerPassword").value(hasItem(DEFAULT_EMAIL_SERVER_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }
    

    @Test
    @Transactional
    public void getOrgEmailConfig() throws Exception {
        // Initialize the database
        orgEmailConfigRepository.saveAndFlush(orgEmailConfig);

        // Get the orgEmailConfig
        restOrgEmailConfigMockMvc.perform(get("/api/org-email-configs/{id}", orgEmailConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orgEmailConfig.getId().intValue()))
            .andExpect(jsonPath("$.emailServerHost").value(DEFAULT_EMAIL_SERVER_HOST.toString()))
            .andExpect(jsonPath("$.emailServerPort").value(DEFAULT_EMAIL_SERVER_PORT))
            .andExpect(jsonPath("$.emailServerUserId").value(DEFAULT_EMAIL_SERVER_USER_ID.toString()))
            .andExpect(jsonPath("$.emailServerPassword").value(DEFAULT_EMAIL_SERVER_PASSWORD.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOrgEmailConfig() throws Exception {
        // Get the orgEmailConfig
        restOrgEmailConfigMockMvc.perform(get("/api/org-email-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrgEmailConfig() throws Exception {
        // Initialize the database
        orgEmailConfigRepository.saveAndFlush(orgEmailConfig);

        int databaseSizeBeforeUpdate = orgEmailConfigRepository.findAll().size();

        // Update the orgEmailConfig
        OrgEmailConfig updatedOrgEmailConfig = orgEmailConfigRepository.findById(orgEmailConfig.getId()).get();
        // Disconnect from session so that the updates on updatedOrgEmailConfig are not directly saved in db
        em.detach(updatedOrgEmailConfig);
        updatedOrgEmailConfig
            .emailServerHost(UPDATED_EMAIL_SERVER_HOST)
            .emailServerPort(UPDATED_EMAIL_SERVER_PORT)
            .emailServerUserId(UPDATED_EMAIL_SERVER_USER_ID)
            .emailServerPassword(UPDATED_EMAIL_SERVER_PASSWORD)
            .createDate(UPDATED_CREATE_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        OrgEmailConfigDTO orgEmailConfigDTO = orgEmailConfigMapper.toDto(updatedOrgEmailConfig);

        restOrgEmailConfigMockMvc.perform(put("/api/org-email-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgEmailConfigDTO)))
            .andExpect(status().isOk());

        // Validate the OrgEmailConfig in the database
        List<OrgEmailConfig> orgEmailConfigList = orgEmailConfigRepository.findAll();
        assertThat(orgEmailConfigList).hasSize(databaseSizeBeforeUpdate);
        OrgEmailConfig testOrgEmailConfig = orgEmailConfigList.get(orgEmailConfigList.size() - 1);
        assertThat(testOrgEmailConfig.getEmailServerHost()).isEqualTo(UPDATED_EMAIL_SERVER_HOST);
        assertThat(testOrgEmailConfig.getEmailServerPort()).isEqualTo(UPDATED_EMAIL_SERVER_PORT);
        assertThat(testOrgEmailConfig.getEmailServerUserId()).isEqualTo(UPDATED_EMAIL_SERVER_USER_ID);
        assertThat(testOrgEmailConfig.getEmailServerPassword()).isEqualTo(UPDATED_EMAIL_SERVER_PASSWORD);
        assertThat(testOrgEmailConfig.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testOrgEmailConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrgEmailConfig.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testOrgEmailConfig.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrgEmailConfig() throws Exception {
        int databaseSizeBeforeUpdate = orgEmailConfigRepository.findAll().size();

        // Create the OrgEmailConfig
        OrgEmailConfigDTO orgEmailConfigDTO = orgEmailConfigMapper.toDto(orgEmailConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restOrgEmailConfigMockMvc.perform(put("/api/org-email-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgEmailConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgEmailConfig in the database
        List<OrgEmailConfig> orgEmailConfigList = orgEmailConfigRepository.findAll();
        assertThat(orgEmailConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrgEmailConfig() throws Exception {
        // Initialize the database
        orgEmailConfigRepository.saveAndFlush(orgEmailConfig);

        int databaseSizeBeforeDelete = orgEmailConfigRepository.findAll().size();

        // Get the orgEmailConfig
        restOrgEmailConfigMockMvc.perform(delete("/api/org-email-configs/{id}", orgEmailConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrgEmailConfig> orgEmailConfigList = orgEmailConfigRepository.findAll();
        assertThat(orgEmailConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgEmailConfig.class);
        OrgEmailConfig orgEmailConfig1 = new OrgEmailConfig();
        orgEmailConfig1.setId(1L);
        OrgEmailConfig orgEmailConfig2 = new OrgEmailConfig();
        orgEmailConfig2.setId(orgEmailConfig1.getId());
        assertThat(orgEmailConfig1).isEqualTo(orgEmailConfig2);
        orgEmailConfig2.setId(2L);
        assertThat(orgEmailConfig1).isNotEqualTo(orgEmailConfig2);
        orgEmailConfig1.setId(null);
        assertThat(orgEmailConfig1).isNotEqualTo(orgEmailConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgEmailConfigDTO.class);
        OrgEmailConfigDTO orgEmailConfigDTO1 = new OrgEmailConfigDTO();
        orgEmailConfigDTO1.setId(1L);
        OrgEmailConfigDTO orgEmailConfigDTO2 = new OrgEmailConfigDTO();
        assertThat(orgEmailConfigDTO1).isNotEqualTo(orgEmailConfigDTO2);
        orgEmailConfigDTO2.setId(orgEmailConfigDTO1.getId());
        assertThat(orgEmailConfigDTO1).isEqualTo(orgEmailConfigDTO2);
        orgEmailConfigDTO2.setId(2L);
        assertThat(orgEmailConfigDTO1).isNotEqualTo(orgEmailConfigDTO2);
        orgEmailConfigDTO1.setId(null);
        assertThat(orgEmailConfigDTO1).isNotEqualTo(orgEmailConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orgEmailConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orgEmailConfigMapper.fromId(null)).isNull();
    }
}

package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.InputTemplate;
import com.edatablock.rpa.repository.InputTemplateRepository;
import com.edatablock.rpa.service.InputTemplateService;
import com.edatablock.rpa.service.dto.InputTemplateDTO;
import com.edatablock.rpa.service.mapper.InputTemplateMapper;
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
 * Test class for the InputTemplateResource REST controller.
 *
 * @see InputTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class InputTemplateResourceIntTest {

    private static final String DEFAULT_TEMPLATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private InputTemplateRepository inputTemplateRepository;


    @Autowired
    private InputTemplateMapper inputTemplateMapper;
    

    @Autowired
    private InputTemplateService inputTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInputTemplateMockMvc;

    private InputTemplate inputTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InputTemplateResource inputTemplateResource = new InputTemplateResource(inputTemplateService);
        this.restInputTemplateMockMvc = MockMvcBuilders.standaloneSetup(inputTemplateResource)
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
    public static InputTemplate createEntity(EntityManager em) {
        InputTemplate inputTemplate = new InputTemplate()
            .templateName(DEFAULT_TEMPLATE_NAME)
            .templateDescription(DEFAULT_TEMPLATE_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE)
            .createDate(DEFAULT_CREATE_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return inputTemplate;
    }

    @Before
    public void initTest() {
        inputTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createInputTemplate() throws Exception {
        int databaseSizeBeforeCreate = inputTemplateRepository.findAll().size();

        // Create the InputTemplate
        InputTemplateDTO inputTemplateDTO = inputTemplateMapper.toDto(inputTemplate);
        restInputTemplateMockMvc.perform(post("/api/input-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the InputTemplate in the database
        List<InputTemplate> inputTemplateList = inputTemplateRepository.findAll();
        assertThat(inputTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        InputTemplate testInputTemplate = inputTemplateList.get(inputTemplateList.size() - 1);
        assertThat(testInputTemplate.getTemplateName()).isEqualTo(DEFAULT_TEMPLATE_NAME);
        assertThat(testInputTemplate.getTemplateDescription()).isEqualTo(DEFAULT_TEMPLATE_DESCRIPTION);
        assertThat(testInputTemplate.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testInputTemplate.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testInputTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInputTemplate.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testInputTemplate.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createInputTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inputTemplateRepository.findAll().size();

        // Create the InputTemplate with an existing ID
        inputTemplate.setId(1L);
        InputTemplateDTO inputTemplateDTO = inputTemplateMapper.toDto(inputTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInputTemplateMockMvc.perform(post("/api/input-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InputTemplate in the database
        List<InputTemplate> inputTemplateList = inputTemplateRepository.findAll();
        assertThat(inputTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTemplateNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = inputTemplateRepository.findAll().size();
        // set the field null
        inputTemplate.setTemplateName(null);

        // Create the InputTemplate, which fails.
        InputTemplateDTO inputTemplateDTO = inputTemplateMapper.toDto(inputTemplate);

        restInputTemplateMockMvc.perform(post("/api/input-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<InputTemplate> inputTemplateList = inputTemplateRepository.findAll();
        assertThat(inputTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInputTemplates() throws Exception {
        // Initialize the database
        inputTemplateRepository.saveAndFlush(inputTemplate);

        // Get all the inputTemplateList
        restInputTemplateMockMvc.perform(get("/api/input-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inputTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateName").value(hasItem(DEFAULT_TEMPLATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].templateDescription").value(hasItem(DEFAULT_TEMPLATE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }
    

    @Test
    @Transactional
    public void getInputTemplate() throws Exception {
        // Initialize the database
        inputTemplateRepository.saveAndFlush(inputTemplate);

        // Get the inputTemplate
        restInputTemplateMockMvc.perform(get("/api/input-templates/{id}", inputTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inputTemplate.getId().intValue()))
            .andExpect(jsonPath("$.templateName").value(DEFAULT_TEMPLATE_NAME.toString()))
            .andExpect(jsonPath("$.templateDescription").value(DEFAULT_TEMPLATE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInputTemplate() throws Exception {
        // Get the inputTemplate
        restInputTemplateMockMvc.perform(get("/api/input-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInputTemplate() throws Exception {
        // Initialize the database
        inputTemplateRepository.saveAndFlush(inputTemplate);

        int databaseSizeBeforeUpdate = inputTemplateRepository.findAll().size();

        // Update the inputTemplate
        InputTemplate updatedInputTemplate = inputTemplateRepository.findById(inputTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedInputTemplate are not directly saved in db
        em.detach(updatedInputTemplate);
        updatedInputTemplate
            .templateName(UPDATED_TEMPLATE_NAME)
            .templateDescription(UPDATED_TEMPLATE_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createDate(UPDATED_CREATE_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        InputTemplateDTO inputTemplateDTO = inputTemplateMapper.toDto(updatedInputTemplate);

        restInputTemplateMockMvc.perform(put("/api/input-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the InputTemplate in the database
        List<InputTemplate> inputTemplateList = inputTemplateRepository.findAll();
        assertThat(inputTemplateList).hasSize(databaseSizeBeforeUpdate);
        InputTemplate testInputTemplate = inputTemplateList.get(inputTemplateList.size() - 1);
        assertThat(testInputTemplate.getTemplateName()).isEqualTo(UPDATED_TEMPLATE_NAME);
        assertThat(testInputTemplate.getTemplateDescription()).isEqualTo(UPDATED_TEMPLATE_DESCRIPTION);
        assertThat(testInputTemplate.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testInputTemplate.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testInputTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInputTemplate.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testInputTemplate.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingInputTemplate() throws Exception {
        int databaseSizeBeforeUpdate = inputTemplateRepository.findAll().size();

        // Create the InputTemplate
        InputTemplateDTO inputTemplateDTO = inputTemplateMapper.toDto(inputTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restInputTemplateMockMvc.perform(put("/api/input-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InputTemplate in the database
        List<InputTemplate> inputTemplateList = inputTemplateRepository.findAll();
        assertThat(inputTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInputTemplate() throws Exception {
        // Initialize the database
        inputTemplateRepository.saveAndFlush(inputTemplate);

        int databaseSizeBeforeDelete = inputTemplateRepository.findAll().size();

        // Get the inputTemplate
        restInputTemplateMockMvc.perform(delete("/api/input-templates/{id}", inputTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InputTemplate> inputTemplateList = inputTemplateRepository.findAll();
        assertThat(inputTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputTemplate.class);
        InputTemplate inputTemplate1 = new InputTemplate();
        inputTemplate1.setId(1L);
        InputTemplate inputTemplate2 = new InputTemplate();
        inputTemplate2.setId(inputTemplate1.getId());
        assertThat(inputTemplate1).isEqualTo(inputTemplate2);
        inputTemplate2.setId(2L);
        assertThat(inputTemplate1).isNotEqualTo(inputTemplate2);
        inputTemplate1.setId(null);
        assertThat(inputTemplate1).isNotEqualTo(inputTemplate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputTemplateDTO.class);
        InputTemplateDTO inputTemplateDTO1 = new InputTemplateDTO();
        inputTemplateDTO1.setId(1L);
        InputTemplateDTO inputTemplateDTO2 = new InputTemplateDTO();
        assertThat(inputTemplateDTO1).isNotEqualTo(inputTemplateDTO2);
        inputTemplateDTO2.setId(inputTemplateDTO1.getId());
        assertThat(inputTemplateDTO1).isEqualTo(inputTemplateDTO2);
        inputTemplateDTO2.setId(2L);
        assertThat(inputTemplateDTO1).isNotEqualTo(inputTemplateDTO2);
        inputTemplateDTO1.setId(null);
        assertThat(inputTemplateDTO1).isNotEqualTo(inputTemplateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inputTemplateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inputTemplateMapper.fromId(null)).isNull();
    }
}

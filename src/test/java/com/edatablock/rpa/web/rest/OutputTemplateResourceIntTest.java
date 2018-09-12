package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.OutputTemplate;
import com.edatablock.rpa.repository.OutputTemplateRepository;
import com.edatablock.rpa.service.OutputTemplateService;
import com.edatablock.rpa.service.dto.OutputTemplateDTO;
import com.edatablock.rpa.service.mapper.OutputTemplateMapper;
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
 * Test class for the OutputTemplateResource REST controller.
 *
 * @see OutputTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class OutputTemplateResourceIntTest {

    private static final String DEFAULT_OUTPUT_TEMPLATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT_TEMPLATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIELD_VALIDATION_REQUIRE = 1;
    private static final Integer UPDATED_FIELD_VALIDATION_REQUIRE = 2;

    private static final String DEFAULT_FIELD_VALIDATION_RULE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_VALIDATION_RULE = "BBBBBBBBBB";

    @Autowired
    private OutputTemplateRepository outputTemplateRepository;


    @Autowired
    private OutputTemplateMapper outputTemplateMapper;
    

    @Autowired
    private OutputTemplateService outputTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOutputTemplateMockMvc;

    private OutputTemplate outputTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OutputTemplateResource outputTemplateResource = new OutputTemplateResource(outputTemplateService);
        this.restOutputTemplateMockMvc = MockMvcBuilders.standaloneSetup(outputTemplateResource)
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
    public static OutputTemplate createEntity(EntityManager em) {
        OutputTemplate outputTemplate = new OutputTemplate()
            .outputTemplateName(DEFAULT_OUTPUT_TEMPLATE_NAME)
            .fieldName(DEFAULT_FIELD_NAME)
            .position(DEFAULT_POSITION)
            .fieldValidationRequire(DEFAULT_FIELD_VALIDATION_REQUIRE)
            .fieldValidationRule(DEFAULT_FIELD_VALIDATION_RULE);
        return outputTemplate;
    }

    @Before
    public void initTest() {
        outputTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutputTemplate() throws Exception {
        int databaseSizeBeforeCreate = outputTemplateRepository.findAll().size();

        // Create the OutputTemplate
        OutputTemplateDTO outputTemplateDTO = outputTemplateMapper.toDto(outputTemplate);
        restOutputTemplateMockMvc.perform(post("/api/output-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outputTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the OutputTemplate in the database
        List<OutputTemplate> outputTemplateList = outputTemplateRepository.findAll();
        assertThat(outputTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        OutputTemplate testOutputTemplate = outputTemplateList.get(outputTemplateList.size() - 1);
        assertThat(testOutputTemplate.getOutputTemplateName()).isEqualTo(DEFAULT_OUTPUT_TEMPLATE_NAME);
        assertThat(testOutputTemplate.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testOutputTemplate.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testOutputTemplate.getFieldValidationRequire()).isEqualTo(DEFAULT_FIELD_VALIDATION_REQUIRE);
        assertThat(testOutputTemplate.getFieldValidationRule()).isEqualTo(DEFAULT_FIELD_VALIDATION_RULE);
    }

    @Test
    @Transactional
    public void createOutputTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outputTemplateRepository.findAll().size();

        // Create the OutputTemplate with an existing ID
        outputTemplate.setId(1L);
        OutputTemplateDTO outputTemplateDTO = outputTemplateMapper.toDto(outputTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutputTemplateMockMvc.perform(post("/api/output-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outputTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OutputTemplate in the database
        List<OutputTemplate> outputTemplateList = outputTemplateRepository.findAll();
        assertThat(outputTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = outputTemplateRepository.findAll().size();
        // set the field null
        outputTemplate.setFieldName(null);

        // Create the OutputTemplate, which fails.
        OutputTemplateDTO outputTemplateDTO = outputTemplateMapper.toDto(outputTemplate);

        restOutputTemplateMockMvc.perform(post("/api/output-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outputTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<OutputTemplate> outputTemplateList = outputTemplateRepository.findAll();
        assertThat(outputTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOutputTemplates() throws Exception {
        // Initialize the database
        outputTemplateRepository.saveAndFlush(outputTemplate);

        // Get all the outputTemplateList
        restOutputTemplateMockMvc.perform(get("/api/output-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outputTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].outputTemplateName").value(hasItem(DEFAULT_OUTPUT_TEMPLATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].fieldValidationRequire").value(hasItem(DEFAULT_FIELD_VALIDATION_REQUIRE)))
            .andExpect(jsonPath("$.[*].fieldValidationRule").value(hasItem(DEFAULT_FIELD_VALIDATION_RULE.toString())));
    }
    

    @Test
    @Transactional
    public void getOutputTemplate() throws Exception {
        // Initialize the database
        outputTemplateRepository.saveAndFlush(outputTemplate);

        // Get the outputTemplate
        restOutputTemplateMockMvc.perform(get("/api/output-templates/{id}", outputTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(outputTemplate.getId().intValue()))
            .andExpect(jsonPath("$.outputTemplateName").value(DEFAULT_OUTPUT_TEMPLATE_NAME.toString()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.fieldValidationRequire").value(DEFAULT_FIELD_VALIDATION_REQUIRE))
            .andExpect(jsonPath("$.fieldValidationRule").value(DEFAULT_FIELD_VALIDATION_RULE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOutputTemplate() throws Exception {
        // Get the outputTemplate
        restOutputTemplateMockMvc.perform(get("/api/output-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutputTemplate() throws Exception {
        // Initialize the database
        outputTemplateRepository.saveAndFlush(outputTemplate);

        int databaseSizeBeforeUpdate = outputTemplateRepository.findAll().size();

        // Update the outputTemplate
        OutputTemplate updatedOutputTemplate = outputTemplateRepository.findById(outputTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedOutputTemplate are not directly saved in db
        em.detach(updatedOutputTemplate);
        updatedOutputTemplate
            .outputTemplateName(UPDATED_OUTPUT_TEMPLATE_NAME)
            .fieldName(UPDATED_FIELD_NAME)
            .position(UPDATED_POSITION)
            .fieldValidationRequire(UPDATED_FIELD_VALIDATION_REQUIRE)
            .fieldValidationRule(UPDATED_FIELD_VALIDATION_RULE);
        OutputTemplateDTO outputTemplateDTO = outputTemplateMapper.toDto(updatedOutputTemplate);

        restOutputTemplateMockMvc.perform(put("/api/output-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outputTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the OutputTemplate in the database
        List<OutputTemplate> outputTemplateList = outputTemplateRepository.findAll();
        assertThat(outputTemplateList).hasSize(databaseSizeBeforeUpdate);
        OutputTemplate testOutputTemplate = outputTemplateList.get(outputTemplateList.size() - 1);
        assertThat(testOutputTemplate.getOutputTemplateName()).isEqualTo(UPDATED_OUTPUT_TEMPLATE_NAME);
        assertThat(testOutputTemplate.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testOutputTemplate.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testOutputTemplate.getFieldValidationRequire()).isEqualTo(UPDATED_FIELD_VALIDATION_REQUIRE);
        assertThat(testOutputTemplate.getFieldValidationRule()).isEqualTo(UPDATED_FIELD_VALIDATION_RULE);
    }

    @Test
    @Transactional
    public void updateNonExistingOutputTemplate() throws Exception {
        int databaseSizeBeforeUpdate = outputTemplateRepository.findAll().size();

        // Create the OutputTemplate
        OutputTemplateDTO outputTemplateDTO = outputTemplateMapper.toDto(outputTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restOutputTemplateMockMvc.perform(put("/api/output-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outputTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OutputTemplate in the database
        List<OutputTemplate> outputTemplateList = outputTemplateRepository.findAll();
        assertThat(outputTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutputTemplate() throws Exception {
        // Initialize the database
        outputTemplateRepository.saveAndFlush(outputTemplate);

        int databaseSizeBeforeDelete = outputTemplateRepository.findAll().size();

        // Get the outputTemplate
        restOutputTemplateMockMvc.perform(delete("/api/output-templates/{id}", outputTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OutputTemplate> outputTemplateList = outputTemplateRepository.findAll();
        assertThat(outputTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OutputTemplate.class);
        OutputTemplate outputTemplate1 = new OutputTemplate();
        outputTemplate1.setId(1L);
        OutputTemplate outputTemplate2 = new OutputTemplate();
        outputTemplate2.setId(outputTemplate1.getId());
        assertThat(outputTemplate1).isEqualTo(outputTemplate2);
        outputTemplate2.setId(2L);
        assertThat(outputTemplate1).isNotEqualTo(outputTemplate2);
        outputTemplate1.setId(null);
        assertThat(outputTemplate1).isNotEqualTo(outputTemplate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OutputTemplateDTO.class);
        OutputTemplateDTO outputTemplateDTO1 = new OutputTemplateDTO();
        outputTemplateDTO1.setId(1L);
        OutputTemplateDTO outputTemplateDTO2 = new OutputTemplateDTO();
        assertThat(outputTemplateDTO1).isNotEqualTo(outputTemplateDTO2);
        outputTemplateDTO2.setId(outputTemplateDTO1.getId());
        assertThat(outputTemplateDTO1).isEqualTo(outputTemplateDTO2);
        outputTemplateDTO2.setId(2L);
        assertThat(outputTemplateDTO1).isNotEqualTo(outputTemplateDTO2);
        outputTemplateDTO1.setId(null);
        assertThat(outputTemplateDTO1).isNotEqualTo(outputTemplateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(outputTemplateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(outputTemplateMapper.fromId(null)).isNull();
    }
}

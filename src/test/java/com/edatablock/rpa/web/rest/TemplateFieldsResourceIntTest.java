package com.edatablock.rpa.web.rest;

import com.edatablock.rpa.RpaprojectApp;

import com.edatablock.rpa.domain.TemplateFields;
import com.edatablock.rpa.repository.TemplateFieldsRepository;
import com.edatablock.rpa.service.TemplateFieldsService;
import com.edatablock.rpa.service.dto.TemplateFieldsDTO;
import com.edatablock.rpa.service.mapper.TemplateFieldsMapper;
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
 * Test class for the TemplateFieldsResource REST controller.
 *
 * @see TemplateFieldsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpaprojectApp.class)
public class TemplateFieldsResourceIntTest {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_FIELD_ZONE_MIN_X = 1D;
    private static final Double UPDATED_FIELD_ZONE_MIN_X = 2D;

    private static final Double DEFAULT_FIELD_ZONE_MIN_Y = 1D;
    private static final Double UPDATED_FIELD_ZONE_MIN_Y = 2D;

    private static final Double DEFAULT_FIELD_ZONE_MAX_X = 1D;
    private static final Double UPDATED_FIELD_ZONE_MAX_X = 2D;

    private static final Double DEFAULT_FIELD_ZONE_MAX_Y = 1D;
    private static final Double UPDATED_FIELD_ZONE_MAX_Y = 2D;

    private static final Integer DEFAULT_FIELD_VALIDATION_REQUIRE = 1;
    private static final Integer UPDATED_FIELD_VALIDATION_REQUIRE = 2;

    private static final String DEFAULT_FIELD_VALIDATION_RULE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_VALIDATION_RULE = "BBBBBBBBBB";

    @Autowired
    private TemplateFieldsRepository templateFieldsRepository;


    @Autowired
    private TemplateFieldsMapper templateFieldsMapper;
    

    @Autowired
    private TemplateFieldsService templateFieldsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemplateFieldsMockMvc;

    private TemplateFields templateFields;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemplateFieldsResource templateFieldsResource = new TemplateFieldsResource(templateFieldsService);
        this.restTemplateFieldsMockMvc = MockMvcBuilders.standaloneSetup(templateFieldsResource)
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
    public static TemplateFields createEntity(EntityManager em) {
        TemplateFields templateFields = new TemplateFields()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldZoneMinX(DEFAULT_FIELD_ZONE_MIN_X)
            .fieldZoneMinY(DEFAULT_FIELD_ZONE_MIN_Y)
            .fieldZoneMaxX(DEFAULT_FIELD_ZONE_MAX_X)
            .fieldZoneMaxY(DEFAULT_FIELD_ZONE_MAX_Y)
            .fieldValidationRequire(DEFAULT_FIELD_VALIDATION_REQUIRE)
            .fieldValidationRule(DEFAULT_FIELD_VALIDATION_RULE);
        return templateFields;
    }

    @Before
    public void initTest() {
        templateFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplateFields() throws Exception {
        int databaseSizeBeforeCreate = templateFieldsRepository.findAll().size();

        // Create the TemplateFields
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);
        restTemplateFieldsMockMvc.perform(post("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the TemplateFields in the database
        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateFields testTemplateFields = templateFieldsList.get(templateFieldsList.size() - 1);
        assertThat(testTemplateFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testTemplateFields.getFieldZoneMinX()).isEqualTo(DEFAULT_FIELD_ZONE_MIN_X);
        assertThat(testTemplateFields.getFieldZoneMinY()).isEqualTo(DEFAULT_FIELD_ZONE_MIN_Y);
        assertThat(testTemplateFields.getFieldZoneMaxX()).isEqualTo(DEFAULT_FIELD_ZONE_MAX_X);
        assertThat(testTemplateFields.getFieldZoneMaxY()).isEqualTo(DEFAULT_FIELD_ZONE_MAX_Y);
        assertThat(testTemplateFields.getFieldValidationRequire()).isEqualTo(DEFAULT_FIELD_VALIDATION_REQUIRE);
        assertThat(testTemplateFields.getFieldValidationRule()).isEqualTo(DEFAULT_FIELD_VALIDATION_RULE);
    }

    @Test
    @Transactional
    public void createTemplateFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateFieldsRepository.findAll().size();

        // Create the TemplateFields with an existing ID
        templateFields.setId(1L);
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateFieldsMockMvc.perform(post("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateFields in the database
        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFieldsRepository.findAll().size();
        // set the field null
        templateFields.setFieldName(null);

        // Create the TemplateFields, which fails.
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);

        restTemplateFieldsMockMvc.perform(post("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldZoneMinXIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFieldsRepository.findAll().size();
        // set the field null
        templateFields.setFieldZoneMinX(null);

        // Create the TemplateFields, which fails.
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);

        restTemplateFieldsMockMvc.perform(post("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldZoneMinYIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFieldsRepository.findAll().size();
        // set the field null
        templateFields.setFieldZoneMinY(null);

        // Create the TemplateFields, which fails.
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);

        restTemplateFieldsMockMvc.perform(post("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldZoneMaxXIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFieldsRepository.findAll().size();
        // set the field null
        templateFields.setFieldZoneMaxX(null);

        // Create the TemplateFields, which fails.
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);

        restTemplateFieldsMockMvc.perform(post("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldZoneMaxYIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFieldsRepository.findAll().size();
        // set the field null
        templateFields.setFieldZoneMaxY(null);

        // Create the TemplateFields, which fails.
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);

        restTemplateFieldsMockMvc.perform(post("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isBadRequest());

        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemplateFields() throws Exception {
        // Initialize the database
        templateFieldsRepository.saveAndFlush(templateFields);

        // Get all the templateFieldsList
        restTemplateFieldsMockMvc.perform(get("/api/template-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldZoneMinX").value(hasItem(DEFAULT_FIELD_ZONE_MIN_X.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldZoneMinY").value(hasItem(DEFAULT_FIELD_ZONE_MIN_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldZoneMaxX").value(hasItem(DEFAULT_FIELD_ZONE_MAX_X.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldZoneMaxY").value(hasItem(DEFAULT_FIELD_ZONE_MAX_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldValidationRequire").value(hasItem(DEFAULT_FIELD_VALIDATION_REQUIRE)))
            .andExpect(jsonPath("$.[*].fieldValidationRule").value(hasItem(DEFAULT_FIELD_VALIDATION_RULE.toString())));
    }
    

    @Test
    @Transactional
    public void getTemplateFields() throws Exception {
        // Initialize the database
        templateFieldsRepository.saveAndFlush(templateFields);

        // Get the templateFields
        restTemplateFieldsMockMvc.perform(get("/api/template-fields/{id}", templateFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(templateFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.fieldZoneMinX").value(DEFAULT_FIELD_ZONE_MIN_X.doubleValue()))
            .andExpect(jsonPath("$.fieldZoneMinY").value(DEFAULT_FIELD_ZONE_MIN_Y.doubleValue()))
            .andExpect(jsonPath("$.fieldZoneMaxX").value(DEFAULT_FIELD_ZONE_MAX_X.doubleValue()))
            .andExpect(jsonPath("$.fieldZoneMaxY").value(DEFAULT_FIELD_ZONE_MAX_Y.doubleValue()))
            .andExpect(jsonPath("$.fieldValidationRequire").value(DEFAULT_FIELD_VALIDATION_REQUIRE))
            .andExpect(jsonPath("$.fieldValidationRule").value(DEFAULT_FIELD_VALIDATION_RULE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTemplateFields() throws Exception {
        // Get the templateFields
        restTemplateFieldsMockMvc.perform(get("/api/template-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplateFields() throws Exception {
        // Initialize the database
        templateFieldsRepository.saveAndFlush(templateFields);

        int databaseSizeBeforeUpdate = templateFieldsRepository.findAll().size();

        // Update the templateFields
        TemplateFields updatedTemplateFields = templateFieldsRepository.findById(templateFields.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateFields are not directly saved in db
        em.detach(updatedTemplateFields);
        updatedTemplateFields
            .fieldName(UPDATED_FIELD_NAME)
            .fieldZoneMinX(UPDATED_FIELD_ZONE_MIN_X)
            .fieldZoneMinY(UPDATED_FIELD_ZONE_MIN_Y)
            .fieldZoneMaxX(UPDATED_FIELD_ZONE_MAX_X)
            .fieldZoneMaxY(UPDATED_FIELD_ZONE_MAX_Y)
            .fieldValidationRequire(UPDATED_FIELD_VALIDATION_REQUIRE)
            .fieldValidationRule(UPDATED_FIELD_VALIDATION_RULE);
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(updatedTemplateFields);

        restTemplateFieldsMockMvc.perform(put("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isOk());

        // Validate the TemplateFields in the database
        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeUpdate);
        TemplateFields testTemplateFields = templateFieldsList.get(templateFieldsList.size() - 1);
        assertThat(testTemplateFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testTemplateFields.getFieldZoneMinX()).isEqualTo(UPDATED_FIELD_ZONE_MIN_X);
        assertThat(testTemplateFields.getFieldZoneMinY()).isEqualTo(UPDATED_FIELD_ZONE_MIN_Y);
        assertThat(testTemplateFields.getFieldZoneMaxX()).isEqualTo(UPDATED_FIELD_ZONE_MAX_X);
        assertThat(testTemplateFields.getFieldZoneMaxY()).isEqualTo(UPDATED_FIELD_ZONE_MAX_Y);
        assertThat(testTemplateFields.getFieldValidationRequire()).isEqualTo(UPDATED_FIELD_VALIDATION_REQUIRE);
        assertThat(testTemplateFields.getFieldValidationRule()).isEqualTo(UPDATED_FIELD_VALIDATION_RULE);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplateFields() throws Exception {
        int databaseSizeBeforeUpdate = templateFieldsRepository.findAll().size();

        // Create the TemplateFields
        TemplateFieldsDTO templateFieldsDTO = templateFieldsMapper.toDto(templateFields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restTemplateFieldsMockMvc.perform(put("/api/template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateFieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateFields in the database
        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemplateFields() throws Exception {
        // Initialize the database
        templateFieldsRepository.saveAndFlush(templateFields);

        int databaseSizeBeforeDelete = templateFieldsRepository.findAll().size();

        // Get the templateFields
        restTemplateFieldsMockMvc.perform(delete("/api/template-fields/{id}", templateFields.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TemplateFields> templateFieldsList = templateFieldsRepository.findAll();
        assertThat(templateFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFields.class);
        TemplateFields templateFields1 = new TemplateFields();
        templateFields1.setId(1L);
        TemplateFields templateFields2 = new TemplateFields();
        templateFields2.setId(templateFields1.getId());
        assertThat(templateFields1).isEqualTo(templateFields2);
        templateFields2.setId(2L);
        assertThat(templateFields1).isNotEqualTo(templateFields2);
        templateFields1.setId(null);
        assertThat(templateFields1).isNotEqualTo(templateFields2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFieldsDTO.class);
        TemplateFieldsDTO templateFieldsDTO1 = new TemplateFieldsDTO();
        templateFieldsDTO1.setId(1L);
        TemplateFieldsDTO templateFieldsDTO2 = new TemplateFieldsDTO();
        assertThat(templateFieldsDTO1).isNotEqualTo(templateFieldsDTO2);
        templateFieldsDTO2.setId(templateFieldsDTO1.getId());
        assertThat(templateFieldsDTO1).isEqualTo(templateFieldsDTO2);
        templateFieldsDTO2.setId(2L);
        assertThat(templateFieldsDTO1).isNotEqualTo(templateFieldsDTO2);
        templateFieldsDTO1.setId(null);
        assertThat(templateFieldsDTO1).isNotEqualTo(templateFieldsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(templateFieldsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(templateFieldsMapper.fromId(null)).isNull();
    }
}

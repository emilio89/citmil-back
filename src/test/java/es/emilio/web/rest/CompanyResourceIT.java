package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.Company;
import es.emilio.domain.Profesional;
import es.emilio.domain.CalendarYearProfesional;
import es.emilio.domain.Appointment;
import es.emilio.domain.TypeService;
import es.emilio.domain.PublicHoliday;
import es.emilio.domain.TimeBand;
import es.emilio.domain.DynamicContent;
import es.emilio.domain.MenuOptionsAvailable;
import es.emilio.domain.TimeBandAvailableProfesionalDay;
import es.emilio.repository.CompanyRepository;
import es.emilio.service.CompanyService;
import es.emilio.service.dto.CompanyDTO;
import es.emilio.service.mapper.CompanyMapper;
import es.emilio.service.dto.CompanyCriteria;
import es.emilio.service.CompanyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_COLOR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_URL_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_DAY_APPOINTMENT = 1;
    private static final Integer UPDATED_MAX_DAY_APPOINTMENT = 2;
    private static final Integer SMALLER_MAX_DAY_APPOINTMENT = 1 - 1;

    private static final Integer DEFAULT_MIN_DAY_APPOINTMENT = 1;
    private static final Integer UPDATED_MIN_DAY_APPOINTMENT = 2;
    private static final Integer SMALLER_MIN_DAY_APPOINTMENT = 1 - 1;

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;
    private static final Double SMALLER_LAT = 1D - 1D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;
    private static final Double SMALLER_LNG = 1D - 1D;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyQueryService companyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .primaryColor(DEFAULT_PRIMARY_COLOR)
            .secondaryColor(DEFAULT_SECONDARY_COLOR)
            .urlImg(DEFAULT_URL_IMG)
            .urlImgContentType(DEFAULT_URL_IMG_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .maxDayAppointment(DEFAULT_MAX_DAY_APPOINTMENT)
            .minDayAppointment(DEFAULT_MIN_DAY_APPOINTMENT)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG);
        return company;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .primaryColor(UPDATED_PRIMARY_COLOR)
            .secondaryColor(UPDATED_SECONDARY_COLOR)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .maxDayAppointment(UPDATED_MAX_DAY_APPOINTMENT)
            .minDayAppointment(UPDATED_MIN_DAY_APPOINTMENT)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompany.getPrimaryColor()).isEqualTo(DEFAULT_PRIMARY_COLOR);
        assertThat(testCompany.getSecondaryColor()).isEqualTo(DEFAULT_SECONDARY_COLOR);
        assertThat(testCompany.getUrlImg()).isEqualTo(DEFAULT_URL_IMG);
        assertThat(testCompany.getUrlImgContentType()).isEqualTo(DEFAULT_URL_IMG_CONTENT_TYPE);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCompany.getMaxDayAppointment()).isEqualTo(DEFAULT_MAX_DAY_APPOINTMENT);
        assertThat(testCompany.getMinDayAppointment()).isEqualTo(DEFAULT_MIN_DAY_APPOINTMENT);
        assertThat(testCompany.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testCompany.getLng()).isEqualTo(DEFAULT_LNG);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setName(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setDescription(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setEmail(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setPhone(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].primaryColor").value(hasItem(DEFAULT_PRIMARY_COLOR)))
            .andExpect(jsonPath("$.[*].secondaryColor").value(hasItem(DEFAULT_SECONDARY_COLOR)))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].maxDayAppointment").value(hasItem(DEFAULT_MAX_DAY_APPOINTMENT)))
            .andExpect(jsonPath("$.[*].minDayAppointment").value(hasItem(DEFAULT_MIN_DAY_APPOINTMENT)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.primaryColor").value(DEFAULT_PRIMARY_COLOR))
            .andExpect(jsonPath("$.secondaryColor").value(DEFAULT_SECONDARY_COLOR))
            .andExpect(jsonPath("$.urlImgContentType").value(DEFAULT_URL_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlImg").value(Base64Utils.encodeToString(DEFAULT_URL_IMG)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.maxDayAppointment").value(DEFAULT_MAX_DAY_APPOINTMENT))
            .andExpect(jsonPath("$.minDayAppointment").value(DEFAULT_MIN_DAY_APPOINTMENT))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()));
    }


    @Test
    @Transactional
    public void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompaniesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name equals to DEFAULT_NAME
        defaultCompanyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name not equals to DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyList where name not equals to UPDATED_NAME
        defaultCompanyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name is not null
        defaultCompanyShouldBeFound("name.specified=true");

        // Get all the companyList where name is null
        defaultCompanyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name contains DEFAULT_NAME
        defaultCompanyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyList where name contains UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name does not contain DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyList where name does not contain UPDATED_NAME
        defaultCompanyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description equals to DEFAULT_DESCRIPTION
        defaultCompanyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description equals to UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description not equals to DEFAULT_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description not equals to UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the companyList where description equals to UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description is not null
        defaultCompanyShouldBeFound("description.specified=true");

        // Get all the companyList where description is null
        defaultCompanyShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description contains DEFAULT_DESCRIPTION
        defaultCompanyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description contains UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description does not contain DEFAULT_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description does not contain UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCompaniesByPrimaryColorIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where primaryColor equals to DEFAULT_PRIMARY_COLOR
        defaultCompanyShouldBeFound("primaryColor.equals=" + DEFAULT_PRIMARY_COLOR);

        // Get all the companyList where primaryColor equals to UPDATED_PRIMARY_COLOR
        defaultCompanyShouldNotBeFound("primaryColor.equals=" + UPDATED_PRIMARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPrimaryColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where primaryColor not equals to DEFAULT_PRIMARY_COLOR
        defaultCompanyShouldNotBeFound("primaryColor.notEquals=" + DEFAULT_PRIMARY_COLOR);

        // Get all the companyList where primaryColor not equals to UPDATED_PRIMARY_COLOR
        defaultCompanyShouldBeFound("primaryColor.notEquals=" + UPDATED_PRIMARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPrimaryColorIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where primaryColor in DEFAULT_PRIMARY_COLOR or UPDATED_PRIMARY_COLOR
        defaultCompanyShouldBeFound("primaryColor.in=" + DEFAULT_PRIMARY_COLOR + "," + UPDATED_PRIMARY_COLOR);

        // Get all the companyList where primaryColor equals to UPDATED_PRIMARY_COLOR
        defaultCompanyShouldNotBeFound("primaryColor.in=" + UPDATED_PRIMARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPrimaryColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where primaryColor is not null
        defaultCompanyShouldBeFound("primaryColor.specified=true");

        // Get all the companyList where primaryColor is null
        defaultCompanyShouldNotBeFound("primaryColor.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByPrimaryColorContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where primaryColor contains DEFAULT_PRIMARY_COLOR
        defaultCompanyShouldBeFound("primaryColor.contains=" + DEFAULT_PRIMARY_COLOR);

        // Get all the companyList where primaryColor contains UPDATED_PRIMARY_COLOR
        defaultCompanyShouldNotBeFound("primaryColor.contains=" + UPDATED_PRIMARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPrimaryColorNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where primaryColor does not contain DEFAULT_PRIMARY_COLOR
        defaultCompanyShouldNotBeFound("primaryColor.doesNotContain=" + DEFAULT_PRIMARY_COLOR);

        // Get all the companyList where primaryColor does not contain UPDATED_PRIMARY_COLOR
        defaultCompanyShouldBeFound("primaryColor.doesNotContain=" + UPDATED_PRIMARY_COLOR);
    }


    @Test
    @Transactional
    public void getAllCompaniesBySecondaryColorIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where secondaryColor equals to DEFAULT_SECONDARY_COLOR
        defaultCompanyShouldBeFound("secondaryColor.equals=" + DEFAULT_SECONDARY_COLOR);

        // Get all the companyList where secondaryColor equals to UPDATED_SECONDARY_COLOR
        defaultCompanyShouldNotBeFound("secondaryColor.equals=" + UPDATED_SECONDARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesBySecondaryColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where secondaryColor not equals to DEFAULT_SECONDARY_COLOR
        defaultCompanyShouldNotBeFound("secondaryColor.notEquals=" + DEFAULT_SECONDARY_COLOR);

        // Get all the companyList where secondaryColor not equals to UPDATED_SECONDARY_COLOR
        defaultCompanyShouldBeFound("secondaryColor.notEquals=" + UPDATED_SECONDARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesBySecondaryColorIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where secondaryColor in DEFAULT_SECONDARY_COLOR or UPDATED_SECONDARY_COLOR
        defaultCompanyShouldBeFound("secondaryColor.in=" + DEFAULT_SECONDARY_COLOR + "," + UPDATED_SECONDARY_COLOR);

        // Get all the companyList where secondaryColor equals to UPDATED_SECONDARY_COLOR
        defaultCompanyShouldNotBeFound("secondaryColor.in=" + UPDATED_SECONDARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesBySecondaryColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where secondaryColor is not null
        defaultCompanyShouldBeFound("secondaryColor.specified=true");

        // Get all the companyList where secondaryColor is null
        defaultCompanyShouldNotBeFound("secondaryColor.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesBySecondaryColorContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where secondaryColor contains DEFAULT_SECONDARY_COLOR
        defaultCompanyShouldBeFound("secondaryColor.contains=" + DEFAULT_SECONDARY_COLOR);

        // Get all the companyList where secondaryColor contains UPDATED_SECONDARY_COLOR
        defaultCompanyShouldNotBeFound("secondaryColor.contains=" + UPDATED_SECONDARY_COLOR);
    }

    @Test
    @Transactional
    public void getAllCompaniesBySecondaryColorNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where secondaryColor does not contain DEFAULT_SECONDARY_COLOR
        defaultCompanyShouldNotBeFound("secondaryColor.doesNotContain=" + DEFAULT_SECONDARY_COLOR);

        // Get all the companyList where secondaryColor does not contain UPDATED_SECONDARY_COLOR
        defaultCompanyShouldBeFound("secondaryColor.doesNotContain=" + UPDATED_SECONDARY_COLOR);
    }


    @Test
    @Transactional
    public void getAllCompaniesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email equals to DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email not equals to DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the companyList where email not equals to UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email is not null
        defaultCompanyShouldBeFound("email.specified=true");

        // Get all the companyList where email is null
        defaultCompanyShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email contains DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyList where email contains UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email does not contain DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyList where email does not contain UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCompaniesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone equals to DEFAULT_PHONE
        defaultCompanyShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the companyList where phone equals to UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone not equals to DEFAULT_PHONE
        defaultCompanyShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the companyList where phone not equals to UPDATED_PHONE
        defaultCompanyShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCompanyShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the companyList where phone equals to UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone is not null
        defaultCompanyShouldBeFound("phone.specified=true");

        // Get all the companyList where phone is null
        defaultCompanyShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone contains DEFAULT_PHONE
        defaultCompanyShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the companyList where phone contains UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone does not contain DEFAULT_PHONE
        defaultCompanyShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the companyList where phone does not contain UPDATED_PHONE
        defaultCompanyShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment equals to DEFAULT_MAX_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("maxDayAppointment.equals=" + DEFAULT_MAX_DAY_APPOINTMENT);

        // Get all the companyList where maxDayAppointment equals to UPDATED_MAX_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("maxDayAppointment.equals=" + UPDATED_MAX_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment not equals to DEFAULT_MAX_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("maxDayAppointment.notEquals=" + DEFAULT_MAX_DAY_APPOINTMENT);

        // Get all the companyList where maxDayAppointment not equals to UPDATED_MAX_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("maxDayAppointment.notEquals=" + UPDATED_MAX_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment in DEFAULT_MAX_DAY_APPOINTMENT or UPDATED_MAX_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("maxDayAppointment.in=" + DEFAULT_MAX_DAY_APPOINTMENT + "," + UPDATED_MAX_DAY_APPOINTMENT);

        // Get all the companyList where maxDayAppointment equals to UPDATED_MAX_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("maxDayAppointment.in=" + UPDATED_MAX_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment is not null
        defaultCompanyShouldBeFound("maxDayAppointment.specified=true");

        // Get all the companyList where maxDayAppointment is null
        defaultCompanyShouldNotBeFound("maxDayAppointment.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment is greater than or equal to DEFAULT_MAX_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("maxDayAppointment.greaterThanOrEqual=" + DEFAULT_MAX_DAY_APPOINTMENT);

        // Get all the companyList where maxDayAppointment is greater than or equal to UPDATED_MAX_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("maxDayAppointment.greaterThanOrEqual=" + UPDATED_MAX_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment is less than or equal to DEFAULT_MAX_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("maxDayAppointment.lessThanOrEqual=" + DEFAULT_MAX_DAY_APPOINTMENT);

        // Get all the companyList where maxDayAppointment is less than or equal to SMALLER_MAX_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("maxDayAppointment.lessThanOrEqual=" + SMALLER_MAX_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment is less than DEFAULT_MAX_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("maxDayAppointment.lessThan=" + DEFAULT_MAX_DAY_APPOINTMENT);

        // Get all the companyList where maxDayAppointment is less than UPDATED_MAX_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("maxDayAppointment.lessThan=" + UPDATED_MAX_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMaxDayAppointmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where maxDayAppointment is greater than DEFAULT_MAX_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("maxDayAppointment.greaterThan=" + DEFAULT_MAX_DAY_APPOINTMENT);

        // Get all the companyList where maxDayAppointment is greater than SMALLER_MAX_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("maxDayAppointment.greaterThan=" + SMALLER_MAX_DAY_APPOINTMENT);
    }


    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment equals to DEFAULT_MIN_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("minDayAppointment.equals=" + DEFAULT_MIN_DAY_APPOINTMENT);

        // Get all the companyList where minDayAppointment equals to UPDATED_MIN_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("minDayAppointment.equals=" + UPDATED_MIN_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment not equals to DEFAULT_MIN_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("minDayAppointment.notEquals=" + DEFAULT_MIN_DAY_APPOINTMENT);

        // Get all the companyList where minDayAppointment not equals to UPDATED_MIN_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("minDayAppointment.notEquals=" + UPDATED_MIN_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment in DEFAULT_MIN_DAY_APPOINTMENT or UPDATED_MIN_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("minDayAppointment.in=" + DEFAULT_MIN_DAY_APPOINTMENT + "," + UPDATED_MIN_DAY_APPOINTMENT);

        // Get all the companyList where minDayAppointment equals to UPDATED_MIN_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("minDayAppointment.in=" + UPDATED_MIN_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment is not null
        defaultCompanyShouldBeFound("minDayAppointment.specified=true");

        // Get all the companyList where minDayAppointment is null
        defaultCompanyShouldNotBeFound("minDayAppointment.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment is greater than or equal to DEFAULT_MIN_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("minDayAppointment.greaterThanOrEqual=" + DEFAULT_MIN_DAY_APPOINTMENT);

        // Get all the companyList where minDayAppointment is greater than or equal to UPDATED_MIN_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("minDayAppointment.greaterThanOrEqual=" + UPDATED_MIN_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment is less than or equal to DEFAULT_MIN_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("minDayAppointment.lessThanOrEqual=" + DEFAULT_MIN_DAY_APPOINTMENT);

        // Get all the companyList where minDayAppointment is less than or equal to SMALLER_MIN_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("minDayAppointment.lessThanOrEqual=" + SMALLER_MIN_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment is less than DEFAULT_MIN_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("minDayAppointment.lessThan=" + DEFAULT_MIN_DAY_APPOINTMENT);

        // Get all the companyList where minDayAppointment is less than UPDATED_MIN_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("minDayAppointment.lessThan=" + UPDATED_MIN_DAY_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByMinDayAppointmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where minDayAppointment is greater than DEFAULT_MIN_DAY_APPOINTMENT
        defaultCompanyShouldNotBeFound("minDayAppointment.greaterThan=" + DEFAULT_MIN_DAY_APPOINTMENT);

        // Get all the companyList where minDayAppointment is greater than SMALLER_MIN_DAY_APPOINTMENT
        defaultCompanyShouldBeFound("minDayAppointment.greaterThan=" + SMALLER_MIN_DAY_APPOINTMENT);
    }


    @Test
    @Transactional
    public void getAllCompaniesByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat equals to DEFAULT_LAT
        defaultCompanyShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the companyList where lat equals to UPDATED_LAT
        defaultCompanyShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat not equals to DEFAULT_LAT
        defaultCompanyShouldNotBeFound("lat.notEquals=" + DEFAULT_LAT);

        // Get all the companyList where lat not equals to UPDATED_LAT
        defaultCompanyShouldBeFound("lat.notEquals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLatIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultCompanyShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the companyList where lat equals to UPDATED_LAT
        defaultCompanyShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat is not null
        defaultCompanyShouldBeFound("lat.specified=true");

        // Get all the companyList where lat is null
        defaultCompanyShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat is greater than or equal to DEFAULT_LAT
        defaultCompanyShouldBeFound("lat.greaterThanOrEqual=" + DEFAULT_LAT);

        // Get all the companyList where lat is greater than or equal to UPDATED_LAT
        defaultCompanyShouldNotBeFound("lat.greaterThanOrEqual=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat is less than or equal to DEFAULT_LAT
        defaultCompanyShouldBeFound("lat.lessThanOrEqual=" + DEFAULT_LAT);

        // Get all the companyList where lat is less than or equal to SMALLER_LAT
        defaultCompanyShouldNotBeFound("lat.lessThanOrEqual=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat is less than DEFAULT_LAT
        defaultCompanyShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the companyList where lat is less than UPDATED_LAT
        defaultCompanyShouldBeFound("lat.lessThan=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lat is greater than DEFAULT_LAT
        defaultCompanyShouldNotBeFound("lat.greaterThan=" + DEFAULT_LAT);

        // Get all the companyList where lat is greater than SMALLER_LAT
        defaultCompanyShouldBeFound("lat.greaterThan=" + SMALLER_LAT);
    }


    @Test
    @Transactional
    public void getAllCompaniesByLngIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng equals to DEFAULT_LNG
        defaultCompanyShouldBeFound("lng.equals=" + DEFAULT_LNG);

        // Get all the companyList where lng equals to UPDATED_LNG
        defaultCompanyShouldNotBeFound("lng.equals=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLngIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng not equals to DEFAULT_LNG
        defaultCompanyShouldNotBeFound("lng.notEquals=" + DEFAULT_LNG);

        // Get all the companyList where lng not equals to UPDATED_LNG
        defaultCompanyShouldBeFound("lng.notEquals=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLngIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng in DEFAULT_LNG or UPDATED_LNG
        defaultCompanyShouldBeFound("lng.in=" + DEFAULT_LNG + "," + UPDATED_LNG);

        // Get all the companyList where lng equals to UPDATED_LNG
        defaultCompanyShouldNotBeFound("lng.in=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLngIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng is not null
        defaultCompanyShouldBeFound("lng.specified=true");

        // Get all the companyList where lng is null
        defaultCompanyShouldNotBeFound("lng.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByLngIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng is greater than or equal to DEFAULT_LNG
        defaultCompanyShouldBeFound("lng.greaterThanOrEqual=" + DEFAULT_LNG);

        // Get all the companyList where lng is greater than or equal to UPDATED_LNG
        defaultCompanyShouldNotBeFound("lng.greaterThanOrEqual=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLngIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng is less than or equal to DEFAULT_LNG
        defaultCompanyShouldBeFound("lng.lessThanOrEqual=" + DEFAULT_LNG);

        // Get all the companyList where lng is less than or equal to SMALLER_LNG
        defaultCompanyShouldNotBeFound("lng.lessThanOrEqual=" + SMALLER_LNG);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLngIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng is less than DEFAULT_LNG
        defaultCompanyShouldNotBeFound("lng.lessThan=" + DEFAULT_LNG);

        // Get all the companyList where lng is less than UPDATED_LNG
        defaultCompanyShouldBeFound("lng.lessThan=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllCompaniesByLngIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lng is greater than DEFAULT_LNG
        defaultCompanyShouldNotBeFound("lng.greaterThan=" + DEFAULT_LNG);

        // Get all the companyList where lng is greater than SMALLER_LNG
        defaultCompanyShouldBeFound("lng.greaterThan=" + SMALLER_LNG);
    }


    @Test
    @Transactional
    public void getAllCompaniesByProfesionalIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Profesional profesional = ProfesionalResourceIT.createEntity(em);
        em.persist(profesional);
        em.flush();
        company.addProfesional(profesional);
        companyRepository.saveAndFlush(company);
        Long profesionalId = profesional.getId();

        // Get all the companyList where profesional equals to profesionalId
        defaultCompanyShouldBeFound("profesionalId.equals=" + profesionalId);

        // Get all the companyList where profesional equals to profesionalId + 1
        defaultCompanyShouldNotBeFound("profesionalId.equals=" + (profesionalId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByCalendarYearProfesionalIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        CalendarYearProfesional calendarYearProfesional = CalendarYearProfesionalResourceIT.createEntity(em);
        em.persist(calendarYearProfesional);
        em.flush();
        company.addCalendarYearProfesional(calendarYearProfesional);
        companyRepository.saveAndFlush(company);
        Long calendarYearProfesionalId = calendarYearProfesional.getId();

        // Get all the companyList where calendarYearProfesional equals to calendarYearProfesionalId
        defaultCompanyShouldBeFound("calendarYearProfesionalId.equals=" + calendarYearProfesionalId);

        // Get all the companyList where calendarYearProfesional equals to calendarYearProfesionalId + 1
        defaultCompanyShouldNotBeFound("calendarYearProfesionalId.equals=" + (calendarYearProfesionalId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByAppointmentIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Appointment appointment = AppointmentResourceIT.createEntity(em);
        em.persist(appointment);
        em.flush();
        company.addAppointment(appointment);
        companyRepository.saveAndFlush(company);
        Long appointmentId = appointment.getId();

        // Get all the companyList where appointment equals to appointmentId
        defaultCompanyShouldBeFound("appointmentId.equals=" + appointmentId);

        // Get all the companyList where appointment equals to appointmentId + 1
        defaultCompanyShouldNotBeFound("appointmentId.equals=" + (appointmentId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByTypeServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        TypeService typeService = TypeServiceResourceIT.createEntity(em);
        em.persist(typeService);
        em.flush();
        company.addTypeService(typeService);
        companyRepository.saveAndFlush(company);
        Long typeServiceId = typeService.getId();

        // Get all the companyList where typeService equals to typeServiceId
        defaultCompanyShouldBeFound("typeServiceId.equals=" + typeServiceId);

        // Get all the companyList where typeService equals to typeServiceId + 1
        defaultCompanyShouldNotBeFound("typeServiceId.equals=" + (typeServiceId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByPublicHolidayIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        PublicHoliday publicHoliday = PublicHolidayResourceIT.createEntity(em);
        em.persist(publicHoliday);
        em.flush();
        company.addPublicHoliday(publicHoliday);
        companyRepository.saveAndFlush(company);
        Long publicHolidayId = publicHoliday.getId();

        // Get all the companyList where publicHoliday equals to publicHolidayId
        defaultCompanyShouldBeFound("publicHolidayId.equals=" + publicHolidayId);

        // Get all the companyList where publicHoliday equals to publicHolidayId + 1
        defaultCompanyShouldNotBeFound("publicHolidayId.equals=" + (publicHolidayId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByTimeBandIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        TimeBand timeBand = TimeBandResourceIT.createEntity(em);
        em.persist(timeBand);
        em.flush();
        company.addTimeBand(timeBand);
        companyRepository.saveAndFlush(company);
        Long timeBandId = timeBand.getId();

        // Get all the companyList where timeBand equals to timeBandId
        defaultCompanyShouldBeFound("timeBandId.equals=" + timeBandId);

        // Get all the companyList where timeBand equals to timeBandId + 1
        defaultCompanyShouldNotBeFound("timeBandId.equals=" + (timeBandId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByDynamicContentIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        DynamicContent dynamicContent = DynamicContentResourceIT.createEntity(em);
        em.persist(dynamicContent);
        em.flush();
        company.addDynamicContent(dynamicContent);
        companyRepository.saveAndFlush(company);
        Long dynamicContentId = dynamicContent.getId();

        // Get all the companyList where dynamicContent equals to dynamicContentId
        defaultCompanyShouldBeFound("dynamicContentId.equals=" + dynamicContentId);

        // Get all the companyList where dynamicContent equals to dynamicContentId + 1
        defaultCompanyShouldNotBeFound("dynamicContentId.equals=" + (dynamicContentId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByMenuOptionsAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        MenuOptionsAvailable menuOptionsAvailable = MenuOptionsAvailableResourceIT.createEntity(em);
        em.persist(menuOptionsAvailable);
        em.flush();
        company.addMenuOptionsAvailable(menuOptionsAvailable);
        companyRepository.saveAndFlush(company);
        Long menuOptionsAvailableId = menuOptionsAvailable.getId();

        // Get all the companyList where menuOptionsAvailable equals to menuOptionsAvailableId
        defaultCompanyShouldBeFound("menuOptionsAvailableId.equals=" + menuOptionsAvailableId);

        // Get all the companyList where menuOptionsAvailable equals to menuOptionsAvailableId + 1
        defaultCompanyShouldNotBeFound("menuOptionsAvailableId.equals=" + (menuOptionsAvailableId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByTimeBandAvailableProfesionalDayIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay = TimeBandAvailableProfesionalDayResourceIT.createEntity(em);
        em.persist(timeBandAvailableProfesionalDay);
        em.flush();
        company.addTimeBandAvailableProfesionalDay(timeBandAvailableProfesionalDay);
        companyRepository.saveAndFlush(company);
        Long timeBandAvailableProfesionalDayId = timeBandAvailableProfesionalDay.getId();

        // Get all the companyList where timeBandAvailableProfesionalDay equals to timeBandAvailableProfesionalDayId
        defaultCompanyShouldBeFound("timeBandAvailableProfesionalDayId.equals=" + timeBandAvailableProfesionalDayId);

        // Get all the companyList where timeBandAvailableProfesionalDay equals to timeBandAvailableProfesionalDayId + 1
        defaultCompanyShouldNotBeFound("timeBandAvailableProfesionalDayId.equals=" + (timeBandAvailableProfesionalDayId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].primaryColor").value(hasItem(DEFAULT_PRIMARY_COLOR)))
            .andExpect(jsonPath("$.[*].secondaryColor").value(hasItem(DEFAULT_SECONDARY_COLOR)))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].maxDayAppointment").value(hasItem(DEFAULT_MAX_DAY_APPOINTMENT)))
            .andExpect(jsonPath("$.[*].minDayAppointment").value(hasItem(DEFAULT_MIN_DAY_APPOINTMENT)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())));

        // Check, that the count call also returns 1
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .primaryColor(UPDATED_PRIMARY_COLOR)
            .secondaryColor(UPDATED_SECONDARY_COLOR)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .maxDayAppointment(UPDATED_MAX_DAY_APPOINTMENT)
            .minDayAppointment(UPDATED_MIN_DAY_APPOINTMENT)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getPrimaryColor()).isEqualTo(UPDATED_PRIMARY_COLOR);
        assertThat(testCompany.getSecondaryColor()).isEqualTo(UPDATED_SECONDARY_COLOR);
        assertThat(testCompany.getUrlImg()).isEqualTo(UPDATED_URL_IMG);
        assertThat(testCompany.getUrlImgContentType()).isEqualTo(UPDATED_URL_IMG_CONTENT_TYPE);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompany.getMaxDayAppointment()).isEqualTo(UPDATED_MAX_DAY_APPOINTMENT);
        assertThat(testCompany.getMinDayAppointment()).isEqualTo(UPDATED_MIN_DAY_APPOINTMENT);
        assertThat(testCompany.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testCompany.getLng()).isEqualTo(UPDATED_LNG);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

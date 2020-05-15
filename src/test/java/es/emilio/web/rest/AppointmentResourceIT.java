package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.Appointment;
import es.emilio.domain.Profesional;
import es.emilio.domain.Company;
import es.emilio.repository.AppointmentRepository;
import es.emilio.service.AppointmentService;
import es.emilio.service.dto.AppointmentDTO;
import es.emilio.service.mapper.AppointmentMapper;
import es.emilio.service.dto.AppointmentCriteria;
import es.emilio.service.AppointmentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppointmentResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AppointmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVED = false;
    private static final Boolean UPDATED_ACTIVED = true;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentQueryService appointmentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppointmentMockMvc;

    private Appointment appointment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appointment createEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .name(DEFAULT_NAME)
            .comments(DEFAULT_COMMENTS)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .dni(DEFAULT_DNI)
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .actived(DEFAULT_ACTIVED);
        return appointment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appointment createUpdatedEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .name(UPDATED_NAME)
            .comments(UPDATED_COMMENTS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .dni(UPDATED_DNI)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .actived(UPDATED_ACTIVED);
        return appointment;
    }

    @BeforeEach
    public void initTest() {
        appointment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointment() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        // Create the Appointment
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate + 1);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppointment.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAppointment.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAppointment.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAppointment.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testAppointment.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testAppointment.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testAppointment.isActived()).isEqualTo(DEFAULT_ACTIVED);
    }

    @Test
    @Transactional
    public void createAppointmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        // Create the Appointment with an existing ID
        appointment.setId(1L);
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentRepository.findAll().size();
        // set the field null
        appointment.setName(null);

        // Create the Appointment, which fails.
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentRepository.findAll().size();
        // set the field null
        appointment.setEmail(null);

        // Create the Appointment, which fails.
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppointments() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", appointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appointment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.actived").value(DEFAULT_ACTIVED.booleanValue()));
    }


    @Test
    @Transactional
    public void getAppointmentsByIdFiltering() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        Long id = appointment.getId();

        defaultAppointmentShouldBeFound("id.equals=" + id);
        defaultAppointmentShouldNotBeFound("id.notEquals=" + id);

        defaultAppointmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAppointmentShouldNotBeFound("id.greaterThan=" + id);

        defaultAppointmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAppointmentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where name equals to DEFAULT_NAME
        defaultAppointmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the appointmentList where name equals to UPDATED_NAME
        defaultAppointmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where name not equals to DEFAULT_NAME
        defaultAppointmentShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the appointmentList where name not equals to UPDATED_NAME
        defaultAppointmentShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAppointmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the appointmentList where name equals to UPDATED_NAME
        defaultAppointmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where name is not null
        defaultAppointmentShouldBeFound("name.specified=true");

        // Get all the appointmentList where name is null
        defaultAppointmentShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppointmentsByNameContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where name contains DEFAULT_NAME
        defaultAppointmentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the appointmentList where name contains UPDATED_NAME
        defaultAppointmentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where name does not contain DEFAULT_NAME
        defaultAppointmentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the appointmentList where name does not contain UPDATED_NAME
        defaultAppointmentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where comments equals to DEFAULT_COMMENTS
        defaultAppointmentShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the appointmentList where comments equals to UPDATED_COMMENTS
        defaultAppointmentShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where comments not equals to DEFAULT_COMMENTS
        defaultAppointmentShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the appointmentList where comments not equals to UPDATED_COMMENTS
        defaultAppointmentShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultAppointmentShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the appointmentList where comments equals to UPDATED_COMMENTS
        defaultAppointmentShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where comments is not null
        defaultAppointmentShouldBeFound("comments.specified=true");

        // Get all the appointmentList where comments is null
        defaultAppointmentShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppointmentsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where comments contains DEFAULT_COMMENTS
        defaultAppointmentShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the appointmentList where comments contains UPDATED_COMMENTS
        defaultAppointmentShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where comments does not contain DEFAULT_COMMENTS
        defaultAppointmentShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the appointmentList where comments does not contain UPDATED_COMMENTS
        defaultAppointmentShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where email equals to DEFAULT_EMAIL
        defaultAppointmentShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the appointmentList where email equals to UPDATED_EMAIL
        defaultAppointmentShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where email not equals to DEFAULT_EMAIL
        defaultAppointmentShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the appointmentList where email not equals to UPDATED_EMAIL
        defaultAppointmentShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultAppointmentShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the appointmentList where email equals to UPDATED_EMAIL
        defaultAppointmentShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where email is not null
        defaultAppointmentShouldBeFound("email.specified=true");

        // Get all the appointmentList where email is null
        defaultAppointmentShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppointmentsByEmailContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where email contains DEFAULT_EMAIL
        defaultAppointmentShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the appointmentList where email contains UPDATED_EMAIL
        defaultAppointmentShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where email does not contain DEFAULT_EMAIL
        defaultAppointmentShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the appointmentList where email does not contain UPDATED_EMAIL
        defaultAppointmentShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where phone equals to DEFAULT_PHONE
        defaultAppointmentShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the appointmentList where phone equals to UPDATED_PHONE
        defaultAppointmentShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where phone not equals to DEFAULT_PHONE
        defaultAppointmentShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the appointmentList where phone not equals to UPDATED_PHONE
        defaultAppointmentShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultAppointmentShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the appointmentList where phone equals to UPDATED_PHONE
        defaultAppointmentShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where phone is not null
        defaultAppointmentShouldBeFound("phone.specified=true");

        // Get all the appointmentList where phone is null
        defaultAppointmentShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppointmentsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where phone contains DEFAULT_PHONE
        defaultAppointmentShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the appointmentList where phone contains UPDATED_PHONE
        defaultAppointmentShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where phone does not contain DEFAULT_PHONE
        defaultAppointmentShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the appointmentList where phone does not contain UPDATED_PHONE
        defaultAppointmentShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByDniIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where dni equals to DEFAULT_DNI
        defaultAppointmentShouldBeFound("dni.equals=" + DEFAULT_DNI);

        // Get all the appointmentList where dni equals to UPDATED_DNI
        defaultAppointmentShouldNotBeFound("dni.equals=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByDniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where dni not equals to DEFAULT_DNI
        defaultAppointmentShouldNotBeFound("dni.notEquals=" + DEFAULT_DNI);

        // Get all the appointmentList where dni not equals to UPDATED_DNI
        defaultAppointmentShouldBeFound("dni.notEquals=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByDniIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where dni in DEFAULT_DNI or UPDATED_DNI
        defaultAppointmentShouldBeFound("dni.in=" + DEFAULT_DNI + "," + UPDATED_DNI);

        // Get all the appointmentList where dni equals to UPDATED_DNI
        defaultAppointmentShouldNotBeFound("dni.in=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByDniIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where dni is not null
        defaultAppointmentShouldBeFound("dni.specified=true");

        // Get all the appointmentList where dni is null
        defaultAppointmentShouldNotBeFound("dni.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppointmentsByDniContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where dni contains DEFAULT_DNI
        defaultAppointmentShouldBeFound("dni.contains=" + DEFAULT_DNI);

        // Get all the appointmentList where dni contains UPDATED_DNI
        defaultAppointmentShouldNotBeFound("dni.contains=" + UPDATED_DNI);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByDniNotContainsSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where dni does not contain DEFAULT_DNI
        defaultAppointmentShouldNotBeFound("dni.doesNotContain=" + DEFAULT_DNI);

        // Get all the appointmentList where dni does not contain UPDATED_DNI
        defaultAppointmentShouldBeFound("dni.doesNotContain=" + UPDATED_DNI);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByStartIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where start equals to DEFAULT_START
        defaultAppointmentShouldBeFound("start.equals=" + DEFAULT_START);

        // Get all the appointmentList where start equals to UPDATED_START
        defaultAppointmentShouldNotBeFound("start.equals=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByStartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where start not equals to DEFAULT_START
        defaultAppointmentShouldNotBeFound("start.notEquals=" + DEFAULT_START);

        // Get all the appointmentList where start not equals to UPDATED_START
        defaultAppointmentShouldBeFound("start.notEquals=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByStartIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where start in DEFAULT_START or UPDATED_START
        defaultAppointmentShouldBeFound("start.in=" + DEFAULT_START + "," + UPDATED_START);

        // Get all the appointmentList where start equals to UPDATED_START
        defaultAppointmentShouldNotBeFound("start.in=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where start is not null
        defaultAppointmentShouldBeFound("start.specified=true");

        // Get all the appointmentList where start is null
        defaultAppointmentShouldNotBeFound("start.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEndIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where end equals to DEFAULT_END
        defaultAppointmentShouldBeFound("end.equals=" + DEFAULT_END);

        // Get all the appointmentList where end equals to UPDATED_END
        defaultAppointmentShouldNotBeFound("end.equals=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where end not equals to DEFAULT_END
        defaultAppointmentShouldNotBeFound("end.notEquals=" + DEFAULT_END);

        // Get all the appointmentList where end not equals to UPDATED_END
        defaultAppointmentShouldBeFound("end.notEquals=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEndIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where end in DEFAULT_END or UPDATED_END
        defaultAppointmentShouldBeFound("end.in=" + DEFAULT_END + "," + UPDATED_END);

        // Get all the appointmentList where end equals to UPDATED_END
        defaultAppointmentShouldNotBeFound("end.in=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where end is not null
        defaultAppointmentShouldBeFound("end.specified=true");

        // Get all the appointmentList where end is null
        defaultAppointmentShouldNotBeFound("end.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByActivedIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where actived equals to DEFAULT_ACTIVED
        defaultAppointmentShouldBeFound("actived.equals=" + DEFAULT_ACTIVED);

        // Get all the appointmentList where actived equals to UPDATED_ACTIVED
        defaultAppointmentShouldNotBeFound("actived.equals=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByActivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where actived not equals to DEFAULT_ACTIVED
        defaultAppointmentShouldNotBeFound("actived.notEquals=" + DEFAULT_ACTIVED);

        // Get all the appointmentList where actived not equals to UPDATED_ACTIVED
        defaultAppointmentShouldBeFound("actived.notEquals=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByActivedIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where actived in DEFAULT_ACTIVED or UPDATED_ACTIVED
        defaultAppointmentShouldBeFound("actived.in=" + DEFAULT_ACTIVED + "," + UPDATED_ACTIVED);

        // Get all the appointmentList where actived equals to UPDATED_ACTIVED
        defaultAppointmentShouldNotBeFound("actived.in=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByActivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where actived is not null
        defaultAppointmentShouldBeFound("actived.specified=true");

        // Get all the appointmentList where actived is null
        defaultAppointmentShouldNotBeFound("actived.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByProfesionalIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);
        Profesional profesional = ProfesionalResourceIT.createEntity(em);
        em.persist(profesional);
        em.flush();
        appointment.setProfesional(profesional);
        appointmentRepository.saveAndFlush(appointment);
        Long profesionalId = profesional.getId();

        // Get all the appointmentList where profesional equals to profesionalId
        defaultAppointmentShouldBeFound("profesionalId.equals=" + profesionalId);

        // Get all the appointmentList where profesional equals to profesionalId + 1
        defaultAppointmentShouldNotBeFound("profesionalId.equals=" + (profesionalId + 1));
    }


    @Test
    @Transactional
    public void getAllAppointmentsByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        appointment.setCompany(company);
        appointmentRepository.saveAndFlush(appointment);
        Long companyId = company.getId();

        // Get all the appointmentList where company equals to companyId
        defaultAppointmentShouldBeFound("companyId.equals=" + companyId);

        // Get all the appointmentList where company equals to companyId + 1
        defaultAppointmentShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppointmentShouldBeFound(String filter) throws Exception {
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())));

        // Check, that the count call also returns 1
        restAppointmentMockMvc.perform(get("/api/appointments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppointmentShouldNotBeFound(String filter) throws Exception {
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppointmentMockMvc.perform(get("/api/appointments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAppointment() throws Exception {
        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Update the appointment
        Appointment updatedAppointment = appointmentRepository.findById(appointment.getId()).get();
        // Disconnect from session so that the updates on updatedAppointment are not directly saved in db
        em.detach(updatedAppointment);
        updatedAppointment
            .name(UPDATED_NAME)
            .comments(UPDATED_COMMENTS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .dni(UPDATED_DNI)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .actived(UPDATED_ACTIVED);
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(updatedAppointment);

        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isOk());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppointment.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAppointment.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAppointment.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAppointment.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testAppointment.getStart()).isEqualTo(UPDATED_START);
        assertThat(testAppointment.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testAppointment.isActived()).isEqualTo(UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointment() throws Exception {
        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Create the Appointment
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeDelete = appointmentRepository.findAll().size();

        // Delete the appointment
        restAppointmentMockMvc.perform(delete("/api/appointments/{id}", appointment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

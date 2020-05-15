package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.CalendarYearProfesional;
import es.emilio.repository.CalendarYearProfesionalRepository;
import es.emilio.service.CalendarYearProfesionalService;
import es.emilio.service.dto.CalendarYearProfesionalDTO;
import es.emilio.service.mapper.CalendarYearProfesionalMapper;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CalendarYearProfesionalResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CalendarYearProfesionalResourceIT {

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Boolean DEFAULT_IS_PUBLIC_HOLIDAY = false;
    private static final Boolean UPDATED_IS_PUBLIC_HOLIDAY = true;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CalendarYearProfesionalRepository calendarYearProfesionalRepository;

    @Autowired
    private CalendarYearProfesionalMapper calendarYearProfesionalMapper;

    @Autowired
    private CalendarYearProfesionalService calendarYearProfesionalService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalendarYearProfesionalMockMvc;

    private CalendarYearProfesional calendarYearProfesional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarYearProfesional createEntity(EntityManager em) {
        CalendarYearProfesional calendarYearProfesional = new CalendarYearProfesional()
            .day(DEFAULT_DAY)
            .year(DEFAULT_YEAR)
            .isPublicHoliday(DEFAULT_IS_PUBLIC_HOLIDAY)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return calendarYearProfesional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarYearProfesional createUpdatedEntity(EntityManager em) {
        CalendarYearProfesional calendarYearProfesional = new CalendarYearProfesional()
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .isPublicHoliday(UPDATED_IS_PUBLIC_HOLIDAY)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return calendarYearProfesional;
    }

    @BeforeEach
    public void initTest() {
        calendarYearProfesional = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendarYearProfesional() throws Exception {
        int databaseSizeBeforeCreate = calendarYearProfesionalRepository.findAll().size();

        // Create the CalendarYearProfesional
        CalendarYearProfesionalDTO calendarYearProfesionalDTO = calendarYearProfesionalMapper.toDto(calendarYearProfesional);
        restCalendarYearProfesionalMockMvc.perform(post("/api/calendar-year-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearProfesionalDTO)))
            .andExpect(status().isCreated());

        // Validate the CalendarYearProfesional in the database
        List<CalendarYearProfesional> calendarYearProfesionalList = calendarYearProfesionalRepository.findAll();
        assertThat(calendarYearProfesionalList).hasSize(databaseSizeBeforeCreate + 1);
        CalendarYearProfesional testCalendarYearProfesional = calendarYearProfesionalList.get(calendarYearProfesionalList.size() - 1);
        assertThat(testCalendarYearProfesional.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testCalendarYearProfesional.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCalendarYearProfesional.isIsPublicHoliday()).isEqualTo(DEFAULT_IS_PUBLIC_HOLIDAY);
        assertThat(testCalendarYearProfesional.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCalendarYearProfesional.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createCalendarYearProfesionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarYearProfesionalRepository.findAll().size();

        // Create the CalendarYearProfesional with an existing ID
        calendarYearProfesional.setId(1L);
        CalendarYearProfesionalDTO calendarYearProfesionalDTO = calendarYearProfesionalMapper.toDto(calendarYearProfesional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarYearProfesionalMockMvc.perform(post("/api/calendar-year-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearProfesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarYearProfesional in the database
        List<CalendarYearProfesional> calendarYearProfesionalList = calendarYearProfesionalRepository.findAll();
        assertThat(calendarYearProfesionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCalendarYearProfesionals() throws Exception {
        // Initialize the database
        calendarYearProfesionalRepository.saveAndFlush(calendarYearProfesional);

        // Get all the calendarYearProfesionalList
        restCalendarYearProfesionalMockMvc.perform(get("/api/calendar-year-profesionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarYearProfesional.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].isPublicHoliday").value(hasItem(DEFAULT_IS_PUBLIC_HOLIDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getCalendarYearProfesional() throws Exception {
        // Initialize the database
        calendarYearProfesionalRepository.saveAndFlush(calendarYearProfesional);

        // Get the calendarYearProfesional
        restCalendarYearProfesionalMockMvc.perform(get("/api/calendar-year-profesionals/{id}", calendarYearProfesional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendarYearProfesional.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.isPublicHoliday").value(DEFAULT_IS_PUBLIC_HOLIDAY.booleanValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalendarYearProfesional() throws Exception {
        // Get the calendarYearProfesional
        restCalendarYearProfesionalMockMvc.perform(get("/api/calendar-year-profesionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarYearProfesional() throws Exception {
        // Initialize the database
        calendarYearProfesionalRepository.saveAndFlush(calendarYearProfesional);

        int databaseSizeBeforeUpdate = calendarYearProfesionalRepository.findAll().size();

        // Update the calendarYearProfesional
        CalendarYearProfesional updatedCalendarYearProfesional = calendarYearProfesionalRepository.findById(calendarYearProfesional.getId()).get();
        // Disconnect from session so that the updates on updatedCalendarYearProfesional are not directly saved in db
        em.detach(updatedCalendarYearProfesional);
        updatedCalendarYearProfesional
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .isPublicHoliday(UPDATED_IS_PUBLIC_HOLIDAY)
            .start(UPDATED_START)
            .end(UPDATED_END);
        CalendarYearProfesionalDTO calendarYearProfesionalDTO = calendarYearProfesionalMapper.toDto(updatedCalendarYearProfesional);

        restCalendarYearProfesionalMockMvc.perform(put("/api/calendar-year-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearProfesionalDTO)))
            .andExpect(status().isOk());

        // Validate the CalendarYearProfesional in the database
        List<CalendarYearProfesional> calendarYearProfesionalList = calendarYearProfesionalRepository.findAll();
        assertThat(calendarYearProfesionalList).hasSize(databaseSizeBeforeUpdate);
        CalendarYearProfesional testCalendarYearProfesional = calendarYearProfesionalList.get(calendarYearProfesionalList.size() - 1);
        assertThat(testCalendarYearProfesional.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testCalendarYearProfesional.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCalendarYearProfesional.isIsPublicHoliday()).isEqualTo(UPDATED_IS_PUBLIC_HOLIDAY);
        assertThat(testCalendarYearProfesional.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCalendarYearProfesional.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendarYearProfesional() throws Exception {
        int databaseSizeBeforeUpdate = calendarYearProfesionalRepository.findAll().size();

        // Create the CalendarYearProfesional
        CalendarYearProfesionalDTO calendarYearProfesionalDTO = calendarYearProfesionalMapper.toDto(calendarYearProfesional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarYearProfesionalMockMvc.perform(put("/api/calendar-year-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearProfesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarYearProfesional in the database
        List<CalendarYearProfesional> calendarYearProfesionalList = calendarYearProfesionalRepository.findAll();
        assertThat(calendarYearProfesionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendarYearProfesional() throws Exception {
        // Initialize the database
        calendarYearProfesionalRepository.saveAndFlush(calendarYearProfesional);

        int databaseSizeBeforeDelete = calendarYearProfesionalRepository.findAll().size();

        // Delete the calendarYearProfesional
        restCalendarYearProfesionalMockMvc.perform(delete("/api/calendar-year-profesionals/{id}", calendarYearProfesional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalendarYearProfesional> calendarYearProfesionalList = calendarYearProfesionalRepository.findAll();
        assertThat(calendarYearProfesionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

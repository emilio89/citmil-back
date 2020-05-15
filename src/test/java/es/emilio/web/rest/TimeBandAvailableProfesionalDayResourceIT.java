package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.TimeBandAvailableProfesionalDay;
import es.emilio.repository.TimeBandAvailableProfesionalDayRepository;
import es.emilio.service.TimeBandAvailableProfesionalDayService;
import es.emilio.service.dto.TimeBandAvailableProfesionalDayDTO;
import es.emilio.service.mapper.TimeBandAvailableProfesionalDayMapper;

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
 * Integration tests for the {@link TimeBandAvailableProfesionalDayResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TimeBandAvailableProfesionalDayResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TimeBandAvailableProfesionalDayRepository timeBandAvailableProfesionalDayRepository;

    @Autowired
    private TimeBandAvailableProfesionalDayMapper timeBandAvailableProfesionalDayMapper;

    @Autowired
    private TimeBandAvailableProfesionalDayService timeBandAvailableProfesionalDayService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeBandAvailableProfesionalDayMockMvc;

    private TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeBandAvailableProfesionalDay createEntity(EntityManager em) {
        TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay = new TimeBandAvailableProfesionalDay()
            .year(DEFAULT_YEAR)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return timeBandAvailableProfesionalDay;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeBandAvailableProfesionalDay createUpdatedEntity(EntityManager em) {
        TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay = new TimeBandAvailableProfesionalDay()
            .year(UPDATED_YEAR)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return timeBandAvailableProfesionalDay;
    }

    @BeforeEach
    public void initTest() {
        timeBandAvailableProfesionalDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeBandAvailableProfesionalDay() throws Exception {
        int databaseSizeBeforeCreate = timeBandAvailableProfesionalDayRepository.findAll().size();

        // Create the TimeBandAvailableProfesionalDay
        TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO = timeBandAvailableProfesionalDayMapper.toDto(timeBandAvailableProfesionalDay);
        restTimeBandAvailableProfesionalDayMockMvc.perform(post("/api/time-band-available-profesional-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableProfesionalDayDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeBandAvailableProfesionalDay in the database
        List<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDayList = timeBandAvailableProfesionalDayRepository.findAll();
        assertThat(timeBandAvailableProfesionalDayList).hasSize(databaseSizeBeforeCreate + 1);
        TimeBandAvailableProfesionalDay testTimeBandAvailableProfesionalDay = timeBandAvailableProfesionalDayList.get(timeBandAvailableProfesionalDayList.size() - 1);
        assertThat(testTimeBandAvailableProfesionalDay.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTimeBandAvailableProfesionalDay.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testTimeBandAvailableProfesionalDay.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createTimeBandAvailableProfesionalDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeBandAvailableProfesionalDayRepository.findAll().size();

        // Create the TimeBandAvailableProfesionalDay with an existing ID
        timeBandAvailableProfesionalDay.setId(1L);
        TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO = timeBandAvailableProfesionalDayMapper.toDto(timeBandAvailableProfesionalDay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeBandAvailableProfesionalDayMockMvc.perform(post("/api/time-band-available-profesional-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableProfesionalDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeBandAvailableProfesionalDay in the database
        List<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDayList = timeBandAvailableProfesionalDayRepository.findAll();
        assertThat(timeBandAvailableProfesionalDayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTimeBandAvailableProfesionalDays() throws Exception {
        // Initialize the database
        timeBandAvailableProfesionalDayRepository.saveAndFlush(timeBandAvailableProfesionalDay);

        // Get all the timeBandAvailableProfesionalDayList
        restTimeBandAvailableProfesionalDayMockMvc.perform(get("/api/time-band-available-profesional-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeBandAvailableProfesionalDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getTimeBandAvailableProfesionalDay() throws Exception {
        // Initialize the database
        timeBandAvailableProfesionalDayRepository.saveAndFlush(timeBandAvailableProfesionalDay);

        // Get the timeBandAvailableProfesionalDay
        restTimeBandAvailableProfesionalDayMockMvc.perform(get("/api/time-band-available-profesional-days/{id}", timeBandAvailableProfesionalDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeBandAvailableProfesionalDay.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeBandAvailableProfesionalDay() throws Exception {
        // Get the timeBandAvailableProfesionalDay
        restTimeBandAvailableProfesionalDayMockMvc.perform(get("/api/time-band-available-profesional-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeBandAvailableProfesionalDay() throws Exception {
        // Initialize the database
        timeBandAvailableProfesionalDayRepository.saveAndFlush(timeBandAvailableProfesionalDay);

        int databaseSizeBeforeUpdate = timeBandAvailableProfesionalDayRepository.findAll().size();

        // Update the timeBandAvailableProfesionalDay
        TimeBandAvailableProfesionalDay updatedTimeBandAvailableProfesionalDay = timeBandAvailableProfesionalDayRepository.findById(timeBandAvailableProfesionalDay.getId()).get();
        // Disconnect from session so that the updates on updatedTimeBandAvailableProfesionalDay are not directly saved in db
        em.detach(updatedTimeBandAvailableProfesionalDay);
        updatedTimeBandAvailableProfesionalDay
            .year(UPDATED_YEAR)
            .start(UPDATED_START)
            .end(UPDATED_END);
        TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO = timeBandAvailableProfesionalDayMapper.toDto(updatedTimeBandAvailableProfesionalDay);

        restTimeBandAvailableProfesionalDayMockMvc.perform(put("/api/time-band-available-profesional-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableProfesionalDayDTO)))
            .andExpect(status().isOk());

        // Validate the TimeBandAvailableProfesionalDay in the database
        List<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDayList = timeBandAvailableProfesionalDayRepository.findAll();
        assertThat(timeBandAvailableProfesionalDayList).hasSize(databaseSizeBeforeUpdate);
        TimeBandAvailableProfesionalDay testTimeBandAvailableProfesionalDay = timeBandAvailableProfesionalDayList.get(timeBandAvailableProfesionalDayList.size() - 1);
        assertThat(testTimeBandAvailableProfesionalDay.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTimeBandAvailableProfesionalDay.getStart()).isEqualTo(UPDATED_START);
        assertThat(testTimeBandAvailableProfesionalDay.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeBandAvailableProfesionalDay() throws Exception {
        int databaseSizeBeforeUpdate = timeBandAvailableProfesionalDayRepository.findAll().size();

        // Create the TimeBandAvailableProfesionalDay
        TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO = timeBandAvailableProfesionalDayMapper.toDto(timeBandAvailableProfesionalDay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeBandAvailableProfesionalDayMockMvc.perform(put("/api/time-band-available-profesional-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableProfesionalDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeBandAvailableProfesionalDay in the database
        List<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDayList = timeBandAvailableProfesionalDayRepository.findAll();
        assertThat(timeBandAvailableProfesionalDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeBandAvailableProfesionalDay() throws Exception {
        // Initialize the database
        timeBandAvailableProfesionalDayRepository.saveAndFlush(timeBandAvailableProfesionalDay);

        int databaseSizeBeforeDelete = timeBandAvailableProfesionalDayRepository.findAll().size();

        // Delete the timeBandAvailableProfesionalDay
        restTimeBandAvailableProfesionalDayMockMvc.perform(delete("/api/time-band-available-profesional-days/{id}", timeBandAvailableProfesionalDay.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDayList = timeBandAvailableProfesionalDayRepository.findAll();
        assertThat(timeBandAvailableProfesionalDayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.TimeBandAvailableUserDay;
import es.emilio.repository.TimeBandAvailableUserDayRepository;
import es.emilio.service.TimeBandAvailableUserDayService;
import es.emilio.service.dto.TimeBandAvailableUserDayDTO;
import es.emilio.service.mapper.TimeBandAvailableUserDayMapper;

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
 * Integration tests for the {@link TimeBandAvailableUserDayResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TimeBandAvailableUserDayResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TimeBandAvailableUserDayRepository timeBandAvailableUserDayRepository;

    @Autowired
    private TimeBandAvailableUserDayMapper timeBandAvailableUserDayMapper;

    @Autowired
    private TimeBandAvailableUserDayService timeBandAvailableUserDayService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeBandAvailableUserDayMockMvc;

    private TimeBandAvailableUserDay timeBandAvailableUserDay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeBandAvailableUserDay createEntity(EntityManager em) {
        TimeBandAvailableUserDay timeBandAvailableUserDay = new TimeBandAvailableUserDay()
            .year(DEFAULT_YEAR)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return timeBandAvailableUserDay;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeBandAvailableUserDay createUpdatedEntity(EntityManager em) {
        TimeBandAvailableUserDay timeBandAvailableUserDay = new TimeBandAvailableUserDay()
            .year(UPDATED_YEAR)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return timeBandAvailableUserDay;
    }

    @BeforeEach
    public void initTest() {
        timeBandAvailableUserDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeBandAvailableUserDay() throws Exception {
        int databaseSizeBeforeCreate = timeBandAvailableUserDayRepository.findAll().size();

        // Create the TimeBandAvailableUserDay
        TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO = timeBandAvailableUserDayMapper.toDto(timeBandAvailableUserDay);
        restTimeBandAvailableUserDayMockMvc.perform(post("/api/time-band-available-user-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableUserDayDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeBandAvailableUserDay in the database
        List<TimeBandAvailableUserDay> timeBandAvailableUserDayList = timeBandAvailableUserDayRepository.findAll();
        assertThat(timeBandAvailableUserDayList).hasSize(databaseSizeBeforeCreate + 1);
        TimeBandAvailableUserDay testTimeBandAvailableUserDay = timeBandAvailableUserDayList.get(timeBandAvailableUserDayList.size() - 1);
        assertThat(testTimeBandAvailableUserDay.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTimeBandAvailableUserDay.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testTimeBandAvailableUserDay.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createTimeBandAvailableUserDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeBandAvailableUserDayRepository.findAll().size();

        // Create the TimeBandAvailableUserDay with an existing ID
        timeBandAvailableUserDay.setId(1L);
        TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO = timeBandAvailableUserDayMapper.toDto(timeBandAvailableUserDay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeBandAvailableUserDayMockMvc.perform(post("/api/time-band-available-user-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableUserDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeBandAvailableUserDay in the database
        List<TimeBandAvailableUserDay> timeBandAvailableUserDayList = timeBandAvailableUserDayRepository.findAll();
        assertThat(timeBandAvailableUserDayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTimeBandAvailableUserDays() throws Exception {
        // Initialize the database
        timeBandAvailableUserDayRepository.saveAndFlush(timeBandAvailableUserDay);

        // Get all the timeBandAvailableUserDayList
        restTimeBandAvailableUserDayMockMvc.perform(get("/api/time-band-available-user-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeBandAvailableUserDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getTimeBandAvailableUserDay() throws Exception {
        // Initialize the database
        timeBandAvailableUserDayRepository.saveAndFlush(timeBandAvailableUserDay);

        // Get the timeBandAvailableUserDay
        restTimeBandAvailableUserDayMockMvc.perform(get("/api/time-band-available-user-days/{id}", timeBandAvailableUserDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeBandAvailableUserDay.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeBandAvailableUserDay() throws Exception {
        // Get the timeBandAvailableUserDay
        restTimeBandAvailableUserDayMockMvc.perform(get("/api/time-band-available-user-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeBandAvailableUserDay() throws Exception {
        // Initialize the database
        timeBandAvailableUserDayRepository.saveAndFlush(timeBandAvailableUserDay);

        int databaseSizeBeforeUpdate = timeBandAvailableUserDayRepository.findAll().size();

        // Update the timeBandAvailableUserDay
        TimeBandAvailableUserDay updatedTimeBandAvailableUserDay = timeBandAvailableUserDayRepository.findById(timeBandAvailableUserDay.getId()).get();
        // Disconnect from session so that the updates on updatedTimeBandAvailableUserDay are not directly saved in db
        em.detach(updatedTimeBandAvailableUserDay);
        updatedTimeBandAvailableUserDay
            .year(UPDATED_YEAR)
            .start(UPDATED_START)
            .end(UPDATED_END);
        TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO = timeBandAvailableUserDayMapper.toDto(updatedTimeBandAvailableUserDay);

        restTimeBandAvailableUserDayMockMvc.perform(put("/api/time-band-available-user-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableUserDayDTO)))
            .andExpect(status().isOk());

        // Validate the TimeBandAvailableUserDay in the database
        List<TimeBandAvailableUserDay> timeBandAvailableUserDayList = timeBandAvailableUserDayRepository.findAll();
        assertThat(timeBandAvailableUserDayList).hasSize(databaseSizeBeforeUpdate);
        TimeBandAvailableUserDay testTimeBandAvailableUserDay = timeBandAvailableUserDayList.get(timeBandAvailableUserDayList.size() - 1);
        assertThat(testTimeBandAvailableUserDay.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTimeBandAvailableUserDay.getStart()).isEqualTo(UPDATED_START);
        assertThat(testTimeBandAvailableUserDay.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeBandAvailableUserDay() throws Exception {
        int databaseSizeBeforeUpdate = timeBandAvailableUserDayRepository.findAll().size();

        // Create the TimeBandAvailableUserDay
        TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO = timeBandAvailableUserDayMapper.toDto(timeBandAvailableUserDay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeBandAvailableUserDayMockMvc.perform(put("/api/time-band-available-user-days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandAvailableUserDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeBandAvailableUserDay in the database
        List<TimeBandAvailableUserDay> timeBandAvailableUserDayList = timeBandAvailableUserDayRepository.findAll();
        assertThat(timeBandAvailableUserDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeBandAvailableUserDay() throws Exception {
        // Initialize the database
        timeBandAvailableUserDayRepository.saveAndFlush(timeBandAvailableUserDay);

        int databaseSizeBeforeDelete = timeBandAvailableUserDayRepository.findAll().size();

        // Delete the timeBandAvailableUserDay
        restTimeBandAvailableUserDayMockMvc.perform(delete("/api/time-band-available-user-days/{id}", timeBandAvailableUserDay.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeBandAvailableUserDay> timeBandAvailableUserDayList = timeBandAvailableUserDayRepository.findAll();
        assertThat(timeBandAvailableUserDayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

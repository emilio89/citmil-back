package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.CalendarYearUser;
import es.emilio.repository.CalendarYearUserRepository;
import es.emilio.service.CalendarYearUserService;
import es.emilio.service.dto.CalendarYearUserDTO;
import es.emilio.service.mapper.CalendarYearUserMapper;

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
 * Integration tests for the {@link CalendarYearUserResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CalendarYearUserResourceIT {

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
    private CalendarYearUserRepository calendarYearUserRepository;

    @Autowired
    private CalendarYearUserMapper calendarYearUserMapper;

    @Autowired
    private CalendarYearUserService calendarYearUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalendarYearUserMockMvc;

    private CalendarYearUser calendarYearUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarYearUser createEntity(EntityManager em) {
        CalendarYearUser calendarYearUser = new CalendarYearUser()
            .day(DEFAULT_DAY)
            .year(DEFAULT_YEAR)
            .isPublicHoliday(DEFAULT_IS_PUBLIC_HOLIDAY)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return calendarYearUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarYearUser createUpdatedEntity(EntityManager em) {
        CalendarYearUser calendarYearUser = new CalendarYearUser()
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .isPublicHoliday(UPDATED_IS_PUBLIC_HOLIDAY)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return calendarYearUser;
    }

    @BeforeEach
    public void initTest() {
        calendarYearUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendarYearUser() throws Exception {
        int databaseSizeBeforeCreate = calendarYearUserRepository.findAll().size();

        // Create the CalendarYearUser
        CalendarYearUserDTO calendarYearUserDTO = calendarYearUserMapper.toDto(calendarYearUser);
        restCalendarYearUserMockMvc.perform(post("/api/calendar-year-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CalendarYearUser in the database
        List<CalendarYearUser> calendarYearUserList = calendarYearUserRepository.findAll();
        assertThat(calendarYearUserList).hasSize(databaseSizeBeforeCreate + 1);
        CalendarYearUser testCalendarYearUser = calendarYearUserList.get(calendarYearUserList.size() - 1);
        assertThat(testCalendarYearUser.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testCalendarYearUser.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCalendarYearUser.isIsPublicHoliday()).isEqualTo(DEFAULT_IS_PUBLIC_HOLIDAY);
        assertThat(testCalendarYearUser.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCalendarYearUser.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createCalendarYearUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarYearUserRepository.findAll().size();

        // Create the CalendarYearUser with an existing ID
        calendarYearUser.setId(1L);
        CalendarYearUserDTO calendarYearUserDTO = calendarYearUserMapper.toDto(calendarYearUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarYearUserMockMvc.perform(post("/api/calendar-year-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarYearUser in the database
        List<CalendarYearUser> calendarYearUserList = calendarYearUserRepository.findAll();
        assertThat(calendarYearUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCalendarYearUsers() throws Exception {
        // Initialize the database
        calendarYearUserRepository.saveAndFlush(calendarYearUser);

        // Get all the calendarYearUserList
        restCalendarYearUserMockMvc.perform(get("/api/calendar-year-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarYearUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].isPublicHoliday").value(hasItem(DEFAULT_IS_PUBLIC_HOLIDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getCalendarYearUser() throws Exception {
        // Initialize the database
        calendarYearUserRepository.saveAndFlush(calendarYearUser);

        // Get the calendarYearUser
        restCalendarYearUserMockMvc.perform(get("/api/calendar-year-users/{id}", calendarYearUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendarYearUser.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.isPublicHoliday").value(DEFAULT_IS_PUBLIC_HOLIDAY.booleanValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalendarYearUser() throws Exception {
        // Get the calendarYearUser
        restCalendarYearUserMockMvc.perform(get("/api/calendar-year-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarYearUser() throws Exception {
        // Initialize the database
        calendarYearUserRepository.saveAndFlush(calendarYearUser);

        int databaseSizeBeforeUpdate = calendarYearUserRepository.findAll().size();

        // Update the calendarYearUser
        CalendarYearUser updatedCalendarYearUser = calendarYearUserRepository.findById(calendarYearUser.getId()).get();
        // Disconnect from session so that the updates on updatedCalendarYearUser are not directly saved in db
        em.detach(updatedCalendarYearUser);
        updatedCalendarYearUser
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .isPublicHoliday(UPDATED_IS_PUBLIC_HOLIDAY)
            .start(UPDATED_START)
            .end(UPDATED_END);
        CalendarYearUserDTO calendarYearUserDTO = calendarYearUserMapper.toDto(updatedCalendarYearUser);

        restCalendarYearUserMockMvc.perform(put("/api/calendar-year-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearUserDTO)))
            .andExpect(status().isOk());

        // Validate the CalendarYearUser in the database
        List<CalendarYearUser> calendarYearUserList = calendarYearUserRepository.findAll();
        assertThat(calendarYearUserList).hasSize(databaseSizeBeforeUpdate);
        CalendarYearUser testCalendarYearUser = calendarYearUserList.get(calendarYearUserList.size() - 1);
        assertThat(testCalendarYearUser.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testCalendarYearUser.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCalendarYearUser.isIsPublicHoliday()).isEqualTo(UPDATED_IS_PUBLIC_HOLIDAY);
        assertThat(testCalendarYearUser.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCalendarYearUser.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendarYearUser() throws Exception {
        int databaseSizeBeforeUpdate = calendarYearUserRepository.findAll().size();

        // Create the CalendarYearUser
        CalendarYearUserDTO calendarYearUserDTO = calendarYearUserMapper.toDto(calendarYearUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarYearUserMockMvc.perform(put("/api/calendar-year-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarYearUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarYearUser in the database
        List<CalendarYearUser> calendarYearUserList = calendarYearUserRepository.findAll();
        assertThat(calendarYearUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendarYearUser() throws Exception {
        // Initialize the database
        calendarYearUserRepository.saveAndFlush(calendarYearUser);

        int databaseSizeBeforeDelete = calendarYearUserRepository.findAll().size();

        // Delete the calendarYearUser
        restCalendarYearUserMockMvc.perform(delete("/api/calendar-year-users/{id}", calendarYearUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalendarYearUser> calendarYearUserList = calendarYearUserRepository.findAll();
        assertThat(calendarYearUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

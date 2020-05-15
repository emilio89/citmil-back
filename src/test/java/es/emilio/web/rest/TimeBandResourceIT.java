package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.TimeBand;
import es.emilio.domain.CalendarYearProfesional;
import es.emilio.domain.Company;
import es.emilio.repository.TimeBandRepository;
import es.emilio.service.TimeBandService;
import es.emilio.service.dto.TimeBandDTO;
import es.emilio.service.mapper.TimeBandMapper;
import es.emilio.service.dto.TimeBandCriteria;
import es.emilio.service.TimeBandQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TimeBandResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TimeBandResourceIT {

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TimeBandRepository timeBandRepository;

    @Mock
    private TimeBandRepository timeBandRepositoryMock;

    @Autowired
    private TimeBandMapper timeBandMapper;

    @Mock
    private TimeBandService timeBandServiceMock;

    @Autowired
    private TimeBandService timeBandService;

    @Autowired
    private TimeBandQueryService timeBandQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeBandMockMvc;

    private TimeBand timeBand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeBand createEntity(EntityManager em) {
        TimeBand timeBand = new TimeBand()
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return timeBand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeBand createUpdatedEntity(EntityManager em) {
        TimeBand timeBand = new TimeBand()
            .start(UPDATED_START)
            .end(UPDATED_END);
        return timeBand;
    }

    @BeforeEach
    public void initTest() {
        timeBand = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeBand() throws Exception {
        int databaseSizeBeforeCreate = timeBandRepository.findAll().size();

        // Create the TimeBand
        TimeBandDTO timeBandDTO = timeBandMapper.toDto(timeBand);
        restTimeBandMockMvc.perform(post("/api/time-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeBand in the database
        List<TimeBand> timeBandList = timeBandRepository.findAll();
        assertThat(timeBandList).hasSize(databaseSizeBeforeCreate + 1);
        TimeBand testTimeBand = timeBandList.get(timeBandList.size() - 1);
        assertThat(testTimeBand.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testTimeBand.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createTimeBandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeBandRepository.findAll().size();

        // Create the TimeBand with an existing ID
        timeBand.setId(1L);
        TimeBandDTO timeBandDTO = timeBandMapper.toDto(timeBand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeBandMockMvc.perform(post("/api/time-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeBand in the database
        List<TimeBand> timeBandList = timeBandRepository.findAll();
        assertThat(timeBandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTimeBands() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList
        restTimeBandMockMvc.perform(get("/api/time-bands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeBand.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTimeBandsWithEagerRelationshipsIsEnabled() throws Exception {
        when(timeBandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTimeBandMockMvc.perform(get("/api/time-bands?eagerload=true"))
            .andExpect(status().isOk());

        verify(timeBandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTimeBandsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(timeBandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTimeBandMockMvc.perform(get("/api/time-bands?eagerload=true"))
            .andExpect(status().isOk());

        verify(timeBandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTimeBand() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get the timeBand
        restTimeBandMockMvc.perform(get("/api/time-bands/{id}", timeBand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeBand.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }


    @Test
    @Transactional
    public void getTimeBandsByIdFiltering() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        Long id = timeBand.getId();

        defaultTimeBandShouldBeFound("id.equals=" + id);
        defaultTimeBandShouldNotBeFound("id.notEquals=" + id);

        defaultTimeBandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTimeBandShouldNotBeFound("id.greaterThan=" + id);

        defaultTimeBandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTimeBandShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTimeBandsByStartIsEqualToSomething() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where start equals to DEFAULT_START
        defaultTimeBandShouldBeFound("start.equals=" + DEFAULT_START);

        // Get all the timeBandList where start equals to UPDATED_START
        defaultTimeBandShouldNotBeFound("start.equals=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllTimeBandsByStartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where start not equals to DEFAULT_START
        defaultTimeBandShouldNotBeFound("start.notEquals=" + DEFAULT_START);

        // Get all the timeBandList where start not equals to UPDATED_START
        defaultTimeBandShouldBeFound("start.notEquals=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllTimeBandsByStartIsInShouldWork() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where start in DEFAULT_START or UPDATED_START
        defaultTimeBandShouldBeFound("start.in=" + DEFAULT_START + "," + UPDATED_START);

        // Get all the timeBandList where start equals to UPDATED_START
        defaultTimeBandShouldNotBeFound("start.in=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllTimeBandsByStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where start is not null
        defaultTimeBandShouldBeFound("start.specified=true");

        // Get all the timeBandList where start is null
        defaultTimeBandShouldNotBeFound("start.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimeBandsByEndIsEqualToSomething() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where end equals to DEFAULT_END
        defaultTimeBandShouldBeFound("end.equals=" + DEFAULT_END);

        // Get all the timeBandList where end equals to UPDATED_END
        defaultTimeBandShouldNotBeFound("end.equals=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllTimeBandsByEndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where end not equals to DEFAULT_END
        defaultTimeBandShouldNotBeFound("end.notEquals=" + DEFAULT_END);

        // Get all the timeBandList where end not equals to UPDATED_END
        defaultTimeBandShouldBeFound("end.notEquals=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllTimeBandsByEndIsInShouldWork() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where end in DEFAULT_END or UPDATED_END
        defaultTimeBandShouldBeFound("end.in=" + DEFAULT_END + "," + UPDATED_END);

        // Get all the timeBandList where end equals to UPDATED_END
        defaultTimeBandShouldNotBeFound("end.in=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllTimeBandsByEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        // Get all the timeBandList where end is not null
        defaultTimeBandShouldBeFound("end.specified=true");

        // Get all the timeBandList where end is null
        defaultTimeBandShouldNotBeFound("end.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimeBandsByCalendarYearProfesionalIsEqualToSomething() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);
        CalendarYearProfesional calendarYearProfesional = CalendarYearProfesionalResourceIT.createEntity(em);
        em.persist(calendarYearProfesional);
        em.flush();
        timeBand.addCalendarYearProfesional(calendarYearProfesional);
        timeBandRepository.saveAndFlush(timeBand);
        Long calendarYearProfesionalId = calendarYearProfesional.getId();

        // Get all the timeBandList where calendarYearProfesional equals to calendarYearProfesionalId
        defaultTimeBandShouldBeFound("calendarYearProfesionalId.equals=" + calendarYearProfesionalId);

        // Get all the timeBandList where calendarYearProfesional equals to calendarYearProfesionalId + 1
        defaultTimeBandShouldNotBeFound("calendarYearProfesionalId.equals=" + (calendarYearProfesionalId + 1));
    }


    @Test
    @Transactional
    public void getAllTimeBandsByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        timeBand.setCompany(company);
        timeBandRepository.saveAndFlush(timeBand);
        Long companyId = company.getId();

        // Get all the timeBandList where company equals to companyId
        defaultTimeBandShouldBeFound("companyId.equals=" + companyId);

        // Get all the timeBandList where company equals to companyId + 1
        defaultTimeBandShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTimeBandShouldBeFound(String filter) throws Exception {
        restTimeBandMockMvc.perform(get("/api/time-bands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeBand.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));

        // Check, that the count call also returns 1
        restTimeBandMockMvc.perform(get("/api/time-bands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTimeBandShouldNotBeFound(String filter) throws Exception {
        restTimeBandMockMvc.perform(get("/api/time-bands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTimeBandMockMvc.perform(get("/api/time-bands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTimeBand() throws Exception {
        // Get the timeBand
        restTimeBandMockMvc.perform(get("/api/time-bands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeBand() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        int databaseSizeBeforeUpdate = timeBandRepository.findAll().size();

        // Update the timeBand
        TimeBand updatedTimeBand = timeBandRepository.findById(timeBand.getId()).get();
        // Disconnect from session so that the updates on updatedTimeBand are not directly saved in db
        em.detach(updatedTimeBand);
        updatedTimeBand
            .start(UPDATED_START)
            .end(UPDATED_END);
        TimeBandDTO timeBandDTO = timeBandMapper.toDto(updatedTimeBand);

        restTimeBandMockMvc.perform(put("/api/time-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandDTO)))
            .andExpect(status().isOk());

        // Validate the TimeBand in the database
        List<TimeBand> timeBandList = timeBandRepository.findAll();
        assertThat(timeBandList).hasSize(databaseSizeBeforeUpdate);
        TimeBand testTimeBand = timeBandList.get(timeBandList.size() - 1);
        assertThat(testTimeBand.getStart()).isEqualTo(UPDATED_START);
        assertThat(testTimeBand.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeBand() throws Exception {
        int databaseSizeBeforeUpdate = timeBandRepository.findAll().size();

        // Create the TimeBand
        TimeBandDTO timeBandDTO = timeBandMapper.toDto(timeBand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeBandMockMvc.perform(put("/api/time-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeBandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeBand in the database
        List<TimeBand> timeBandList = timeBandRepository.findAll();
        assertThat(timeBandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeBand() throws Exception {
        // Initialize the database
        timeBandRepository.saveAndFlush(timeBand);

        int databaseSizeBeforeDelete = timeBandRepository.findAll().size();

        // Delete the timeBand
        restTimeBandMockMvc.perform(delete("/api/time-bands/{id}", timeBand.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeBand> timeBandList = timeBandRepository.findAll();
        assertThat(timeBandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

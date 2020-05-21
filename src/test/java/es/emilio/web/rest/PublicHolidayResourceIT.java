package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.PublicHoliday;
import es.emilio.repository.PublicHolidayRepository;
import es.emilio.service.PublicHolidayService;
import es.emilio.service.dto.PublicHolidayDTO;
import es.emilio.service.mapper.PublicHolidayMapper;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PublicHolidayResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PublicHolidayResourceIT {

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    @Autowired
    private PublicHolidayMapper publicHolidayMapper;

    @Autowired
    private PublicHolidayService publicHolidayService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicHolidayMockMvc;

    private PublicHoliday publicHoliday;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicHoliday createEntity(EntityManager em) {
        PublicHoliday publicHoliday = new PublicHoliday()
            .day(DEFAULT_DAY)
            .year(DEFAULT_YEAR)
            .name(DEFAULT_NAME)
            .dni(DEFAULT_DNI);
        return publicHoliday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicHoliday createUpdatedEntity(EntityManager em) {
        PublicHoliday publicHoliday = new PublicHoliday()
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .name(UPDATED_NAME)
            .dni(UPDATED_DNI);
        return publicHoliday;
    }

    @BeforeEach
    public void initTest() {
        publicHoliday = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicHoliday() throws Exception {
        int databaseSizeBeforeCreate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(publicHoliday);
        restPublicHolidayMockMvc.perform(post("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isCreated());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeCreate + 1);
        PublicHoliday testPublicHoliday = publicHolidayList.get(publicHolidayList.size() - 1);
        assertThat(testPublicHoliday.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testPublicHoliday.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPublicHoliday.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublicHoliday.getDni()).isEqualTo(DEFAULT_DNI);
    }

    @Test
    @Transactional
    public void createPublicHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday with an existing ID
        publicHoliday.setId(1L);
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(publicHoliday);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicHolidayMockMvc.perform(post("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPublicHolidays() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList
        restPublicHolidayMockMvc.perform(get("/api/public-holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)));
    }
    
    @Test
    @Transactional
    public void getPublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get the publicHoliday
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/{id}", publicHoliday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicHoliday.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI));
    }

    @Test
    @Transactional
    public void getNonExistingPublicHoliday() throws Exception {
        // Get the publicHoliday
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        int databaseSizeBeforeUpdate = publicHolidayRepository.findAll().size();

        // Update the publicHoliday
        PublicHoliday updatedPublicHoliday = publicHolidayRepository.findById(publicHoliday.getId()).get();
        // Disconnect from session so that the updates on updatedPublicHoliday are not directly saved in db
        em.detach(updatedPublicHoliday);
        updatedPublicHoliday
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            .name(UPDATED_NAME)
            .dni(UPDATED_DNI);
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(updatedPublicHoliday);

        restPublicHolidayMockMvc.perform(put("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isOk());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeUpdate);
        PublicHoliday testPublicHoliday = publicHolidayList.get(publicHolidayList.size() - 1);
        assertThat(testPublicHoliday.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testPublicHoliday.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPublicHoliday.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicHoliday.getDni()).isEqualTo(UPDATED_DNI);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicHoliday() throws Exception {
        int databaseSizeBeforeUpdate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(publicHoliday);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicHolidayMockMvc.perform(put("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        int databaseSizeBeforeDelete = publicHolidayRepository.findAll().size();

        // Delete the publicHoliday
        restPublicHolidayMockMvc.perform(delete("/api/public-holidays/{id}", publicHoliday.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

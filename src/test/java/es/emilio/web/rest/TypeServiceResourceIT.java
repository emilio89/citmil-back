package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.TypeService;
import es.emilio.repository.TypeServiceRepository;
import es.emilio.service.TypeServiceService;
import es.emilio.service.dto.TypeServiceDTO;
import es.emilio.service.mapper.TypeServiceMapper;

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
 * Integration tests for the {@link TypeServiceResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TypeServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION_MINUTES = 1;
    private static final Integer UPDATED_DURATION_MINUTES = 2;

    private static final Integer DEFAULT_MAX_DAY_APPOINTMENT = 1;
    private static final Integer UPDATED_MAX_DAY_APPOINTMENT = 2;

    private static final Integer DEFAULT_MIN_DAY_APPOINTMENT = 1;
    private static final Integer UPDATED_MIN_DAY_APPOINTMENT = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACTIVED = false;
    private static final Boolean UPDATED_ACTIVED = true;

    @Autowired
    private TypeServiceRepository typeServiceRepository;

    @Autowired
    private TypeServiceMapper typeServiceMapper;

    @Autowired
    private TypeServiceService typeServiceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeServiceMockMvc;

    private TypeService typeService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeService createEntity(EntityManager em) {
        TypeService typeService = new TypeService()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .durationMinutes(DEFAULT_DURATION_MINUTES)
            .maxDayAppointment(DEFAULT_MAX_DAY_APPOINTMENT)
            .minDayAppointment(DEFAULT_MIN_DAY_APPOINTMENT)
            .price(DEFAULT_PRICE)
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE)
            .actived(DEFAULT_ACTIVED);
        return typeService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeService createUpdatedEntity(EntityManager em) {
        TypeService typeService = new TypeService()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .durationMinutes(UPDATED_DURATION_MINUTES)
            .maxDayAppointment(UPDATED_MAX_DAY_APPOINTMENT)
            .minDayAppointment(UPDATED_MIN_DAY_APPOINTMENT)
            .price(UPDATED_PRICE)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED);
        return typeService;
    }

    @BeforeEach
    public void initTest() {
        typeService = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeService() throws Exception {
        int databaseSizeBeforeCreate = typeServiceRepository.findAll().size();

        // Create the TypeService
        TypeServiceDTO typeServiceDTO = typeServiceMapper.toDto(typeService);
        restTypeServiceMockMvc.perform(post("/api/type-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeService in the database
        List<TypeService> typeServiceList = typeServiceRepository.findAll();
        assertThat(typeServiceList).hasSize(databaseSizeBeforeCreate + 1);
        TypeService testTypeService = typeServiceList.get(typeServiceList.size() - 1);
        assertThat(testTypeService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTypeService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTypeService.getDurationMinutes()).isEqualTo(DEFAULT_DURATION_MINUTES);
        assertThat(testTypeService.getMaxDayAppointment()).isEqualTo(DEFAULT_MAX_DAY_APPOINTMENT);
        assertThat(testTypeService.getMinDayAppointment()).isEqualTo(DEFAULT_MIN_DAY_APPOINTMENT);
        assertThat(testTypeService.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTypeService.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testTypeService.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testTypeService.isActived()).isEqualTo(DEFAULT_ACTIVED);
    }

    @Test
    @Transactional
    public void createTypeServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeServiceRepository.findAll().size();

        // Create the TypeService with an existing ID
        typeService.setId(1L);
        TypeServiceDTO typeServiceDTO = typeServiceMapper.toDto(typeService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeServiceMockMvc.perform(post("/api/type-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeService in the database
        List<TypeService> typeServiceList = typeServiceRepository.findAll();
        assertThat(typeServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeServiceRepository.findAll().size();
        // set the field null
        typeService.setName(null);

        // Create the TypeService, which fails.
        TypeServiceDTO typeServiceDTO = typeServiceMapper.toDto(typeService);

        restTypeServiceMockMvc.perform(post("/api/type-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServiceDTO)))
            .andExpect(status().isBadRequest());

        List<TypeService> typeServiceList = typeServiceRepository.findAll();
        assertThat(typeServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationMinutesIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeServiceRepository.findAll().size();
        // set the field null
        typeService.setDurationMinutes(null);

        // Create the TypeService, which fails.
        TypeServiceDTO typeServiceDTO = typeServiceMapper.toDto(typeService);

        restTypeServiceMockMvc.perform(post("/api/type-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServiceDTO)))
            .andExpect(status().isBadRequest());

        List<TypeService> typeServiceList = typeServiceRepository.findAll();
        assertThat(typeServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeServices() throws Exception {
        // Initialize the database
        typeServiceRepository.saveAndFlush(typeService);

        // Get all the typeServiceList
        restTypeServiceMockMvc.perform(get("/api/type-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].durationMinutes").value(hasItem(DEFAULT_DURATION_MINUTES)))
            .andExpect(jsonPath("$.[*].maxDayAppointment").value(hasItem(DEFAULT_MAX_DAY_APPOINTMENT)))
            .andExpect(jsonPath("$.[*].minDayAppointment").value(hasItem(DEFAULT_MIN_DAY_APPOINTMENT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTypeService() throws Exception {
        // Initialize the database
        typeServiceRepository.saveAndFlush(typeService);

        // Get the typeService
        restTypeServiceMockMvc.perform(get("/api/type-services/{id}", typeService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.durationMinutes").value(DEFAULT_DURATION_MINUTES))
            .andExpect(jsonPath("$.maxDayAppointment").value(DEFAULT_MAX_DAY_APPOINTMENT))
            .andExpect(jsonPath("$.minDayAppointment").value(DEFAULT_MIN_DAY_APPOINTMENT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.actived").value(DEFAULT_ACTIVED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeService() throws Exception {
        // Get the typeService
        restTypeServiceMockMvc.perform(get("/api/type-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeService() throws Exception {
        // Initialize the database
        typeServiceRepository.saveAndFlush(typeService);

        int databaseSizeBeforeUpdate = typeServiceRepository.findAll().size();

        // Update the typeService
        TypeService updatedTypeService = typeServiceRepository.findById(typeService.getId()).get();
        // Disconnect from session so that the updates on updatedTypeService are not directly saved in db
        em.detach(updatedTypeService);
        updatedTypeService
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .durationMinutes(UPDATED_DURATION_MINUTES)
            .maxDayAppointment(UPDATED_MAX_DAY_APPOINTMENT)
            .minDayAppointment(UPDATED_MIN_DAY_APPOINTMENT)
            .price(UPDATED_PRICE)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED);
        TypeServiceDTO typeServiceDTO = typeServiceMapper.toDto(updatedTypeService);

        restTypeServiceMockMvc.perform(put("/api/type-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServiceDTO)))
            .andExpect(status().isOk());

        // Validate the TypeService in the database
        List<TypeService> typeServiceList = typeServiceRepository.findAll();
        assertThat(typeServiceList).hasSize(databaseSizeBeforeUpdate);
        TypeService testTypeService = typeServiceList.get(typeServiceList.size() - 1);
        assertThat(testTypeService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTypeService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypeService.getDurationMinutes()).isEqualTo(UPDATED_DURATION_MINUTES);
        assertThat(testTypeService.getMaxDayAppointment()).isEqualTo(UPDATED_MAX_DAY_APPOINTMENT);
        assertThat(testTypeService.getMinDayAppointment()).isEqualTo(UPDATED_MIN_DAY_APPOINTMENT);
        assertThat(testTypeService.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTypeService.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testTypeService.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testTypeService.isActived()).isEqualTo(UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeService() throws Exception {
        int databaseSizeBeforeUpdate = typeServiceRepository.findAll().size();

        // Create the TypeService
        TypeServiceDTO typeServiceDTO = typeServiceMapper.toDto(typeService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeServiceMockMvc.perform(put("/api/type-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeService in the database
        List<TypeService> typeServiceList = typeServiceRepository.findAll();
        assertThat(typeServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeService() throws Exception {
        // Initialize the database
        typeServiceRepository.saveAndFlush(typeService);

        int databaseSizeBeforeDelete = typeServiceRepository.findAll().size();

        // Delete the typeService
        restTypeServiceMockMvc.perform(delete("/api/type-services/{id}", typeService.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeService> typeServiceList = typeServiceRepository.findAll();
        assertThat(typeServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

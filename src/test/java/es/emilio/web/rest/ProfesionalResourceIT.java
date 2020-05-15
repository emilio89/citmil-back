package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.Profesional;
import es.emilio.repository.ProfesionalRepository;
import es.emilio.service.ProfesionalService;
import es.emilio.service.dto.ProfesionalDTO;
import es.emilio.service.mapper.ProfesionalMapper;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProfesionalResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfesionalResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_URL_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_IMG_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACTIVED = false;
    private static final Boolean UPDATED_ACTIVED = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Mock
    private ProfesionalRepository profesionalRepositoryMock;

    @Autowired
    private ProfesionalMapper profesionalMapper;

    @Mock
    private ProfesionalService profesionalServiceMock;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfesionalMockMvc;

    private Profesional profesional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profesional createEntity(EntityManager em) {
        Profesional profesional = new Profesional()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .description(DEFAULT_DESCRIPTION)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .birthdate(DEFAULT_BIRTHDATE)
            .urlImg(DEFAULT_URL_IMG)
            .urlImgContentType(DEFAULT_URL_IMG_CONTENT_TYPE)
            .actived(DEFAULT_ACTIVED)
            .deleted(DEFAULT_DELETED);
        return profesional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profesional createUpdatedEntity(EntityManager em) {
        Profesional profesional = new Profesional()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .birthdate(UPDATED_BIRTHDATE)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED)
            .deleted(UPDATED_DELETED);
        return profesional;
    }

    @BeforeEach
    public void initTest() {
        profesional = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfesional() throws Exception {
        int databaseSizeBeforeCreate = profesionalRepository.findAll().size();

        // Create the Profesional
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);
        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isCreated());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeCreate + 1);
        Profesional testProfesional = profesionalList.get(profesionalList.size() - 1);
        assertThat(testProfesional.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProfesional.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProfesional.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProfesional.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfesional.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testProfesional.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfesional.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testProfesional.getUrlImg()).isEqualTo(DEFAULT_URL_IMG);
        assertThat(testProfesional.getUrlImgContentType()).isEqualTo(DEFAULT_URL_IMG_CONTENT_TYPE);
        assertThat(testProfesional.isActived()).isEqualTo(DEFAULT_ACTIVED);
        assertThat(testProfesional.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createProfesionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profesionalRepository.findAll().size();

        // Create the Profesional with an existing ID
        profesional.setId(1L);
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesionalRepository.findAll().size();
        // set the field null
        profesional.setFirstName(null);

        // Create the Profesional, which fails.
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfesionals() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        // Get all the profesionalList
        restProfesionalMockMvc.perform(get("/api/profesionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profesional.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProfesionalsWithEagerRelationshipsIsEnabled() throws Exception {
        when(profesionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfesionalMockMvc.perform(get("/api/profesionals?eagerload=true"))
            .andExpect(status().isOk());

        verify(profesionalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProfesionalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(profesionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfesionalMockMvc.perform(get("/api/profesionals?eagerload=true"))
            .andExpect(status().isOk());

        verify(profesionalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProfesional() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        // Get the profesional
        restProfesionalMockMvc.perform(get("/api/profesionals/{id}", profesional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profesional.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.urlImgContentType").value(DEFAULT_URL_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlImg").value(Base64Utils.encodeToString(DEFAULT_URL_IMG)))
            .andExpect(jsonPath("$.actived").value(DEFAULT_ACTIVED.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProfesional() throws Exception {
        // Get the profesional
        restProfesionalMockMvc.perform(get("/api/profesionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfesional() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        int databaseSizeBeforeUpdate = profesionalRepository.findAll().size();

        // Update the profesional
        Profesional updatedProfesional = profesionalRepository.findById(profesional.getId()).get();
        // Disconnect from session so that the updates on updatedProfesional are not directly saved in db
        em.detach(updatedProfesional);
        updatedProfesional
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .birthdate(UPDATED_BIRTHDATE)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED)
            .deleted(UPDATED_DELETED);
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(updatedProfesional);

        restProfesionalMockMvc.perform(put("/api/profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isOk());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeUpdate);
        Profesional testProfesional = profesionalList.get(profesionalList.size() - 1);
        assertThat(testProfesional.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfesional.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfesional.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProfesional.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfesional.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProfesional.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfesional.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testProfesional.getUrlImg()).isEqualTo(UPDATED_URL_IMG);
        assertThat(testProfesional.getUrlImgContentType()).isEqualTo(UPDATED_URL_IMG_CONTENT_TYPE);
        assertThat(testProfesional.isActived()).isEqualTo(UPDATED_ACTIVED);
        assertThat(testProfesional.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingProfesional() throws Exception {
        int databaseSizeBeforeUpdate = profesionalRepository.findAll().size();

        // Create the Profesional
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesionalMockMvc.perform(put("/api/profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfesional() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        int databaseSizeBeforeDelete = profesionalRepository.findAll().size();

        // Delete the profesional
        restProfesionalMockMvc.perform(delete("/api/profesionals/{id}", profesional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

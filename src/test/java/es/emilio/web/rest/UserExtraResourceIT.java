package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.UserExtra;
import es.emilio.repository.UserExtraRepository;
import es.emilio.service.UserExtraService;
import es.emilio.service.dto.UserExtraDTO;
import es.emilio.service.mapper.UserExtraMapper;

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
 * Integration tests for the {@link UserExtraResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserExtraResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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
    private UserExtraRepository userExtraRepository;

    @Mock
    private UserExtraRepository userExtraRepositoryMock;

    @Autowired
    private UserExtraMapper userExtraMapper;

    @Mock
    private UserExtraService userExtraServiceMock;

    @Autowired
    private UserExtraService userExtraService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .birthdate(DEFAULT_BIRTHDATE)
            .urlImg(DEFAULT_URL_IMG)
            .urlImgContentType(DEFAULT_URL_IMG_CONTENT_TYPE)
            .actived(DEFAULT_ACTIVED)
            .deleted(DEFAULT_DELETED);
        return userExtra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createUpdatedEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .birthdate(UPDATED_BIRTHDATE)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED)
            .deleted(UPDATED_DELETED);
        return userExtra;
    }

    @BeforeEach
    public void initTest() {
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserExtra.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserExtra.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserExtra.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testUserExtra.getUrlImg()).isEqualTo(DEFAULT_URL_IMG);
        assertThat(testUserExtra.getUrlImgContentType()).isEqualTo(DEFAULT_URL_IMG_CONTENT_TYPE);
        assertThat(testUserExtra.isActived()).isEqualTo(DEFAULT_ACTIVED);
        assertThat(testUserExtra.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createUserExtraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // Create the UserExtra with an existing ID
        userExtra.setId(1L);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc.perform(get("/api/user-extras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserExtrasWithEagerRelationshipsIsEnabled() throws Exception {
        when(userExtraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtraMockMvc.perform(get("/api/user-extras?eagerload=true"))
            .andExpect(status().isOk());

        verify(userExtraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserExtrasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userExtraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtraMockMvc.perform(get("/api/user-extras?eagerload=true"))
            .andExpect(status().isOk());

        verify(userExtraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
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
    public void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .birthdate(UPDATED_BIRTHDATE)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED)
            .deleted(UPDATED_DELETED);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(updatedUserExtra);

        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserExtra.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserExtra.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserExtra.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testUserExtra.getUrlImg()).isEqualTo(UPDATED_URL_IMG);
        assertThat(testUserExtra.getUrlImgContentType()).isEqualTo(UPDATED_URL_IMG_CONTENT_TYPE);
        assertThat(testUserExtra.isActived()).isEqualTo(UPDATED_ACTIVED);
        assertThat(testUserExtra.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Delete the userExtra
        restUserExtraMockMvc.perform(delete("/api/user-extras/{id}", userExtra.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

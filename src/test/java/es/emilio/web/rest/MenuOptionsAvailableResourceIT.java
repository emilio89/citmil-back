package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.MenuOptionsAvailable;
import es.emilio.domain.Company;
import es.emilio.repository.MenuOptionsAvailableRepository;
import es.emilio.service.MenuOptionsAvailableService;
import es.emilio.service.dto.MenuOptionsAvailableDTO;
import es.emilio.service.mapper.MenuOptionsAvailableMapper;
import es.emilio.service.dto.MenuOptionsAvailableCriteria;
import es.emilio.service.MenuOptionsAvailableQueryService;

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
 * Integration tests for the {@link MenuOptionsAvailableResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MenuOptionsAvailableResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_URL_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_IMG_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACTIVED = false;
    private static final Boolean UPDATED_ACTIVED = true;

    @Autowired
    private MenuOptionsAvailableRepository menuOptionsAvailableRepository;

    @Autowired
    private MenuOptionsAvailableMapper menuOptionsAvailableMapper;

    @Autowired
    private MenuOptionsAvailableService menuOptionsAvailableService;

    @Autowired
    private MenuOptionsAvailableQueryService menuOptionsAvailableQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuOptionsAvailableMockMvc;

    private MenuOptionsAvailable menuOptionsAvailable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuOptionsAvailable createEntity(EntityManager em) {
        MenuOptionsAvailable menuOptionsAvailable = new MenuOptionsAvailable()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .urlImg(DEFAULT_URL_IMG)
            .urlImgContentType(DEFAULT_URL_IMG_CONTENT_TYPE)
            .actived(DEFAULT_ACTIVED);
        return menuOptionsAvailable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuOptionsAvailable createUpdatedEntity(EntityManager em) {
        MenuOptionsAvailable menuOptionsAvailable = new MenuOptionsAvailable()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED);
        return menuOptionsAvailable;
    }

    @BeforeEach
    public void initTest() {
        menuOptionsAvailable = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenuOptionsAvailable() throws Exception {
        int databaseSizeBeforeCreate = menuOptionsAvailableRepository.findAll().size();

        // Create the MenuOptionsAvailable
        MenuOptionsAvailableDTO menuOptionsAvailableDTO = menuOptionsAvailableMapper.toDto(menuOptionsAvailable);
        restMenuOptionsAvailableMockMvc.perform(post("/api/menu-options-availables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuOptionsAvailableDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuOptionsAvailable in the database
        List<MenuOptionsAvailable> menuOptionsAvailableList = menuOptionsAvailableRepository.findAll();
        assertThat(menuOptionsAvailableList).hasSize(databaseSizeBeforeCreate + 1);
        MenuOptionsAvailable testMenuOptionsAvailable = menuOptionsAvailableList.get(menuOptionsAvailableList.size() - 1);
        assertThat(testMenuOptionsAvailable.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMenuOptionsAvailable.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMenuOptionsAvailable.getUrlImg()).isEqualTo(DEFAULT_URL_IMG);
        assertThat(testMenuOptionsAvailable.getUrlImgContentType()).isEqualTo(DEFAULT_URL_IMG_CONTENT_TYPE);
        assertThat(testMenuOptionsAvailable.isActived()).isEqualTo(DEFAULT_ACTIVED);
    }

    @Test
    @Transactional
    public void createMenuOptionsAvailableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuOptionsAvailableRepository.findAll().size();

        // Create the MenuOptionsAvailable with an existing ID
        menuOptionsAvailable.setId(1L);
        MenuOptionsAvailableDTO menuOptionsAvailableDTO = menuOptionsAvailableMapper.toDto(menuOptionsAvailable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuOptionsAvailableMockMvc.perform(post("/api/menu-options-availables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuOptionsAvailableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuOptionsAvailable in the database
        List<MenuOptionsAvailable> menuOptionsAvailableList = menuOptionsAvailableRepository.findAll();
        assertThat(menuOptionsAvailableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMenuOptionsAvailables() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList
        restMenuOptionsAvailableMockMvc.perform(get("/api/menu-options-availables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuOptionsAvailable.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMenuOptionsAvailable() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get the menuOptionsAvailable
        restMenuOptionsAvailableMockMvc.perform(get("/api/menu-options-availables/{id}", menuOptionsAvailable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuOptionsAvailable.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.urlImgContentType").value(DEFAULT_URL_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlImg").value(Base64Utils.encodeToString(DEFAULT_URL_IMG)))
            .andExpect(jsonPath("$.actived").value(DEFAULT_ACTIVED.booleanValue()));
    }


    @Test
    @Transactional
    public void getMenuOptionsAvailablesByIdFiltering() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        Long id = menuOptionsAvailable.getId();

        defaultMenuOptionsAvailableShouldBeFound("id.equals=" + id);
        defaultMenuOptionsAvailableShouldNotBeFound("id.notEquals=" + id);

        defaultMenuOptionsAvailableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMenuOptionsAvailableShouldNotBeFound("id.greaterThan=" + id);

        defaultMenuOptionsAvailableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMenuOptionsAvailableShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where title equals to DEFAULT_TITLE
        defaultMenuOptionsAvailableShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the menuOptionsAvailableList where title equals to UPDATED_TITLE
        defaultMenuOptionsAvailableShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where title not equals to DEFAULT_TITLE
        defaultMenuOptionsAvailableShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the menuOptionsAvailableList where title not equals to UPDATED_TITLE
        defaultMenuOptionsAvailableShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultMenuOptionsAvailableShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the menuOptionsAvailableList where title equals to UPDATED_TITLE
        defaultMenuOptionsAvailableShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where title is not null
        defaultMenuOptionsAvailableShouldBeFound("title.specified=true");

        // Get all the menuOptionsAvailableList where title is null
        defaultMenuOptionsAvailableShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByTitleContainsSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where title contains DEFAULT_TITLE
        defaultMenuOptionsAvailableShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the menuOptionsAvailableList where title contains UPDATED_TITLE
        defaultMenuOptionsAvailableShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where title does not contain DEFAULT_TITLE
        defaultMenuOptionsAvailableShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the menuOptionsAvailableList where title does not contain UPDATED_TITLE
        defaultMenuOptionsAvailableShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where description equals to DEFAULT_DESCRIPTION
        defaultMenuOptionsAvailableShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the menuOptionsAvailableList where description equals to UPDATED_DESCRIPTION
        defaultMenuOptionsAvailableShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where description not equals to DEFAULT_DESCRIPTION
        defaultMenuOptionsAvailableShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the menuOptionsAvailableList where description not equals to UPDATED_DESCRIPTION
        defaultMenuOptionsAvailableShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMenuOptionsAvailableShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the menuOptionsAvailableList where description equals to UPDATED_DESCRIPTION
        defaultMenuOptionsAvailableShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where description is not null
        defaultMenuOptionsAvailableShouldBeFound("description.specified=true");

        // Get all the menuOptionsAvailableList where description is null
        defaultMenuOptionsAvailableShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where description contains DEFAULT_DESCRIPTION
        defaultMenuOptionsAvailableShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the menuOptionsAvailableList where description contains UPDATED_DESCRIPTION
        defaultMenuOptionsAvailableShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where description does not contain DEFAULT_DESCRIPTION
        defaultMenuOptionsAvailableShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the menuOptionsAvailableList where description does not contain UPDATED_DESCRIPTION
        defaultMenuOptionsAvailableShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByActivedIsEqualToSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where actived equals to DEFAULT_ACTIVED
        defaultMenuOptionsAvailableShouldBeFound("actived.equals=" + DEFAULT_ACTIVED);

        // Get all the menuOptionsAvailableList where actived equals to UPDATED_ACTIVED
        defaultMenuOptionsAvailableShouldNotBeFound("actived.equals=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByActivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where actived not equals to DEFAULT_ACTIVED
        defaultMenuOptionsAvailableShouldNotBeFound("actived.notEquals=" + DEFAULT_ACTIVED);

        // Get all the menuOptionsAvailableList where actived not equals to UPDATED_ACTIVED
        defaultMenuOptionsAvailableShouldBeFound("actived.notEquals=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByActivedIsInShouldWork() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where actived in DEFAULT_ACTIVED or UPDATED_ACTIVED
        defaultMenuOptionsAvailableShouldBeFound("actived.in=" + DEFAULT_ACTIVED + "," + UPDATED_ACTIVED);

        // Get all the menuOptionsAvailableList where actived equals to UPDATED_ACTIVED
        defaultMenuOptionsAvailableShouldNotBeFound("actived.in=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByActivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        // Get all the menuOptionsAvailableList where actived is not null
        defaultMenuOptionsAvailableShouldBeFound("actived.specified=true");

        // Get all the menuOptionsAvailableList where actived is null
        defaultMenuOptionsAvailableShouldNotBeFound("actived.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenuOptionsAvailablesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        menuOptionsAvailable.setCompany(company);
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);
        Long companyId = company.getId();

        // Get all the menuOptionsAvailableList where company equals to companyId
        defaultMenuOptionsAvailableShouldBeFound("companyId.equals=" + companyId);

        // Get all the menuOptionsAvailableList where company equals to companyId + 1
        defaultMenuOptionsAvailableShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMenuOptionsAvailableShouldBeFound(String filter) throws Exception {
        restMenuOptionsAvailableMockMvc.perform(get("/api/menu-options-availables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuOptionsAvailable.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())));

        // Check, that the count call also returns 1
        restMenuOptionsAvailableMockMvc.perform(get("/api/menu-options-availables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMenuOptionsAvailableShouldNotBeFound(String filter) throws Exception {
        restMenuOptionsAvailableMockMvc.perform(get("/api/menu-options-availables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMenuOptionsAvailableMockMvc.perform(get("/api/menu-options-availables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMenuOptionsAvailable() throws Exception {
        // Get the menuOptionsAvailable
        restMenuOptionsAvailableMockMvc.perform(get("/api/menu-options-availables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenuOptionsAvailable() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        int databaseSizeBeforeUpdate = menuOptionsAvailableRepository.findAll().size();

        // Update the menuOptionsAvailable
        MenuOptionsAvailable updatedMenuOptionsAvailable = menuOptionsAvailableRepository.findById(menuOptionsAvailable.getId()).get();
        // Disconnect from session so that the updates on updatedMenuOptionsAvailable are not directly saved in db
        em.detach(updatedMenuOptionsAvailable);
        updatedMenuOptionsAvailable
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED);
        MenuOptionsAvailableDTO menuOptionsAvailableDTO = menuOptionsAvailableMapper.toDto(updatedMenuOptionsAvailable);

        restMenuOptionsAvailableMockMvc.perform(put("/api/menu-options-availables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuOptionsAvailableDTO)))
            .andExpect(status().isOk());

        // Validate the MenuOptionsAvailable in the database
        List<MenuOptionsAvailable> menuOptionsAvailableList = menuOptionsAvailableRepository.findAll();
        assertThat(menuOptionsAvailableList).hasSize(databaseSizeBeforeUpdate);
        MenuOptionsAvailable testMenuOptionsAvailable = menuOptionsAvailableList.get(menuOptionsAvailableList.size() - 1);
        assertThat(testMenuOptionsAvailable.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMenuOptionsAvailable.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenuOptionsAvailable.getUrlImg()).isEqualTo(UPDATED_URL_IMG);
        assertThat(testMenuOptionsAvailable.getUrlImgContentType()).isEqualTo(UPDATED_URL_IMG_CONTENT_TYPE);
        assertThat(testMenuOptionsAvailable.isActived()).isEqualTo(UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void updateNonExistingMenuOptionsAvailable() throws Exception {
        int databaseSizeBeforeUpdate = menuOptionsAvailableRepository.findAll().size();

        // Create the MenuOptionsAvailable
        MenuOptionsAvailableDTO menuOptionsAvailableDTO = menuOptionsAvailableMapper.toDto(menuOptionsAvailable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuOptionsAvailableMockMvc.perform(put("/api/menu-options-availables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuOptionsAvailableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuOptionsAvailable in the database
        List<MenuOptionsAvailable> menuOptionsAvailableList = menuOptionsAvailableRepository.findAll();
        assertThat(menuOptionsAvailableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMenuOptionsAvailable() throws Exception {
        // Initialize the database
        menuOptionsAvailableRepository.saveAndFlush(menuOptionsAvailable);

        int databaseSizeBeforeDelete = menuOptionsAvailableRepository.findAll().size();

        // Delete the menuOptionsAvailable
        restMenuOptionsAvailableMockMvc.perform(delete("/api/menu-options-availables/{id}", menuOptionsAvailable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MenuOptionsAvailable> menuOptionsAvailableList = menuOptionsAvailableRepository.findAll();
        assertThat(menuOptionsAvailableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

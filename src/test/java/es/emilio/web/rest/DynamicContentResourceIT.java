package es.emilio.web.rest;

import es.emilio.CitmilApp;
import es.emilio.domain.DynamicContent;
import es.emilio.domain.Company;
import es.emilio.repository.DynamicContentRepository;
import es.emilio.service.DynamicContentService;
import es.emilio.service.dto.DynamicContentDTO;
import es.emilio.service.mapper.DynamicContentMapper;
import es.emilio.service.dto.DynamicContentCriteria;
import es.emilio.service.DynamicContentQueryService;

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
 * Integration tests for the {@link DynamicContentResource} REST controller.
 */
@SpringBootTest(classes = CitmilApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DynamicContentResourceIT {

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
    private DynamicContentRepository dynamicContentRepository;

    @Autowired
    private DynamicContentMapper dynamicContentMapper;

    @Autowired
    private DynamicContentService dynamicContentService;

    @Autowired
    private DynamicContentQueryService dynamicContentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDynamicContentMockMvc;

    private DynamicContent dynamicContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicContent createEntity(EntityManager em) {
        DynamicContent dynamicContent = new DynamicContent()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .urlImg(DEFAULT_URL_IMG)
            .urlImgContentType(DEFAULT_URL_IMG_CONTENT_TYPE)
            .actived(DEFAULT_ACTIVED);
        return dynamicContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicContent createUpdatedEntity(EntityManager em) {
        DynamicContent dynamicContent = new DynamicContent()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED);
        return dynamicContent;
    }

    @BeforeEach
    public void initTest() {
        dynamicContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createDynamicContent() throws Exception {
        int databaseSizeBeforeCreate = dynamicContentRepository.findAll().size();

        // Create the DynamicContent
        DynamicContentDTO dynamicContentDTO = dynamicContentMapper.toDto(dynamicContent);
        restDynamicContentMockMvc.perform(post("/api/dynamic-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicContentDTO)))
            .andExpect(status().isCreated());

        // Validate the DynamicContent in the database
        List<DynamicContent> dynamicContentList = dynamicContentRepository.findAll();
        assertThat(dynamicContentList).hasSize(databaseSizeBeforeCreate + 1);
        DynamicContent testDynamicContent = dynamicContentList.get(dynamicContentList.size() - 1);
        assertThat(testDynamicContent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDynamicContent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDynamicContent.getUrlImg()).isEqualTo(DEFAULT_URL_IMG);
        assertThat(testDynamicContent.getUrlImgContentType()).isEqualTo(DEFAULT_URL_IMG_CONTENT_TYPE);
        assertThat(testDynamicContent.isActived()).isEqualTo(DEFAULT_ACTIVED);
    }

    @Test
    @Transactional
    public void createDynamicContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dynamicContentRepository.findAll().size();

        // Create the DynamicContent with an existing ID
        dynamicContent.setId(1L);
        DynamicContentDTO dynamicContentDTO = dynamicContentMapper.toDto(dynamicContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDynamicContentMockMvc.perform(post("/api/dynamic-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicContent in the database
        List<DynamicContent> dynamicContentList = dynamicContentRepository.findAll();
        assertThat(dynamicContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDynamicContents() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList
        restDynamicContentMockMvc.perform(get("/api/dynamic-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dynamicContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDynamicContent() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get the dynamicContent
        restDynamicContentMockMvc.perform(get("/api/dynamic-contents/{id}", dynamicContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dynamicContent.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.urlImgContentType").value(DEFAULT_URL_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlImg").value(Base64Utils.encodeToString(DEFAULT_URL_IMG)))
            .andExpect(jsonPath("$.actived").value(DEFAULT_ACTIVED.booleanValue()));
    }


    @Test
    @Transactional
    public void getDynamicContentsByIdFiltering() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        Long id = dynamicContent.getId();

        defaultDynamicContentShouldBeFound("id.equals=" + id);
        defaultDynamicContentShouldNotBeFound("id.notEquals=" + id);

        defaultDynamicContentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDynamicContentShouldNotBeFound("id.greaterThan=" + id);

        defaultDynamicContentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDynamicContentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDynamicContentsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where title equals to DEFAULT_TITLE
        defaultDynamicContentShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the dynamicContentList where title equals to UPDATED_TITLE
        defaultDynamicContentShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where title not equals to DEFAULT_TITLE
        defaultDynamicContentShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the dynamicContentList where title not equals to UPDATED_TITLE
        defaultDynamicContentShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultDynamicContentShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the dynamicContentList where title equals to UPDATED_TITLE
        defaultDynamicContentShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where title is not null
        defaultDynamicContentShouldBeFound("title.specified=true");

        // Get all the dynamicContentList where title is null
        defaultDynamicContentShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllDynamicContentsByTitleContainsSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where title contains DEFAULT_TITLE
        defaultDynamicContentShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the dynamicContentList where title contains UPDATED_TITLE
        defaultDynamicContentShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where title does not contain DEFAULT_TITLE
        defaultDynamicContentShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the dynamicContentList where title does not contain UPDATED_TITLE
        defaultDynamicContentShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllDynamicContentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where description equals to DEFAULT_DESCRIPTION
        defaultDynamicContentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the dynamicContentList where description equals to UPDATED_DESCRIPTION
        defaultDynamicContentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where description not equals to DEFAULT_DESCRIPTION
        defaultDynamicContentShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the dynamicContentList where description not equals to UPDATED_DESCRIPTION
        defaultDynamicContentShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDynamicContentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the dynamicContentList where description equals to UPDATED_DESCRIPTION
        defaultDynamicContentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where description is not null
        defaultDynamicContentShouldBeFound("description.specified=true");

        // Get all the dynamicContentList where description is null
        defaultDynamicContentShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllDynamicContentsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where description contains DEFAULT_DESCRIPTION
        defaultDynamicContentShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the dynamicContentList where description contains UPDATED_DESCRIPTION
        defaultDynamicContentShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where description does not contain DEFAULT_DESCRIPTION
        defaultDynamicContentShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the dynamicContentList where description does not contain UPDATED_DESCRIPTION
        defaultDynamicContentShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllDynamicContentsByActivedIsEqualToSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where actived equals to DEFAULT_ACTIVED
        defaultDynamicContentShouldBeFound("actived.equals=" + DEFAULT_ACTIVED);

        // Get all the dynamicContentList where actived equals to UPDATED_ACTIVED
        defaultDynamicContentShouldNotBeFound("actived.equals=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByActivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where actived not equals to DEFAULT_ACTIVED
        defaultDynamicContentShouldNotBeFound("actived.notEquals=" + DEFAULT_ACTIVED);

        // Get all the dynamicContentList where actived not equals to UPDATED_ACTIVED
        defaultDynamicContentShouldBeFound("actived.notEquals=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByActivedIsInShouldWork() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where actived in DEFAULT_ACTIVED or UPDATED_ACTIVED
        defaultDynamicContentShouldBeFound("actived.in=" + DEFAULT_ACTIVED + "," + UPDATED_ACTIVED);

        // Get all the dynamicContentList where actived equals to UPDATED_ACTIVED
        defaultDynamicContentShouldNotBeFound("actived.in=" + UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByActivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        // Get all the dynamicContentList where actived is not null
        defaultDynamicContentShouldBeFound("actived.specified=true");

        // Get all the dynamicContentList where actived is null
        defaultDynamicContentShouldNotBeFound("actived.specified=false");
    }

    @Test
    @Transactional
    public void getAllDynamicContentsByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        dynamicContent.setCompany(company);
        dynamicContentRepository.saveAndFlush(dynamicContent);
        Long companyId = company.getId();

        // Get all the dynamicContentList where company equals to companyId
        defaultDynamicContentShouldBeFound("companyId.equals=" + companyId);

        // Get all the dynamicContentList where company equals to companyId + 1
        defaultDynamicContentShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDynamicContentShouldBeFound(String filter) throws Exception {
        restDynamicContentMockMvc.perform(get("/api/dynamic-contents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dynamicContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].urlImgContentType").value(hasItem(DEFAULT_URL_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_IMG))))
            .andExpect(jsonPath("$.[*].actived").value(hasItem(DEFAULT_ACTIVED.booleanValue())));

        // Check, that the count call also returns 1
        restDynamicContentMockMvc.perform(get("/api/dynamic-contents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDynamicContentShouldNotBeFound(String filter) throws Exception {
        restDynamicContentMockMvc.perform(get("/api/dynamic-contents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDynamicContentMockMvc.perform(get("/api/dynamic-contents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDynamicContent() throws Exception {
        // Get the dynamicContent
        restDynamicContentMockMvc.perform(get("/api/dynamic-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDynamicContent() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        int databaseSizeBeforeUpdate = dynamicContentRepository.findAll().size();

        // Update the dynamicContent
        DynamicContent updatedDynamicContent = dynamicContentRepository.findById(dynamicContent.getId()).get();
        // Disconnect from session so that the updates on updatedDynamicContent are not directly saved in db
        em.detach(updatedDynamicContent);
        updatedDynamicContent
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .urlImg(UPDATED_URL_IMG)
            .urlImgContentType(UPDATED_URL_IMG_CONTENT_TYPE)
            .actived(UPDATED_ACTIVED);
        DynamicContentDTO dynamicContentDTO = dynamicContentMapper.toDto(updatedDynamicContent);

        restDynamicContentMockMvc.perform(put("/api/dynamic-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicContentDTO)))
            .andExpect(status().isOk());

        // Validate the DynamicContent in the database
        List<DynamicContent> dynamicContentList = dynamicContentRepository.findAll();
        assertThat(dynamicContentList).hasSize(databaseSizeBeforeUpdate);
        DynamicContent testDynamicContent = dynamicContentList.get(dynamicContentList.size() - 1);
        assertThat(testDynamicContent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDynamicContent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDynamicContent.getUrlImg()).isEqualTo(UPDATED_URL_IMG);
        assertThat(testDynamicContent.getUrlImgContentType()).isEqualTo(UPDATED_URL_IMG_CONTENT_TYPE);
        assertThat(testDynamicContent.isActived()).isEqualTo(UPDATED_ACTIVED);
    }

    @Test
    @Transactional
    public void updateNonExistingDynamicContent() throws Exception {
        int databaseSizeBeforeUpdate = dynamicContentRepository.findAll().size();

        // Create the DynamicContent
        DynamicContentDTO dynamicContentDTO = dynamicContentMapper.toDto(dynamicContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDynamicContentMockMvc.perform(put("/api/dynamic-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicContent in the database
        List<DynamicContent> dynamicContentList = dynamicContentRepository.findAll();
        assertThat(dynamicContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDynamicContent() throws Exception {
        // Initialize the database
        dynamicContentRepository.saveAndFlush(dynamicContent);

        int databaseSizeBeforeDelete = dynamicContentRepository.findAll().size();

        // Delete the dynamicContent
        restDynamicContentMockMvc.perform(delete("/api/dynamic-contents/{id}", dynamicContent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DynamicContent> dynamicContentList = dynamicContentRepository.findAll();
        assertThat(dynamicContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

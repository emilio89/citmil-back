package es.emilio.web.rest;

import es.emilio.service.DynamicContentService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.DynamicContentDTO;
import es.emilio.service.dto.DynamicContentCriteria;
import es.emilio.service.DynamicContentQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link es.emilio.domain.DynamicContent}.
 */
@RestController
@RequestMapping("/api")
public class DynamicContentResource {

    private final Logger log = LoggerFactory.getLogger(DynamicContentResource.class);

    private static final String ENTITY_NAME = "dynamicContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DynamicContentService dynamicContentService;

    private final DynamicContentQueryService dynamicContentQueryService;

    public DynamicContentResource(DynamicContentService dynamicContentService, DynamicContentQueryService dynamicContentQueryService) {
        this.dynamicContentService = dynamicContentService;
        this.dynamicContentQueryService = dynamicContentQueryService;
    }

    /**
     * {@code POST  /dynamic-contents} : Create a new dynamicContent.
     *
     * @param dynamicContentDTO the dynamicContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dynamicContentDTO, or with status {@code 400 (Bad Request)} if the dynamicContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dynamic-contents")
    public ResponseEntity<DynamicContentDTO> createDynamicContent(@RequestBody DynamicContentDTO dynamicContentDTO) throws URISyntaxException {
        log.debug("REST request to save DynamicContent : {}", dynamicContentDTO);
        if (dynamicContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new dynamicContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DynamicContentDTO result = dynamicContentService.save(dynamicContentDTO);
        return ResponseEntity.created(new URI("/api/dynamic-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dynamic-contents} : Updates an existing dynamicContent.
     *
     * @param dynamicContentDTO the dynamicContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dynamicContentDTO,
     * or with status {@code 400 (Bad Request)} if the dynamicContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dynamicContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dynamic-contents")
    public ResponseEntity<DynamicContentDTO> updateDynamicContent(@RequestBody DynamicContentDTO dynamicContentDTO) throws URISyntaxException {
        log.debug("REST request to update DynamicContent : {}", dynamicContentDTO);
        if (dynamicContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DynamicContentDTO result = dynamicContentService.save(dynamicContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dynamicContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dynamic-contents} : get all the dynamicContents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dynamicContents in body.
     */
    @GetMapping("/dynamic-contents")
    public ResponseEntity<List<DynamicContentDTO>> getAllDynamicContents(DynamicContentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DynamicContents by criteria: {}", criteria);
        Page<DynamicContentDTO> page = dynamicContentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dynamic-contents/count} : count all the dynamicContents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dynamic-contents/count")
    public ResponseEntity<Long> countDynamicContents(DynamicContentCriteria criteria) {
        log.debug("REST request to count DynamicContents by criteria: {}", criteria);
        return ResponseEntity.ok().body(dynamicContentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dynamic-contents/:id} : get the "id" dynamicContent.
     *
     * @param id the id of the dynamicContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dynamicContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dynamic-contents/{id}")
    public ResponseEntity<DynamicContentDTO> getDynamicContent(@PathVariable Long id) {
        log.debug("REST request to get DynamicContent : {}", id);
        Optional<DynamicContentDTO> dynamicContentDTO = dynamicContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dynamicContentDTO);
    }

    /**
     * {@code DELETE  /dynamic-contents/:id} : delete the "id" dynamicContent.
     *
     * @param id the id of the dynamicContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dynamic-contents/{id}")
    public ResponseEntity<Void> deleteDynamicContent(@PathVariable Long id) {
        log.debug("REST request to delete DynamicContent : {}", id);
        dynamicContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

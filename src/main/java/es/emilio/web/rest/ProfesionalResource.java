package es.emilio.web.rest;

import es.emilio.service.ProfesionalService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.ProfesionalDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link es.emilio.domain.Profesional}.
 */
@RestController
@RequestMapping("/api")
public class ProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(ProfesionalResource.class);

    private static final String ENTITY_NAME = "profesional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfesionalService profesionalService;

    public ProfesionalResource(ProfesionalService profesionalService) {
        this.profesionalService = profesionalService;
    }

    /**
     * {@code POST  /profesionals} : Create a new profesional.
     *
     * @param profesionalDTO the profesionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profesionalDTO, or with status {@code 400 (Bad Request)} if the profesional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profesionals")
    public ResponseEntity<ProfesionalDTO> createProfesional(@Valid @RequestBody ProfesionalDTO profesionalDTO) throws URISyntaxException {
        log.debug("REST request to save Profesional : {}", profesionalDTO);
        if (profesionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new profesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfesionalDTO result = profesionalService.save(profesionalDTO);
        return ResponseEntity.created(new URI("/api/profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profesionals} : Updates an existing profesional.
     *
     * @param profesionalDTO the profesionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profesionalDTO,
     * or with status {@code 400 (Bad Request)} if the profesionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profesionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profesionals")
    public ResponseEntity<ProfesionalDTO> updateProfesional(@Valid @RequestBody ProfesionalDTO profesionalDTO) throws URISyntaxException {
        log.debug("REST request to update Profesional : {}", profesionalDTO);
        if (profesionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfesionalDTO result = profesionalService.save(profesionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profesionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profesionals} : get all the profesionals.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profesionals in body.
     */
    @GetMapping("/profesionals")
    public ResponseEntity<List<ProfesionalDTO>> getAllProfesionals(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Profesionals");
        Page<ProfesionalDTO> page;
        if (eagerload) {
            page = profesionalService.findAllWithEagerRelationships(pageable);
        } else {
            page = profesionalService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profesionals/:id} : get the "id" profesional.
     *
     * @param id the id of the profesionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profesionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profesionals/{id}")
    public ResponseEntity<ProfesionalDTO> getProfesional(@PathVariable Long id) {
        log.debug("REST request to get Profesional : {}", id);
        Optional<ProfesionalDTO> profesionalDTO = profesionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profesionalDTO);
    }

    /**
     * {@code DELETE  /profesionals/:id} : delete the "id" profesional.
     *
     * @param id the id of the profesionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profesionals/{id}")
    public ResponseEntity<Void> deleteProfesional(@PathVariable Long id) {
        log.debug("REST request to delete Profesional : {}", id);
        profesionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

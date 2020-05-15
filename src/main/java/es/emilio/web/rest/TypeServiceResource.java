package es.emilio.web.rest;

import es.emilio.service.TypeServiceService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.TypeServiceDTO;

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
 * REST controller for managing {@link es.emilio.domain.TypeService}.
 */
@RestController
@RequestMapping("/api")
public class TypeServiceResource {

    private final Logger log = LoggerFactory.getLogger(TypeServiceResource.class);

    private static final String ENTITY_NAME = "typeService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeServiceService typeServiceService;

    public TypeServiceResource(TypeServiceService typeServiceService) {
        this.typeServiceService = typeServiceService;
    }

    /**
     * {@code POST  /type-services} : Create a new typeService.
     *
     * @param typeServiceDTO the typeServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeServiceDTO, or with status {@code 400 (Bad Request)} if the typeService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-services")
    public ResponseEntity<TypeServiceDTO> createTypeService(@Valid @RequestBody TypeServiceDTO typeServiceDTO) throws URISyntaxException {
        log.debug("REST request to save TypeService : {}", typeServiceDTO);
        if (typeServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeServiceDTO result = typeServiceService.save(typeServiceDTO);
        return ResponseEntity.created(new URI("/api/type-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-services} : Updates an existing typeService.
     *
     * @param typeServiceDTO the typeServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeServiceDTO,
     * or with status {@code 400 (Bad Request)} if the typeServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-services")
    public ResponseEntity<TypeServiceDTO> updateTypeService(@Valid @RequestBody TypeServiceDTO typeServiceDTO) throws URISyntaxException {
        log.debug("REST request to update TypeService : {}", typeServiceDTO);
        if (typeServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeServiceDTO result = typeServiceService.save(typeServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-services} : get all the typeServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeServices in body.
     */
    @GetMapping("/type-services")
    public ResponseEntity<List<TypeServiceDTO>> getAllTypeServices(Pageable pageable) {
        log.debug("REST request to get a page of TypeServices");
        Page<TypeServiceDTO> page = typeServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-services/:id} : get the "id" typeService.
     *
     * @param id the id of the typeServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-services/{id}")
    public ResponseEntity<TypeServiceDTO> getTypeService(@PathVariable Long id) {
        log.debug("REST request to get TypeService : {}", id);
        Optional<TypeServiceDTO> typeServiceDTO = typeServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeServiceDTO);
    }

    /**
     * {@code DELETE  /type-services/:id} : delete the "id" typeService.
     *
     * @param id the id of the typeServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-services/{id}")
    public ResponseEntity<Void> deleteTypeService(@PathVariable Long id) {
        log.debug("REST request to delete TypeService : {}", id);
        typeServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

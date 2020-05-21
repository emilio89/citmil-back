package es.emilio.web.rest;

import es.emilio.service.MenuOptionsAvailableService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.MenuOptionsAvailableDTO;
import es.emilio.service.dto.MenuOptionsAvailableCriteria;
import es.emilio.service.MenuOptionsAvailableQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link es.emilio.domain.MenuOptionsAvailable}.
 */
@RestController
@RequestMapping("/api")
public class MenuOptionsAvailableResource {

    private final Logger log = LoggerFactory.getLogger(MenuOptionsAvailableResource.class);

    private static final String ENTITY_NAME = "menuOptionsAvailable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenuOptionsAvailableService menuOptionsAvailableService;

    private final MenuOptionsAvailableQueryService menuOptionsAvailableQueryService;

    public MenuOptionsAvailableResource(MenuOptionsAvailableService menuOptionsAvailableService, MenuOptionsAvailableQueryService menuOptionsAvailableQueryService) {
        this.menuOptionsAvailableService = menuOptionsAvailableService;
        this.menuOptionsAvailableQueryService = menuOptionsAvailableQueryService;
    }

    /**
     * {@code POST  /menu-options-availables} : Create a new menuOptionsAvailable.
     *
     * @param menuOptionsAvailableDTO the menuOptionsAvailableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menuOptionsAvailableDTO, or with status {@code 400 (Bad Request)} if the menuOptionsAvailable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/menu-options-availables")
    public ResponseEntity<MenuOptionsAvailableDTO> createMenuOptionsAvailable(@RequestBody MenuOptionsAvailableDTO menuOptionsAvailableDTO) throws URISyntaxException {
        log.debug("REST request to save MenuOptionsAvailable : {}", menuOptionsAvailableDTO);
        if (menuOptionsAvailableDTO.getId() != null) {
            throw new BadRequestAlertException("A new menuOptionsAvailable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenuOptionsAvailableDTO result = menuOptionsAvailableService.save(menuOptionsAvailableDTO);
        return ResponseEntity.created(new URI("/api/menu-options-availables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /menu-options-availables} : Updates an existing menuOptionsAvailable.
     *
     * @param menuOptionsAvailableDTO the menuOptionsAvailableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuOptionsAvailableDTO,
     * or with status {@code 400 (Bad Request)} if the menuOptionsAvailableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menuOptionsAvailableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/menu-options-availables")
    public ResponseEntity<MenuOptionsAvailableDTO> updateMenuOptionsAvailable(@RequestBody MenuOptionsAvailableDTO menuOptionsAvailableDTO) throws URISyntaxException {
        log.debug("REST request to update MenuOptionsAvailable : {}", menuOptionsAvailableDTO);
        if (menuOptionsAvailableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MenuOptionsAvailableDTO result = menuOptionsAvailableService.save(menuOptionsAvailableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuOptionsAvailableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /menu-options-availables} : get all the menuOptionsAvailables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menuOptionsAvailables in body.
     */
    @GetMapping("/menu-options-availables")
    public ResponseEntity<List<MenuOptionsAvailableDTO>> getAllMenuOptionsAvailables(MenuOptionsAvailableCriteria criteria) {
        log.debug("REST request to get MenuOptionsAvailables by criteria: {}", criteria);
        List<MenuOptionsAvailableDTO> entityList = menuOptionsAvailableQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /menu-options-availables/count} : count all the menuOptionsAvailables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/menu-options-availables/count")
    public ResponseEntity<Long> countMenuOptionsAvailables(MenuOptionsAvailableCriteria criteria) {
        log.debug("REST request to count MenuOptionsAvailables by criteria: {}", criteria);
        return ResponseEntity.ok().body(menuOptionsAvailableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /menu-options-availables/:id} : get the "id" menuOptionsAvailable.
     *
     * @param id the id of the menuOptionsAvailableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menuOptionsAvailableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/menu-options-availables/{id}")
    public ResponseEntity<MenuOptionsAvailableDTO> getMenuOptionsAvailable(@PathVariable Long id) {
        log.debug("REST request to get MenuOptionsAvailable : {}", id);
        Optional<MenuOptionsAvailableDTO> menuOptionsAvailableDTO = menuOptionsAvailableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(menuOptionsAvailableDTO);
    }

    /**
     * {@code DELETE  /menu-options-availables/:id} : delete the "id" menuOptionsAvailable.
     *
     * @param id the id of the menuOptionsAvailableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/menu-options-availables/{id}")
    public ResponseEntity<Void> deleteMenuOptionsAvailable(@PathVariable Long id) {
        log.debug("REST request to delete MenuOptionsAvailable : {}", id);
        menuOptionsAvailableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package es.emilio.web.rest;

import es.emilio.service.TimeBandService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.TimeBandDTO;
import es.emilio.service.dto.TimeBandCriteria;
import es.emilio.service.TimeBandQueryService;

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
 * REST controller for managing {@link es.emilio.domain.TimeBand}.
 */
@RestController
@RequestMapping("/api")
public class TimeBandResource {

    private final Logger log = LoggerFactory.getLogger(TimeBandResource.class);

    private static final String ENTITY_NAME = "timeBand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeBandService timeBandService;

    private final TimeBandQueryService timeBandQueryService;

    public TimeBandResource(TimeBandService timeBandService, TimeBandQueryService timeBandQueryService) {
        this.timeBandService = timeBandService;
        this.timeBandQueryService = timeBandQueryService;
    }

    /**
     * {@code POST  /time-bands} : Create a new timeBand.
     *
     * @param timeBandDTO the timeBandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeBandDTO, or with status {@code 400 (Bad Request)} if the timeBand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-bands")
    public ResponseEntity<TimeBandDTO> createTimeBand(@RequestBody TimeBandDTO timeBandDTO) throws URISyntaxException {
        log.debug("REST request to save TimeBand : {}", timeBandDTO);
        if (timeBandDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeBand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeBandDTO result = timeBandService.save(timeBandDTO);
        return ResponseEntity.created(new URI("/api/time-bands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-bands} : Updates an existing timeBand.
     *
     * @param timeBandDTO the timeBandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeBandDTO,
     * or with status {@code 400 (Bad Request)} if the timeBandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeBandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-bands")
    public ResponseEntity<TimeBandDTO> updateTimeBand(@RequestBody TimeBandDTO timeBandDTO) throws URISyntaxException {
        log.debug("REST request to update TimeBand : {}", timeBandDTO);
        if (timeBandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeBandDTO result = timeBandService.save(timeBandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeBandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /time-bands} : get all the timeBands.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeBands in body.
     */
    @GetMapping("/time-bands")
    public ResponseEntity<List<TimeBandDTO>> getAllTimeBands(TimeBandCriteria criteria) {
        log.debug("REST request to get TimeBands by criteria: {}", criteria);
        List<TimeBandDTO> entityList = timeBandQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /time-bands/count} : count all the timeBands.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/time-bands/count")
    public ResponseEntity<Long> countTimeBands(TimeBandCriteria criteria) {
        log.debug("REST request to count TimeBands by criteria: {}", criteria);
        return ResponseEntity.ok().body(timeBandQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /time-bands/:id} : get the "id" timeBand.
     *
     * @param id the id of the timeBandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeBandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-bands/{id}")
    public ResponseEntity<TimeBandDTO> getTimeBand(@PathVariable Long id) {
        log.debug("REST request to get TimeBand : {}", id);
        Optional<TimeBandDTO> timeBandDTO = timeBandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeBandDTO);
    }

    /**
     * {@code DELETE  /time-bands/:id} : delete the "id" timeBand.
     *
     * @param id the id of the timeBandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-bands/{id}")
    public ResponseEntity<Void> deleteTimeBand(@PathVariable Long id) {
        log.debug("REST request to delete TimeBand : {}", id);
        timeBandService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

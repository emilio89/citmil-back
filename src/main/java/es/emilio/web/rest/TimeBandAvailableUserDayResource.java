package es.emilio.web.rest;

import es.emilio.service.TimeBandAvailableUserDayService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.TimeBandAvailableUserDayDTO;

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
 * REST controller for managing {@link es.emilio.domain.TimeBandAvailableUserDay}.
 */
@RestController
@RequestMapping("/api")
public class TimeBandAvailableUserDayResource {

    private final Logger log = LoggerFactory.getLogger(TimeBandAvailableUserDayResource.class);

    private static final String ENTITY_NAME = "timeBandAvailableUserDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeBandAvailableUserDayService timeBandAvailableUserDayService;

    public TimeBandAvailableUserDayResource(TimeBandAvailableUserDayService timeBandAvailableUserDayService) {
        this.timeBandAvailableUserDayService = timeBandAvailableUserDayService;
    }

    /**
     * {@code POST  /time-band-available-user-days} : Create a new timeBandAvailableUserDay.
     *
     * @param timeBandAvailableUserDayDTO the timeBandAvailableUserDayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeBandAvailableUserDayDTO, or with status {@code 400 (Bad Request)} if the timeBandAvailableUserDay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-band-available-user-days")
    public ResponseEntity<TimeBandAvailableUserDayDTO> createTimeBandAvailableUserDay(@RequestBody TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO) throws URISyntaxException {
        log.debug("REST request to save TimeBandAvailableUserDay : {}", timeBandAvailableUserDayDTO);
        if (timeBandAvailableUserDayDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeBandAvailableUserDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeBandAvailableUserDayDTO result = timeBandAvailableUserDayService.save(timeBandAvailableUserDayDTO);
        return ResponseEntity.created(new URI("/api/time-band-available-user-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-band-available-user-days} : Updates an existing timeBandAvailableUserDay.
     *
     * @param timeBandAvailableUserDayDTO the timeBandAvailableUserDayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeBandAvailableUserDayDTO,
     * or with status {@code 400 (Bad Request)} if the timeBandAvailableUserDayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeBandAvailableUserDayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-band-available-user-days")
    public ResponseEntity<TimeBandAvailableUserDayDTO> updateTimeBandAvailableUserDay(@RequestBody TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO) throws URISyntaxException {
        log.debug("REST request to update TimeBandAvailableUserDay : {}", timeBandAvailableUserDayDTO);
        if (timeBandAvailableUserDayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeBandAvailableUserDayDTO result = timeBandAvailableUserDayService.save(timeBandAvailableUserDayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeBandAvailableUserDayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /time-band-available-user-days} : get all the timeBandAvailableUserDays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeBandAvailableUserDays in body.
     */
    @GetMapping("/time-band-available-user-days")
    public List<TimeBandAvailableUserDayDTO> getAllTimeBandAvailableUserDays() {
        log.debug("REST request to get all TimeBandAvailableUserDays");
        return timeBandAvailableUserDayService.findAll();
    }

    /**
     * {@code GET  /time-band-available-user-days/:id} : get the "id" timeBandAvailableUserDay.
     *
     * @param id the id of the timeBandAvailableUserDayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeBandAvailableUserDayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-band-available-user-days/{id}")
    public ResponseEntity<TimeBandAvailableUserDayDTO> getTimeBandAvailableUserDay(@PathVariable Long id) {
        log.debug("REST request to get TimeBandAvailableUserDay : {}", id);
        Optional<TimeBandAvailableUserDayDTO> timeBandAvailableUserDayDTO = timeBandAvailableUserDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeBandAvailableUserDayDTO);
    }

    /**
     * {@code DELETE  /time-band-available-user-days/:id} : delete the "id" timeBandAvailableUserDay.
     *
     * @param id the id of the timeBandAvailableUserDayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-band-available-user-days/{id}")
    public ResponseEntity<Void> deleteTimeBandAvailableUserDay(@PathVariable Long id) {
        log.debug("REST request to delete TimeBandAvailableUserDay : {}", id);
        timeBandAvailableUserDayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

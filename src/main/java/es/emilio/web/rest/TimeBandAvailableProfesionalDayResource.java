package es.emilio.web.rest;

import es.emilio.service.TimeBandAvailableProfesionalDayService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.TimeBandAvailableProfesionalDayDTO;

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
 * REST controller for managing {@link es.emilio.domain.TimeBandAvailableProfesionalDay}.
 */
@RestController
@RequestMapping("/api")
public class TimeBandAvailableProfesionalDayResource {

    private final Logger log = LoggerFactory.getLogger(TimeBandAvailableProfesionalDayResource.class);

    private static final String ENTITY_NAME = "timeBandAvailableProfesionalDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeBandAvailableProfesionalDayService timeBandAvailableProfesionalDayService;

    public TimeBandAvailableProfesionalDayResource(TimeBandAvailableProfesionalDayService timeBandAvailableProfesionalDayService) {
        this.timeBandAvailableProfesionalDayService = timeBandAvailableProfesionalDayService;
    }

    /**
     * {@code POST  /time-band-available-profesional-days} : Create a new timeBandAvailableProfesionalDay.
     *
     * @param timeBandAvailableProfesionalDayDTO the timeBandAvailableProfesionalDayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeBandAvailableProfesionalDayDTO, or with status {@code 400 (Bad Request)} if the timeBandAvailableProfesionalDay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-band-available-profesional-days")
    public ResponseEntity<TimeBandAvailableProfesionalDayDTO> createTimeBandAvailableProfesionalDay(@RequestBody TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO) throws URISyntaxException {
        log.debug("REST request to save TimeBandAvailableProfesionalDay : {}", timeBandAvailableProfesionalDayDTO);
        if (timeBandAvailableProfesionalDayDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeBandAvailableProfesionalDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeBandAvailableProfesionalDayDTO result = timeBandAvailableProfesionalDayService.save(timeBandAvailableProfesionalDayDTO);
        return ResponseEntity.created(new URI("/api/time-band-available-profesional-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-band-available-profesional-days} : Updates an existing timeBandAvailableProfesionalDay.
     *
     * @param timeBandAvailableProfesionalDayDTO the timeBandAvailableProfesionalDayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeBandAvailableProfesionalDayDTO,
     * or with status {@code 400 (Bad Request)} if the timeBandAvailableProfesionalDayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeBandAvailableProfesionalDayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-band-available-profesional-days")
    public ResponseEntity<TimeBandAvailableProfesionalDayDTO> updateTimeBandAvailableProfesionalDay(@RequestBody TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO) throws URISyntaxException {
        log.debug("REST request to update TimeBandAvailableProfesionalDay : {}", timeBandAvailableProfesionalDayDTO);
        if (timeBandAvailableProfesionalDayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeBandAvailableProfesionalDayDTO result = timeBandAvailableProfesionalDayService.save(timeBandAvailableProfesionalDayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeBandAvailableProfesionalDayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /time-band-available-profesional-days} : get all the timeBandAvailableProfesionalDays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeBandAvailableProfesionalDays in body.
     */
    @GetMapping("/time-band-available-profesional-days")
    public List<TimeBandAvailableProfesionalDayDTO> getAllTimeBandAvailableProfesionalDays() {
        log.debug("REST request to get all TimeBandAvailableProfesionalDays");
        return timeBandAvailableProfesionalDayService.findAll();
    }

    /**
     * {@code GET  /time-band-available-profesional-days/:id} : get the "id" timeBandAvailableProfesionalDay.
     *
     * @param id the id of the timeBandAvailableProfesionalDayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeBandAvailableProfesionalDayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-band-available-profesional-days/{id}")
    public ResponseEntity<TimeBandAvailableProfesionalDayDTO> getTimeBandAvailableProfesionalDay(@PathVariable Long id) {
        log.debug("REST request to get TimeBandAvailableProfesionalDay : {}", id);
        Optional<TimeBandAvailableProfesionalDayDTO> timeBandAvailableProfesionalDayDTO = timeBandAvailableProfesionalDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeBandAvailableProfesionalDayDTO);
    }

    /**
     * {@code DELETE  /time-band-available-profesional-days/:id} : delete the "id" timeBandAvailableProfesionalDay.
     *
     * @param id the id of the timeBandAvailableProfesionalDayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-band-available-profesional-days/{id}")
    public ResponseEntity<Void> deleteTimeBandAvailableProfesionalDay(@PathVariable Long id) {
        log.debug("REST request to delete TimeBandAvailableProfesionalDay : {}", id);
        timeBandAvailableProfesionalDayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

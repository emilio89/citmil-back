package es.emilio.web.rest;

import es.emilio.service.CalendarYearProfesionalService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.CalendarYearProfesionalDTO;

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
 * REST controller for managing {@link es.emilio.domain.CalendarYearProfesional}.
 */
@RestController
@RequestMapping("/api")
public class CalendarYearProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(CalendarYearProfesionalResource.class);

    private static final String ENTITY_NAME = "calendarYearProfesional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalendarYearProfesionalService calendarYearProfesionalService;

    public CalendarYearProfesionalResource(CalendarYearProfesionalService calendarYearProfesionalService) {
        this.calendarYearProfesionalService = calendarYearProfesionalService;
    }

    /**
     * {@code POST  /calendar-year-profesionals} : Create a new calendarYearProfesional.
     *
     * @param calendarYearProfesionalDTO the calendarYearProfesionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendarYearProfesionalDTO, or with status {@code 400 (Bad Request)} if the calendarYearProfesional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calendar-year-profesionals")
    public ResponseEntity<CalendarYearProfesionalDTO> createCalendarYearProfesional(@RequestBody CalendarYearProfesionalDTO calendarYearProfesionalDTO) throws URISyntaxException {
        log.debug("REST request to save CalendarYearProfesional : {}", calendarYearProfesionalDTO);
        if (calendarYearProfesionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new calendarYearProfesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalendarYearProfesionalDTO result = calendarYearProfesionalService.save(calendarYearProfesionalDTO);
        return ResponseEntity.created(new URI("/api/calendar-year-profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calendar-year-profesionals} : Updates an existing calendarYearProfesional.
     *
     * @param calendarYearProfesionalDTO the calendarYearProfesionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarYearProfesionalDTO,
     * or with status {@code 400 (Bad Request)} if the calendarYearProfesionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendarYearProfesionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calendar-year-profesionals")
    public ResponseEntity<CalendarYearProfesionalDTO> updateCalendarYearProfesional(@RequestBody CalendarYearProfesionalDTO calendarYearProfesionalDTO) throws URISyntaxException {
        log.debug("REST request to update CalendarYearProfesional : {}", calendarYearProfesionalDTO);
        if (calendarYearProfesionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalendarYearProfesionalDTO result = calendarYearProfesionalService.save(calendarYearProfesionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calendarYearProfesionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calendar-year-profesionals} : get all the calendarYearProfesionals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calendarYearProfesionals in body.
     */
    @GetMapping("/calendar-year-profesionals")
    public List<CalendarYearProfesionalDTO> getAllCalendarYearProfesionals() {
        log.debug("REST request to get all CalendarYearProfesionals");
        return calendarYearProfesionalService.findAll();
    }

    /**
     * {@code GET  /calendar-year-profesionals/:id} : get the "id" calendarYearProfesional.
     *
     * @param id the id of the calendarYearProfesionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarYearProfesionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendar-year-profesionals/{id}")
    public ResponseEntity<CalendarYearProfesionalDTO> getCalendarYearProfesional(@PathVariable Long id) {
        log.debug("REST request to get CalendarYearProfesional : {}", id);
        Optional<CalendarYearProfesionalDTO> calendarYearProfesionalDTO = calendarYearProfesionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarYearProfesionalDTO);
    }

    /**
     * {@code DELETE  /calendar-year-profesionals/:id} : delete the "id" calendarYearProfesional.
     *
     * @param id the id of the calendarYearProfesionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calendar-year-profesionals/{id}")
    public ResponseEntity<Void> deleteCalendarYearProfesional(@PathVariable Long id) {
        log.debug("REST request to delete CalendarYearProfesional : {}", id);
        calendarYearProfesionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

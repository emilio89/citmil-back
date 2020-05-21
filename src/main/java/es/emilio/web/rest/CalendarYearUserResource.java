package es.emilio.web.rest;

import es.emilio.service.CalendarYearUserService;
import es.emilio.web.rest.errors.BadRequestAlertException;
import es.emilio.service.dto.CalendarYearUserDTO;

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
 * REST controller for managing {@link es.emilio.domain.CalendarYearUser}.
 */
@RestController
@RequestMapping("/api")
public class CalendarYearUserResource {

    private final Logger log = LoggerFactory.getLogger(CalendarYearUserResource.class);

    private static final String ENTITY_NAME = "calendarYearUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalendarYearUserService calendarYearUserService;

    public CalendarYearUserResource(CalendarYearUserService calendarYearUserService) {
        this.calendarYearUserService = calendarYearUserService;
    }

    /**
     * {@code POST  /calendar-year-users} : Create a new calendarYearUser.
     *
     * @param calendarYearUserDTO the calendarYearUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendarYearUserDTO, or with status {@code 400 (Bad Request)} if the calendarYearUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calendar-year-users")
    public ResponseEntity<CalendarYearUserDTO> createCalendarYearUser(@RequestBody CalendarYearUserDTO calendarYearUserDTO) throws URISyntaxException {
        log.debug("REST request to save CalendarYearUser : {}", calendarYearUserDTO);
        if (calendarYearUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new calendarYearUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalendarYearUserDTO result = calendarYearUserService.save(calendarYearUserDTO);
        return ResponseEntity.created(new URI("/api/calendar-year-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calendar-year-users} : Updates an existing calendarYearUser.
     *
     * @param calendarYearUserDTO the calendarYearUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarYearUserDTO,
     * or with status {@code 400 (Bad Request)} if the calendarYearUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendarYearUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calendar-year-users")
    public ResponseEntity<CalendarYearUserDTO> updateCalendarYearUser(@RequestBody CalendarYearUserDTO calendarYearUserDTO) throws URISyntaxException {
        log.debug("REST request to update CalendarYearUser : {}", calendarYearUserDTO);
        if (calendarYearUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalendarYearUserDTO result = calendarYearUserService.save(calendarYearUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calendarYearUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calendar-year-users} : get all the calendarYearUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calendarYearUsers in body.
     */
    @GetMapping("/calendar-year-users")
    public List<CalendarYearUserDTO> getAllCalendarYearUsers() {
        log.debug("REST request to get all CalendarYearUsers");
        return calendarYearUserService.findAll();
    }

    /**
     * {@code GET  /calendar-year-users/:id} : get the "id" calendarYearUser.
     *
     * @param id the id of the calendarYearUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarYearUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendar-year-users/{id}")
    public ResponseEntity<CalendarYearUserDTO> getCalendarYearUser(@PathVariable Long id) {
        log.debug("REST request to get CalendarYearUser : {}", id);
        Optional<CalendarYearUserDTO> calendarYearUserDTO = calendarYearUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarYearUserDTO);
    }

    /**
     * {@code DELETE  /calendar-year-users/:id} : delete the "id" calendarYearUser.
     *
     * @param id the id of the calendarYearUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calendar-year-users/{id}")
    public ResponseEntity<Void> deleteCalendarYearUser(@PathVariable Long id) {
        log.debug("REST request to delete CalendarYearUser : {}", id);
        calendarYearUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

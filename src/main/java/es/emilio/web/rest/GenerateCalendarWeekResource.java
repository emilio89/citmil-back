package es.emilio.web.rest;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.emilio.security.jwt.TokenProvider;
import es.emilio.service.GenerateCalendarWeekService;
import es.emilio.service.dto.GenerateCalendarWeekDTO;
import io.github.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link es.emilio.domain.TimeBand}.
 */
@RestController
@RequestMapping("/api")
public class GenerateCalendarWeekResource {

	private final Logger log = LoggerFactory.getLogger(GenerateCalendarWeekResource.class);

	private static final String ENTITY_NAME = "timeBand";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
    public static final String AUTHORIZATION_HEADER = "Authorization";

	@Autowired
	GenerateCalendarWeekService generateCalendarWeek;
	
    @Autowired
    TokenProvider tokenProvider;

	public GenerateCalendarWeekResource() {
	}

	/**
	 * {@code POST  /generateCalendarWeek} : Generate a week in a calendar
	 *
	 */
	@PostMapping("/generateCalendarWeek")
	public ResponseEntity<Void> generateCalendarWeek(@RequestBody GenerateCalendarWeekDTO generateCalendarWeekDTO, HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        Long companyId = tokenProvider.getIdCompany(bearerToken);
		log.debug("REST request to generate calendar week  : {} List users size "
				+ generateCalendarWeekDTO.getUsers().size() + " week " 
				+ " time bands days size " + generateCalendarWeekDTO.getTimeBandsDay().size());
		generateCalendarWeek.generate(generateCalendarWeekDTO, companyId);
		return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "generado correctamente", generateCalendarWeekDTO.toString())).build();	
	}

//    /**
//     * {@code PUT  /time-bands} : Updates an existing timeBand.
//     *
//     * @param timeBandDTO the timeBandDTO to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeBandDTO,
//     * or with status {@code 400 (Bad Request)} if the timeBandDTO is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the timeBandDTO couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/time-bands")
//    public ResponseEntity<TimeBandDTO> updateTimeBand(@RequestBody TimeBandDTO timeBandDTO) throws URISyntaxException {
//        log.debug("REST request to update TimeBand : {}", timeBandDTO);
//        if (timeBandDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        TimeBandDTO result = timeBandService.save(timeBandDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeBandDTO.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code GET  /time-bands} : get all the timeBands.
//     *
//     * @param criteria the criteria which the requested entities should match.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeBands in body.
//     */
//    @GetMapping("/time-bands")
//    public ResponseEntity<List<TimeBandDTO>> getAllTimeBands(TimeBandCriteria criteria) {
//        log.debug("REST request to get TimeBands by criteria: {}", criteria);
//        List<TimeBandDTO> entityList = timeBandQueryService.findByCriteria(criteria);
//        return ResponseEntity.ok().body(entityList);
//    }
//
//    /**
//     * {@code GET  /time-bands/count} : count all the timeBands.
//     *
//     * @param criteria the criteria which the requested entities should match.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
//     */
//    @GetMapping("/time-bands/count")
//    public ResponseEntity<Long> countTimeBands(TimeBandCriteria criteria) {
//        log.debug("REST request to count TimeBands by criteria: {}", criteria);
//        return ResponseEntity.ok().body(timeBandQueryService.countByCriteria(criteria));
//    }
//
//    /**
//     * {@code GET  /time-bands/:id} : get the "id" timeBand.
//     *
//     * @param id the id of the timeBandDTO to retrieve.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeBandDTO, or with status {@code 404 (Not Found)}.
//     */
//    @GetMapping("/time-bands/{id}")
//    public ResponseEntity<TimeBandDTO> getTimeBand(@PathVariable Long id) {
//        log.debug("REST request to get TimeBand : {}", id);
//        Optional<TimeBandDTO> timeBandDTO = timeBandService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(timeBandDTO);
//    }
//
//    /**
//     * {@code DELETE  /time-bands/:id} : delete the "id" timeBand.
//     *
//     * @param id the id of the timeBandDTO to delete.
//     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//     */
//    @DeleteMapping("/time-bands/{id}")
//    public ResponseEntity<Void> deleteTimeBand(@PathVariable Long id) {
//        log.debug("REST request to delete TimeBand : {}", id);
//        timeBandService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
//    }
}

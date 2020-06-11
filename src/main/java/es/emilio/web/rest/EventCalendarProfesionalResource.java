package es.emilio.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.emilio.security.jwt.TokenProvider;
import es.emilio.service.CalendarYearUserService;
import es.emilio.service.dto.EventCalendarProfesionalDTO;
import io.github.jhipster.web.util.HeaderUtil;

/**
 * REST controller for manage events in calendar profesional
 */
@RestController
@RequestMapping("/api")
public class EventCalendarProfesionalResource {

	private final Logger log = LoggerFactory.getLogger(EventCalendarProfesionalResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
    public static final String AUTHORIZATION_HEADER = "Authorization";

	@Autowired
	CalendarYearUserService calendarYearUserService;
	
    @Autowired
    TokenProvider tokenProvider;

	public EventCalendarProfesionalResource() {
	}

	/**
	 * {@code GET  /getAllEventCalendarProfesional} : Request to view all
	 * profesional events in a galendar
	 *
	 */
	@GetMapping("/getAllEventCalendarProfesional")
	public ResponseEntity<List<EventCalendarProfesionalDTO>> generateCalendarIndividualDays(
			HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		Long companyId = tokenProvider.getIdCompany(bearerToken);
		log.debug("REST request to get all events in calendar profesional user  : {} in company : " + companyId);
		List<EventCalendarProfesionalDTO> response = calendarYearUserService.getAllEventCalendarProfesional(companyId);
		return ResponseEntity.ok().body(response);
	}

}

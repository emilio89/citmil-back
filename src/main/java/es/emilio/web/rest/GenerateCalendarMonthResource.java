package es.emilio.web.rest;

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
import es.emilio.service.GenerateCalendarService;
import es.emilio.service.dto.GenerateCalendarMonthDTO;
import io.github.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link es.emilio.domain.TimeBand}.
 */
@RestController
@RequestMapping("/api")
public class GenerateCalendarMonthResource {

	private final Logger log = LoggerFactory.getLogger(GenerateCalendarMonthResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
    public static final String AUTHORIZATION_HEADER = "Authorization";

	@Autowired
	GenerateCalendarService generateCalendarService;
	
    @Autowired
    TokenProvider tokenProvider;

	public GenerateCalendarMonthResource() {
	}

	/**
	 * {@code POST  /generateCalendarMonth} : Generate a calendar for a month selected
	 *
	 */
	@PostMapping("/generateCalendarMonth")
	public ResponseEntity<Void> generateCalendarMonth(@RequestBody GenerateCalendarMonthDTO generateCalendarMonthDTO, HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        Long companyId = tokenProvider.getIdCompany(bearerToken);
		log.debug("REST request to generate calendar with a month  : {} List users size "
				+ generateCalendarMonthDTO.getUsers().size() + "  " 
				+ " time bands days size " + generateCalendarMonthDTO.getDays().size());
		generateCalendarService.generateMonth(generateCalendarMonthDTO, companyId);
		return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "generado correctamente", generateCalendarMonthDTO.toString())).build();	
	}


}

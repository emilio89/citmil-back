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
import es.emilio.service.GenerateCalendarIndividualDaysService;
import es.emilio.service.dto.GenerateCalendarIndividualDaysDTO;
import io.github.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link es.emilio.domain.TimeBand}.
 */
@RestController
@RequestMapping("/api")
public class GenerateCalendarIndividualDaysResource {

	private final Logger log = LoggerFactory.getLogger(GenerateCalendarIndividualDaysResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
    public static final String AUTHORIZATION_HEADER = "Authorization";

	@Autowired
	GenerateCalendarIndividualDaysService generateCalendarIndividualDays;
	
    @Autowired
    TokenProvider tokenProvider;

	public GenerateCalendarIndividualDaysResource() {
	}

	/**
	 * {@code POST  /generateCalendarIndividualDays} : Generate a calendar for individual names selected in a calendar
	 *
	 */
	@PostMapping("/generateCalendarIndividualDays")
	public ResponseEntity<Void> generateCalendarIndividualDays(@RequestBody GenerateCalendarIndividualDaysDTO generateCalendarIndividualDaysDTO, HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        Long companyId = tokenProvider.getIdCompany(bearerToken);
		log.debug("REST request to generate calendar IndividualDays  : {} List users size "
				+ generateCalendarIndividualDaysDTO.getUsers().size() + "  " 
				+ " time bands days size " + generateCalendarIndividualDaysDTO.getTimeBandsDay().size());
		generateCalendarIndividualDays.generate(generateCalendarIndividualDaysDTO, companyId);
		return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "generado correctamente", generateCalendarIndividualDaysDTO.toString())).build();	
	}


}

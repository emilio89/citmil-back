package es.emilio.service;

import es.emilio.domain.Company;
import es.emilio.service.dto.GenerateCalendarIndividualDaysDTO;

/**
 * Service Interface for generate Individual days selected in Calendar of users 
 */
public interface GenerateCalendarIndividualDaysService {


	public void generate(GenerateCalendarIndividualDaysDTO generateCalendarIndividualDaysDTO, Long companyId);

    
}

package es.emilio.service;

import es.emilio.domain.Company;
import es.emilio.service.dto.GenerateCalendarIndividualDaysDTO;
import es.emilio.service.dto.GenerateCalendarMonthDTO;

/**
 * Service Interface for generate Individual days selected in Calendar of users 
 */
public interface GenerateCalendarService {


	public void generateIndividualDays(GenerateCalendarIndividualDaysDTO generateCalendarIndividualDaysDTO, Long companyId);

	public void generateMonth(GenerateCalendarMonthDTO generateCalendarMonthDTO, Long companyId);

    
}

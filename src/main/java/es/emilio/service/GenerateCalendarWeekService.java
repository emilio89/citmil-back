package es.emilio.service;

import es.emilio.domain.Company;
import es.emilio.service.dto.GenerateCalendarWeekDTO;

/**
 * Service Interface for generate Week Calendar of users 
 */
public interface GenerateCalendarWeekService {


	public void generate(GenerateCalendarWeekDTO generateCalendarWeekDTO, Long companyId);

    
}

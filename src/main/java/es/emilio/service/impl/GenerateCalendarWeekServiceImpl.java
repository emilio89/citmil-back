package es.emilio.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.emilio.domain.CalendarYearUser;
import es.emilio.domain.Company;
import es.emilio.domain.User;
import es.emilio.repository.CalendarYearUserRepository;
import es.emilio.repository.CompanyRepository;
import es.emilio.repository.TimeBandAvailableUserDayRepository;
import es.emilio.repository.UserRepository;
import es.emilio.service.GenerateCalendarWeekService;
import es.emilio.service.dto.GenerateCalendarWeekDTO;
import es.emilio.service.dto.TimeBandDayDTO;
import es.emilio.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenerateCalendarWeekServiceImpl implements GenerateCalendarWeekService{

	@Autowired
	CalendarYearUserRepository calendarYearUserRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TimeBandAvailableUserDayRepository timeBandAvailableUserRepository;
	
	@Override
	@Transactional
	public void generate(GenerateCalendarWeekDTO generateCalendarWeekDTO, Long companyId) {
		log.info("Comenzamos a generar el calendario por semana de los profesionales ");
		for (UserDTO userDTO : generateCalendarWeekDTO.getUsers()) {
			for (TimeBandDayDTO timeBandDayDTO : generateCalendarWeekDTO.getTimeBandsDay()) {
				User user = userRepository.getOne(userDTO.getId());
				Company company = companyRepository.getOne(companyId);
				log.info("user : " + userDTO.getFirstName());
				LocalDate day = timeBandDayDTO.getDay().atZone(ZoneId.of("Europe/Paris")).toLocalDate();
				Optional<CalendarYearUser> calendarYearUserOp = calendarYearUserRepository.findByDayAndYearAndUserAndCompany(day, day.getYear(), user, company);
				if (calendarYearUserOp.isPresent()) {
					log.info("Se procede a borrar la línea de calendario : " + calendarYearUserOp.toString());
					calendarYearUserRepository.delete(calendarYearUserOp.get());
				}
				CalendarYearUser calendarYearUser = new CalendarYearUser();
				calendarYearUser.setDay(day);
				calendarYearUser.setStart(day.atStartOfDay().atZone((ZoneId.of("Europe/Paris"))));
				calendarYearUser.setEnd(day.atTime(LocalTime.MAX).atZone((ZoneId.of("Europe/Paris"))));
				calendarYearUser.setIsPublicHoliday(Boolean.FALSE);
				calendarYearUser.setYear(day.getYear());
				calendarYearUser.setCompany(company);
				calendarYearUser.setUser(user);
				calendarYearUserRepository.save(calendarYearUser);

			
			}
		}
		// genero los siguientes 7 días empezando por el que me llega en week

		//ALGORITMO: por cada USUARIO!
		// Recuperar el inicio de la semana y a partir de ese día, mirar cuantos timeBandsDays tenemos
		// y por cada timeBandsDays mirar que día es y meter en CalendarYearUser el día
		// y las timeBands en TimeBandAvailableUserDay
	}

}

package es.emilio.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.emilio.domain.CalendarYearUser;
import es.emilio.domain.Company;
import es.emilio.domain.TimeBand;
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
public class GenerateCalendarWeekServiceImpl implements GenerateCalendarWeekService {

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
			User user = userRepository.getOne(userDTO.getId());
			Company company = companyRepository.getOne(companyId);
			log.info("user : " + userDTO.getFirstName());

			for (TimeBandDayDTO timeBandDayDTO : generateCalendarWeekDTO.getTimeBandsDay()) {
				LocalDate day = timeBandDayDTO.getDay().atZone(ZoneId.systemDefault()).toLocalDate();
				Optional<CalendarYearUser> calendarYearUserOp = calendarYearUserRepository
						.findByDayAndYearAndUserAndCompany(day, day.getYear(), user, company);
				if (calendarYearUserOp.isPresent()) {
					log.info("Se procede a borrar la línea de calendario : " + calendarYearUserOp.toString());
					log.info("Se procede a borrar las time bands asociadas a la linea del calendario : "
							+ calendarYearUserOp.get().getId());

					calendarYearUserRepository.delete(calendarYearUserOp.get());
				}

			}
			for (TimeBandDayDTO timeBandDayDTO : generateCalendarWeekDTO.getTimeBandsDay()) {
				LocalDate day = timeBandDayDTO.getDay().atZone(ZoneId.systemDefault()).toLocalDate();
				Set<TimeBand> timeBands = new HashSet<TimeBand>();
				Set<CalendarYearUser> calendarYearUsers = new HashSet<CalendarYearUser>();

				TimeBand timeBand = new TimeBand();
				timeBand.setCompany(company);
				Optional<CalendarYearUser> calendarYearUserOp = calendarYearUserRepository
						.findByDayAndYearAndUserAndCompany(day, day.getYear(), user, company);
				if (calendarYearUserOp.isPresent()) {
					//MODIFICAR AQUI TAMÉN AS FECHAS
					//timeBand.setEnd(timeBandDayDTO.getEnd().atZone(ZoneId.systemDefault()).toInstant());
				//	timeBand.setStart(timeBandDayDTO.getStart().atZone(ZoneId.systemDefault()).toInstant());
					calendarYearUserOp.get().getTimeBands().add(timeBand);
					calendarYearUserRepository.save(calendarYearUserOp.get());

				} else {
					CalendarYearUser calendarYearUser = new CalendarYearUser();
					calendarYearUser.setDay(day);
					calendarYearUser.setStart(day.atStartOfDay().atZone((ZoneId.systemDefault())).toInstant());
					calendarYearUser.setEnd(day.atTime(LocalTime.MAX).atZone((ZoneId.systemDefault())).toInstant());
					//LocalTime hourStart = LocalTime.from(timeBandDayDTO.getStart());
					LocalTime hourStart = (timeBandDayDTO.getStart() != null) ? LocalTime.parse(timeBandDayDTO.getStart())
		                    : LocalTime.parse("00:00");
					LocalTime hourEnd = (timeBandDayDTO.getEnd() != null) ? LocalTime.parse(timeBandDayDTO.getEnd())
		                    : LocalTime.parse("00:00");
					LocalDateTime start = day.atTime(hourStart.getHour(), hourStart.getMinute());
					LocalDateTime end = day.atTime(hourEnd.getHour(), hourEnd.getMinute());

					timeBand.setEnd(end.atZone(ZoneId.systemDefault()).toInstant());
					timeBand.setStart(start.atZone(ZoneId.systemDefault()).toInstant());

					calendarYearUser.setIsPublicHoliday(Boolean.FALSE);
					calendarYearUser.setYear(day.getYear());
					calendarYearUser.setCompany(company);
					calendarYearUser.setUser(user);
					calendarYearUser.setTimeBands(timeBands);
					calendarYearUsers.add(calendarYearUser);
					timeBand.setCalendarYearUsers(calendarYearUsers);
					timeBands.add(timeBand);
					calendarYearUserRepository.save(calendarYearUser);

				}
			}
		}
		// genero los siguientes 7 días empezando por el que me llega en week

		// ALGORITMO: por cada USUARIO!
		// Recuperar el inicio de la semana y a partir de ese día, mirar cuantos
		// timeBandsDays tenemos
		// y por cada timeBandsDays mirar que día es y meter en CalendarYearUser el día
		// y las timeBands en TimeBandAvailableUserDay
	}

}

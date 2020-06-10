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

			deleteExistsCalendarYears(generateCalendarWeekDTO, user, company);
			for (TimeBandDayDTO timeBandDayDTO : generateCalendarWeekDTO.getTimeBandsDay()) {
				LocalDate day = timeBandDayDTO.getDay().atZone(ZoneId.systemDefault()).toLocalDate();
				Set<TimeBand> timeBands = new HashSet<TimeBand>();
				Set<CalendarYearUser> calendarYearUsers = new HashSet<CalendarYearUser>();

				TimeBand timeBand = new TimeBand();
				timeBand.setCompany(company);
				Optional<CalendarYearUser> calendarYearUserOp = calendarYearUserRepository
						.findByDayAndYearAndUserAndCompany(day, day.getYear(), user, company);
				LocalTime hourStart = getLocalTimeHourFromString(timeBandDayDTO.getStart());
				LocalTime hourEnd =  getLocalTimeHourFromString(timeBandDayDTO.getEnd());
				LocalDateTime start = getLocalDateTimeWithLocalDateAndLocalTime(day, hourStart);
				LocalDateTime end = getLocalDateTimeWithLocalDateAndLocalTime(day, hourEnd);
				timeBand.setEnd(getInstantWithLocalDateTime(end));
				timeBand.setStart(getInstantWithLocalDateTime(start));

				// Update porque existe (agregar el time band)
				if (calendarYearUserOp.isPresent()) {
					calendarYearUsers.add(calendarYearUserOp.get());
					timeBand.setCalendarYearUsers(calendarYearUsers);
					timeBands.add(timeBand);
					calendarYearUserOp.get().getTimeBands().add(timeBand);
					calendarYearUserRepository.save(calendarYearUserOp.get());
				} else {
					// Crear uno nuevo
					CalendarYearUser calendarYearUser = new CalendarYearUser();
					calendarYearUser.setDay(day);
					calendarYearUser.setStart(day.atStartOfDay().atZone((ZoneId.systemDefault())).toInstant());
					calendarYearUser.setEnd(day.atTime(LocalTime.MAX).atZone((ZoneId.systemDefault())).toInstant());

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
	}

	private void deleteExistsCalendarYears(GenerateCalendarWeekDTO generateCalendarWeekDTO, User user,
			Company company) {
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
	}

	private Instant getInstantWithLocalDateTime(LocalDateTime end) {
		return end.atZone(ZoneId.systemDefault()).toInstant();
	}

	private LocalDateTime getLocalDateTimeWithLocalDateAndLocalTime(LocalDate day, LocalTime hourStart) {
		return day.atTime(hourStart.getHour(), hourStart.getMinute());
	}

	private LocalTime getLocalTimeHourFromString(String hour) {
		return (hour != null) ? LocalTime.parse(hour)
		        : LocalTime.parse("00:00");
	}

}

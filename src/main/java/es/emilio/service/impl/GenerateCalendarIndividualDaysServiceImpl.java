package es.emilio.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
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
import es.emilio.service.GenerateCalendarService;
import es.emilio.service.dto.GenerateCalendarIndividualDaysDTO;
import es.emilio.service.dto.GenerateCalendarMonthDTO;
import es.emilio.service.dto.TimeBandDayDTO;
import es.emilio.service.dto.TimeBandStringDayDTO;
import es.emilio.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenerateCalendarIndividualDaysServiceImpl implements GenerateCalendarService {

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
	public void generateIndividualDays(GenerateCalendarIndividualDaysDTO generateCalendarIndividualDaysDTO, Long companyId) {
		Company company = companyRepository.getOne(companyId);
		log.info("Comenzamos a generar el calendario por semana de los profesionales en la empresa " + company.getName());

		for (UserDTO userDTO : generateCalendarIndividualDaysDTO.getUsers()) {
			User user = userRepository.getOne(userDTO.getId());
			log.info("user : " + userDTO.getFirstName());

			deleteExistsCalendarYearsIndividualDays(generateCalendarIndividualDaysDTO, user, company);
			createNewCalendarDayAndTimeBands(generateCalendarIndividualDaysDTO, user, company);
		}
	}

	private void createNewCalendarDayAndTimeBands(GenerateCalendarIndividualDaysDTO generateCalendarIndividualDaysDTO, User user,
			Company company) {
		for (TimeBandDayDTO timeBandDayDTO : generateCalendarIndividualDaysDTO.getTimeBandsDay()) {
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

	private void deleteExistsCalendarYearsIndividualDays(GenerateCalendarIndividualDaysDTO generateCalendarIndividualDaysDTO, User user,
			Company company) {
		for (TimeBandDayDTO timeBandDayDTO : generateCalendarIndividualDaysDTO.getTimeBandsDay()) {
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

	@Override
	public void generateMonth(GenerateCalendarMonthDTO generateCalendarMonthDTO, Long companyId) {
		// TODO Auto-generated method stub
	    ZoneId z = ZoneId.systemDefault();
	    ZonedDateTime zdtMonth = ZonedDateTime.ofInstant(generateCalendarMonthDTO.getMonth(), z);
		Company company = companyRepository.getOne(companyId);
		LocalDate date = zdtMonth.toLocalDate();
		//start of month :
		LocalDate firstDay = date .withDayOfMonth(1);
		//end of month
		LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());

		log.info("Se comienza la generación del calendario de los profesionales por mes para la compañia : " + companyId);
		log.info("El mes a generar es: " + zdtMonth.getMonth());

		iterateUsersAndMontToDelete(generateCalendarMonthDTO, company, firstDay, lastDay);
		iterateUsersAndMontToAdd(generateCalendarMonthDTO, company, firstDay, lastDay);

	}

	private void iterateUsersAndMontToDelete(GenerateCalendarMonthDTO generateCalendarMonthDTO, Company company,
			LocalDate firstDay, LocalDate lastDay) {
		for (UserDTO userDTO : generateCalendarMonthDTO.getUsers()) {
			User user = userRepository.getOne(userDTO.getId());
			for (LocalDate dateM = firstDay ; dateM.isBefore(lastDay ); dateM = dateM.plusDays(1))
			{
				if (generateCalendarMonthDTO.getDays().containsKey(dateM.getDayOfWeek().getValue())) {
					// Contiene ese día de la semana asi que borrar
					deleteExistsCalendarYearsMonth(dateM, user, company);
				}

			}
			// SE HACE LO MISMO PARA EL ÚLTIMO DÍA DEL MES
			if (generateCalendarMonthDTO.getDays().containsKey(lastDay.getDayOfWeek().getValue())) {
				// Contiene ese día de la semana asi que borrar
				deleteExistsCalendarYearsMonth(lastDay, user, company);
			}
		}
	}
	
	
	private void iterateUsersAndMontToAdd(GenerateCalendarMonthDTO generateCalendarMonthDTO, Company company,
			LocalDate firstDay, LocalDate lastDay) {
		for (UserDTO userDTO : generateCalendarMonthDTO.getUsers()) {
			User user = userRepository.getOne(userDTO.getId());

			for (LocalDate date = firstDay ; date.isBefore(lastDay ); date = date.plusDays(1))
			{
				if (generateCalendarMonthDTO.getDays().containsKey(date.getDayOfWeek().getValue())) {
					// Contiene ese día de la semana asi que añadir
					Optional<CalendarYearUser> calendarYearUserOp = calendarYearUserRepository
							.findByDayAndYearAndUserAndCompany(date, date.getYear(), user, company);
					Set<TimeBand> timeBands = new HashSet<TimeBand>();
					Set<CalendarYearUser> calendarYearUsers = new HashSet<CalendarYearUser>();

					if (calendarYearUserOp.isPresent()) {
						calendarYearUsers.add(calendarYearUserOp.get());
						List<TimeBandStringDayDTO> timeBandsToAdd = generateCalendarMonthDTO.getDays().get(date.getDayOfWeek().getValue());
						for (TimeBandStringDayDTO timeBandStringDayDTO : timeBandsToAdd) {
							TimeBand timeBand = new TimeBand();
							timeBand.setCompany(company);
							LocalTime hourStart = getLocalTimeHourFromString(timeBandStringDayDTO.getTimeBand().getStart());
							LocalTime hourEnd =  getLocalTimeHourFromString(timeBandStringDayDTO.getTimeBand().getEnd());
							LocalDateTime start = getLocalDateTimeWithLocalDateAndLocalTime(date, hourStart);
							LocalDateTime end = getLocalDateTimeWithLocalDateAndLocalTime(date, hourEnd);
							timeBand.setEnd(getInstantWithLocalDateTime(end));
							timeBand.setStart(getInstantWithLocalDateTime(start));
							timeBands.add(timeBand);
							timeBand.setCalendarYearUsers(calendarYearUsers);
							calendarYearUserOp.get().getTimeBands().add(timeBand);
						}
						calendarYearUserRepository.save(calendarYearUserOp.get());
					} else {
						CalendarYearUser calendarYearUser = new CalendarYearUser();
						calendarYearUser.setDay(date);
						calendarYearUser.setStart(date.atStartOfDay().atZone((ZoneId.systemDefault())).toInstant());
						calendarYearUser.setEnd(date.atTime(LocalTime.MAX).atZone((ZoneId.systemDefault())).toInstant());
						calendarYearUser.setIsPublicHoliday(Boolean.FALSE);
						calendarYearUser.setYear(date.getYear());
						calendarYearUser.setCompany(company);
						calendarYearUser.setUser(user);
						List<TimeBandStringDayDTO> timeBandsToAdd = generateCalendarMonthDTO.getDays().get(date.getDayOfWeek().getValue());
						calendarYearUsers.add(calendarYearUser);
						for (TimeBandStringDayDTO timeBandStringDayDTO : timeBandsToAdd) {
							TimeBand timeBand = new TimeBand();
							timeBand.setCompany(company);
							LocalTime hourStart = getLocalTimeHourFromString(timeBandStringDayDTO.getTimeBand().getStart());
							LocalTime hourEnd =  getLocalTimeHourFromString(timeBandStringDayDTO.getTimeBand().getEnd());
							LocalDateTime start = getLocalDateTimeWithLocalDateAndLocalTime(date, hourStart);
							LocalDateTime end = getLocalDateTimeWithLocalDateAndLocalTime(date, hourEnd);
							timeBand.setEnd(getInstantWithLocalDateTime(end));
							timeBand.setStart(getInstantWithLocalDateTime(start));
							timeBands.add(timeBand);
							timeBand.setCalendarYearUsers(calendarYearUsers);

						}
						calendarYearUser.setTimeBands(timeBands);
						calendarYearUserRepository.save(calendarYearUser);

					}
				}

			}
			// SE HACE LO MISMO PARA EL ÚLTIMO DÍA DEL MES
			if (generateCalendarMonthDTO.getDays().containsKey(lastDay.getDayOfWeek().getValue())) {
				// Contiene ese día de la semana asi que borrar
				deleteExistsCalendarYearsMonth(lastDay, user, company);
			}


		}
	}

	private void deleteExistsCalendarYearsMonth(LocalDate day, User user,
			Company company) {
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

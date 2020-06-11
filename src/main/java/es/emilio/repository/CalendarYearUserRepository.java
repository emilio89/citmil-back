package es.emilio.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.emilio.domain.CalendarYearUser;
import es.emilio.domain.Company;
import es.emilio.domain.User;
import es.emilio.service.dto.EventCalendarProfesionalDTO;

/**
 * Spring Data repository for the CalendarYearUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarYearUserRepository extends JpaRepository<CalendarYearUser, Long> {

	public Optional<CalendarYearUser> findByDayAndYearAndUserAndCompany(LocalDate day, Integer year, User user, Company company);

	@Query("Select new es.emilio.service.dto.EventCalendarProfesionalDTO(cyu.user.id, cyu.user.firstName,timeBand.start, timeBand.end ) from CalendarYearUser cyu join cyu.timeBands timeBand where cyu.company.id = ?1 and timeBand.company.id = ?1 ")
	public List<EventCalendarProfesionalDTO> findEventCalendarProfesional(Long companyId);

}
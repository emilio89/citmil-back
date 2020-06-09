package es.emilio.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.emilio.domain.CalendarYearUser;
import es.emilio.domain.Company;
import es.emilio.domain.User;

/**
 * Spring Data repository for the CalendarYearUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarYearUserRepository extends JpaRepository<CalendarYearUser, Long> {

	public Optional<CalendarYearUser> findByDayAndYearAndUserAndCompany(LocalDate day, Integer year, User user, Company company);
}

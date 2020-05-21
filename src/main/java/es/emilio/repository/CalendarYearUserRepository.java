package es.emilio.repository;

import es.emilio.domain.CalendarYearUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CalendarYearUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarYearUserRepository extends JpaRepository<CalendarYearUser, Long> {
}

package es.emilio.repository;

import es.emilio.domain.CalendarYearProfesional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CalendarYearProfesional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarYearProfesionalRepository extends JpaRepository<CalendarYearProfesional, Long> {
}

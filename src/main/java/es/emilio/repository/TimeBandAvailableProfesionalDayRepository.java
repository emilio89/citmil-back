package es.emilio.repository;

import es.emilio.domain.TimeBandAvailableProfesionalDay;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TimeBandAvailableProfesionalDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeBandAvailableProfesionalDayRepository extends JpaRepository<TimeBandAvailableProfesionalDay, Long> {
}

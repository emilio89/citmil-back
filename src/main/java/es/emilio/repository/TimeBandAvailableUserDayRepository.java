package es.emilio.repository;

import es.emilio.domain.TimeBandAvailableUserDay;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TimeBandAvailableUserDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeBandAvailableUserDayRepository extends JpaRepository<TimeBandAvailableUserDay, Long> {
}

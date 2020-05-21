package es.emilio.service;

import es.emilio.service.dto.TimeBandAvailableUserDayDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.TimeBandAvailableUserDay}.
 */
public interface TimeBandAvailableUserDayService {

    /**
     * Save a timeBandAvailableUserDay.
     *
     * @param timeBandAvailableUserDayDTO the entity to save.
     * @return the persisted entity.
     */
    TimeBandAvailableUserDayDTO save(TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO);

    /**
     * Get all the timeBandAvailableUserDays.
     *
     * @return the list of entities.
     */
    List<TimeBandAvailableUserDayDTO> findAll();

    /**
     * Get the "id" timeBandAvailableUserDay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeBandAvailableUserDayDTO> findOne(Long id);

    /**
     * Delete the "id" timeBandAvailableUserDay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

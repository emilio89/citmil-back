package es.emilio.service;

import es.emilio.service.dto.TimeBandAvailableProfesionalDayDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.TimeBandAvailableProfesionalDay}.
 */
public interface TimeBandAvailableProfesionalDayService {

    /**
     * Save a timeBandAvailableProfesionalDay.
     *
     * @param timeBandAvailableProfesionalDayDTO the entity to save.
     * @return the persisted entity.
     */
    TimeBandAvailableProfesionalDayDTO save(TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO);

    /**
     * Get all the timeBandAvailableProfesionalDays.
     *
     * @return the list of entities.
     */
    List<TimeBandAvailableProfesionalDayDTO> findAll();

    /**
     * Get the "id" timeBandAvailableProfesionalDay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeBandAvailableProfesionalDayDTO> findOne(Long id);

    /**
     * Delete the "id" timeBandAvailableProfesionalDay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package es.emilio.service;

import es.emilio.service.dto.CalendarYearUserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.CalendarYearUser}.
 */
public interface CalendarYearUserService {

    /**
     * Save a calendarYearUser.
     *
     * @param calendarYearUserDTO the entity to save.
     * @return the persisted entity.
     */
    CalendarYearUserDTO save(CalendarYearUserDTO calendarYearUserDTO);

    /**
     * Get all the calendarYearUsers.
     *
     * @return the list of entities.
     */
    List<CalendarYearUserDTO> findAll();

    /**
     * Get the "id" calendarYearUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalendarYearUserDTO> findOne(Long id);

    /**
     * Delete the "id" calendarYearUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

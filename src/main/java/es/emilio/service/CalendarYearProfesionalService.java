package es.emilio.service;

import es.emilio.service.dto.CalendarYearProfesionalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.CalendarYearProfesional}.
 */
public interface CalendarYearProfesionalService {

    /**
     * Save a calendarYearProfesional.
     *
     * @param calendarYearProfesionalDTO the entity to save.
     * @return the persisted entity.
     */
    CalendarYearProfesionalDTO save(CalendarYearProfesionalDTO calendarYearProfesionalDTO);

    /**
     * Get all the calendarYearProfesionals.
     *
     * @return the list of entities.
     */
    List<CalendarYearProfesionalDTO> findAll();

    /**
     * Get the "id" calendarYearProfesional.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalendarYearProfesionalDTO> findOne(Long id);

    /**
     * Delete the "id" calendarYearProfesional.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

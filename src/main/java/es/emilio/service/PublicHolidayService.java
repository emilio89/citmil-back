package es.emilio.service;

import es.emilio.service.dto.PublicHolidayDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.PublicHoliday}.
 */
public interface PublicHolidayService {

    /**
     * Save a publicHoliday.
     *
     * @param publicHolidayDTO the entity to save.
     * @return the persisted entity.
     */
    PublicHolidayDTO save(PublicHolidayDTO publicHolidayDTO);

    /**
     * Get all the publicHolidays.
     *
     * @return the list of entities.
     */
    List<PublicHolidayDTO> findAll();

    /**
     * Get the "id" publicHoliday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicHolidayDTO> findOne(Long id);

    /**
     * Delete the "id" publicHoliday.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package es.emilio.service;

import es.emilio.service.dto.TimeBandDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.TimeBand}.
 */
public interface TimeBandService {

    /**
     * Save a timeBand.
     *
     * @param timeBandDTO the entity to save.
     * @return the persisted entity.
     */
    TimeBandDTO save(TimeBandDTO timeBandDTO);

    /**
     * Get all the timeBands.
     *
     * @return the list of entities.
     */
    List<TimeBandDTO> findAll();

    /**
     * Get all the timeBands with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TimeBandDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" timeBand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeBandDTO> findOne(Long id);

    /**
     * Delete the "id" timeBand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

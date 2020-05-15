package es.emilio.service;

import es.emilio.service.dto.ProfesionalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.Profesional}.
 */
public interface ProfesionalService {

    /**
     * Save a profesional.
     *
     * @param profesionalDTO the entity to save.
     * @return the persisted entity.
     */
    ProfesionalDTO save(ProfesionalDTO profesionalDTO);

    /**
     * Get all the profesionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfesionalDTO> findAll(Pageable pageable);

    /**
     * Get all the profesionals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ProfesionalDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" profesional.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfesionalDTO> findOne(Long id);

    /**
     * Delete the "id" profesional.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

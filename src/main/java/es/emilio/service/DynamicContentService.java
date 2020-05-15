package es.emilio.service;

import es.emilio.service.dto.DynamicContentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.DynamicContent}.
 */
public interface DynamicContentService {

    /**
     * Save a dynamicContent.
     *
     * @param dynamicContentDTO the entity to save.
     * @return the persisted entity.
     */
    DynamicContentDTO save(DynamicContentDTO dynamicContentDTO);

    /**
     * Get all the dynamicContents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DynamicContentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dynamicContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DynamicContentDTO> findOne(Long id);

    /**
     * Delete the "id" dynamicContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

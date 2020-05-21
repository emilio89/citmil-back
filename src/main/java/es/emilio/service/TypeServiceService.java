package es.emilio.service;

import es.emilio.service.dto.TypeServiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.TypeService}.
 */
public interface TypeServiceService {

    /**
     * Save a typeService.
     *
     * @param typeServiceDTO the entity to save.
     * @return the persisted entity.
     */
    TypeServiceDTO save(TypeServiceDTO typeServiceDTO);

    /**
     * Get all the typeServices.
     *
     * @param pageable the pagination information.
     * @param companyId 
     * @return the list of entities.
     */
    Page<TypeServiceDTO> findAll(Pageable pageable, Long companyId);

    /**
     * Get the "id" typeService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeServiceDTO> findOne(Long id);

    /**
     * Delete the "id" typeService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

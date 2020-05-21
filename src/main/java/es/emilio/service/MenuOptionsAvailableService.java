package es.emilio.service;

import es.emilio.service.dto.MenuOptionsAvailableDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link es.emilio.domain.MenuOptionsAvailable}.
 */
public interface MenuOptionsAvailableService {

    /**
     * Save a menuOptionsAvailable.
     *
     * @param menuOptionsAvailableDTO the entity to save.
     * @return the persisted entity.
     */
    MenuOptionsAvailableDTO save(MenuOptionsAvailableDTO menuOptionsAvailableDTO);

    /**
     * Get all the menuOptionsAvailables.
     *
     * @return the list of entities.
     */
    List<MenuOptionsAvailableDTO> findAll();

    /**
     * Get the "id" menuOptionsAvailable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenuOptionsAvailableDTO> findOne(Long id);

    /**
     * Delete the "id" menuOptionsAvailable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package es.emilio.service.impl;

import es.emilio.service.MenuOptionsAvailableService;
import es.emilio.domain.MenuOptionsAvailable;
import es.emilio.repository.MenuOptionsAvailableRepository;
import es.emilio.service.dto.MenuOptionsAvailableDTO;
import es.emilio.service.mapper.MenuOptionsAvailableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MenuOptionsAvailable}.
 */
@Service
@Transactional
public class MenuOptionsAvailableServiceImpl implements MenuOptionsAvailableService {

    private final Logger log = LoggerFactory.getLogger(MenuOptionsAvailableServiceImpl.class);

    private final MenuOptionsAvailableRepository menuOptionsAvailableRepository;

    private final MenuOptionsAvailableMapper menuOptionsAvailableMapper;

    public MenuOptionsAvailableServiceImpl(MenuOptionsAvailableRepository menuOptionsAvailableRepository, MenuOptionsAvailableMapper menuOptionsAvailableMapper) {
        this.menuOptionsAvailableRepository = menuOptionsAvailableRepository;
        this.menuOptionsAvailableMapper = menuOptionsAvailableMapper;
    }

    /**
     * Save a menuOptionsAvailable.
     *
     * @param menuOptionsAvailableDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MenuOptionsAvailableDTO save(MenuOptionsAvailableDTO menuOptionsAvailableDTO) {
        log.debug("Request to save MenuOptionsAvailable : {}", menuOptionsAvailableDTO);
        MenuOptionsAvailable menuOptionsAvailable = menuOptionsAvailableMapper.toEntity(menuOptionsAvailableDTO);
        menuOptionsAvailable = menuOptionsAvailableRepository.save(menuOptionsAvailable);
        return menuOptionsAvailableMapper.toDto(menuOptionsAvailable);
    }

    /**
     * Get all the menuOptionsAvailables.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MenuOptionsAvailableDTO> findAll() {
        log.debug("Request to get all MenuOptionsAvailables");
        return menuOptionsAvailableRepository.findAll().stream()
            .map(menuOptionsAvailableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one menuOptionsAvailable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MenuOptionsAvailableDTO> findOne(Long id) {
        log.debug("Request to get MenuOptionsAvailable : {}", id);
        return menuOptionsAvailableRepository.findById(id)
            .map(menuOptionsAvailableMapper::toDto);
    }

    /**
     * Delete the menuOptionsAvailable by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MenuOptionsAvailable : {}", id);
        menuOptionsAvailableRepository.deleteById(id);
    }
}

package es.emilio.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import es.emilio.domain.MenuOptionsAvailable;
import es.emilio.domain.*; // for static metamodels
import es.emilio.repository.MenuOptionsAvailableRepository;
import es.emilio.service.dto.MenuOptionsAvailableCriteria;
import es.emilio.service.dto.MenuOptionsAvailableDTO;
import es.emilio.service.mapper.MenuOptionsAvailableMapper;

/**
 * Service for executing complex queries for {@link MenuOptionsAvailable} entities in the database.
 * The main input is a {@link MenuOptionsAvailableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MenuOptionsAvailableDTO} or a {@link Page} of {@link MenuOptionsAvailableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MenuOptionsAvailableQueryService extends QueryService<MenuOptionsAvailable> {

    private final Logger log = LoggerFactory.getLogger(MenuOptionsAvailableQueryService.class);

    private final MenuOptionsAvailableRepository menuOptionsAvailableRepository;

    private final MenuOptionsAvailableMapper menuOptionsAvailableMapper;

    public MenuOptionsAvailableQueryService(MenuOptionsAvailableRepository menuOptionsAvailableRepository, MenuOptionsAvailableMapper menuOptionsAvailableMapper) {
        this.menuOptionsAvailableRepository = menuOptionsAvailableRepository;
        this.menuOptionsAvailableMapper = menuOptionsAvailableMapper;
    }

    /**
     * Return a {@link List} of {@link MenuOptionsAvailableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MenuOptionsAvailableDTO> findByCriteria(MenuOptionsAvailableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MenuOptionsAvailable> specification = createSpecification(criteria);
        return menuOptionsAvailableMapper.toDto(menuOptionsAvailableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MenuOptionsAvailableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MenuOptionsAvailableDTO> findByCriteria(MenuOptionsAvailableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MenuOptionsAvailable> specification = createSpecification(criteria);
        return menuOptionsAvailableRepository.findAll(specification, page)
            .map(menuOptionsAvailableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MenuOptionsAvailableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MenuOptionsAvailable> specification = createSpecification(criteria);
        return menuOptionsAvailableRepository.count(specification);
    }

    /**
     * Function to convert {@link MenuOptionsAvailableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MenuOptionsAvailable> createSpecification(MenuOptionsAvailableCriteria criteria) {
        Specification<MenuOptionsAvailable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MenuOptionsAvailable_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), MenuOptionsAvailable_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MenuOptionsAvailable_.description));
            }
            if (criteria.getActived() != null) {
                specification = specification.and(buildSpecification(criteria.getActived(), MenuOptionsAvailable_.actived));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(MenuOptionsAvailable_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}

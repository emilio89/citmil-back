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

import es.emilio.domain.DynamicContent;
import es.emilio.domain.*; // for static metamodels
import es.emilio.repository.DynamicContentRepository;
import es.emilio.service.dto.DynamicContentCriteria;
import es.emilio.service.dto.DynamicContentDTO;
import es.emilio.service.mapper.DynamicContentMapper;

/**
 * Service for executing complex queries for {@link DynamicContent} entities in the database.
 * The main input is a {@link DynamicContentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DynamicContentDTO} or a {@link Page} of {@link DynamicContentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DynamicContentQueryService extends QueryService<DynamicContent> {

    private final Logger log = LoggerFactory.getLogger(DynamicContentQueryService.class);

    private final DynamicContentRepository dynamicContentRepository;

    private final DynamicContentMapper dynamicContentMapper;

    public DynamicContentQueryService(DynamicContentRepository dynamicContentRepository, DynamicContentMapper dynamicContentMapper) {
        this.dynamicContentRepository = dynamicContentRepository;
        this.dynamicContentMapper = dynamicContentMapper;
    }

    /**
     * Return a {@link List} of {@link DynamicContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DynamicContentDTO> findByCriteria(DynamicContentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DynamicContent> specification = createSpecification(criteria);
        return dynamicContentMapper.toDto(dynamicContentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DynamicContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DynamicContentDTO> findByCriteria(DynamicContentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DynamicContent> specification = createSpecification(criteria);
        return dynamicContentRepository.findAll(specification, page)
            .map(dynamicContentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DynamicContentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DynamicContent> specification = createSpecification(criteria);
        return dynamicContentRepository.count(specification);
    }

    /**
     * Function to convert {@link DynamicContentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DynamicContent> createSpecification(DynamicContentCriteria criteria) {
        Specification<DynamicContent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DynamicContent_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), DynamicContent_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DynamicContent_.description));
            }
            if (criteria.getActived() != null) {
                specification = specification.and(buildSpecification(criteria.getActived(), DynamicContent_.actived));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(DynamicContent_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}

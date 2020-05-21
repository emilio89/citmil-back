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

import es.emilio.domain.TimeBand;
import es.emilio.domain.*; // for static metamodels
import es.emilio.repository.TimeBandRepository;
import es.emilio.service.dto.TimeBandCriteria;
import es.emilio.service.dto.TimeBandDTO;
import es.emilio.service.mapper.TimeBandMapper;

/**
 * Service for executing complex queries for {@link TimeBand} entities in the database.
 * The main input is a {@link TimeBandCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TimeBandDTO} or a {@link Page} of {@link TimeBandDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TimeBandQueryService extends QueryService<TimeBand> {

    private final Logger log = LoggerFactory.getLogger(TimeBandQueryService.class);

    private final TimeBandRepository timeBandRepository;

    private final TimeBandMapper timeBandMapper;

    public TimeBandQueryService(TimeBandRepository timeBandRepository, TimeBandMapper timeBandMapper) {
        this.timeBandRepository = timeBandRepository;
        this.timeBandMapper = timeBandMapper;
    }

    /**
     * Return a {@link List} of {@link TimeBandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TimeBandDTO> findByCriteria(TimeBandCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TimeBand> specification = createSpecification(criteria);
        return timeBandMapper.toDto(timeBandRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TimeBandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TimeBandDTO> findByCriteria(TimeBandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TimeBand> specification = createSpecification(criteria);
        return timeBandRepository.findAll(specification, page)
            .map(timeBandMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TimeBandCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TimeBand> specification = createSpecification(criteria);
        return timeBandRepository.count(specification);
    }

    /**
     * Function to convert {@link TimeBandCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TimeBand> createSpecification(TimeBandCriteria criteria) {
        Specification<TimeBand> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TimeBand_.id));
            }
            if (criteria.getStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStart(), TimeBand_.start));
            }
            if (criteria.getEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnd(), TimeBand_.end));
            }
            if (criteria.getCalendarYearUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCalendarYearUserId(),
                    root -> root.join(TimeBand_.calendarYearUsers, JoinType.LEFT).get(CalendarYearUser_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(TimeBand_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}

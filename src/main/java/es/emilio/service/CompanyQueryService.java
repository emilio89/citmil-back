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

import es.emilio.domain.Company;
import es.emilio.domain.*; // for static metamodels
import es.emilio.repository.CompanyRepository;
import es.emilio.service.dto.CompanyCriteria;
import es.emilio.service.dto.CompanyDTO;
import es.emilio.service.mapper.CompanyMapper;

/**
 * Service for executing complex queries for {@link Company} entities in the database.
 * The main input is a {@link CompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyDTO} or a {@link Page} of {@link CompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

    private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyQueryService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByCriteria(CompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyMapper.toDto(companyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findByCriteria(CompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.findAll(specification, page)
            .map(companyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Company> createSpecification(CompanyCriteria criteria) {
        Specification<Company> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Company_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Company_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Company_.description));
            }
            if (criteria.getPrimaryColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrimaryColor(), Company_.primaryColor));
            }
            if (criteria.getSecondaryColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecondaryColor(), Company_.secondaryColor));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Company_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Company_.phone));
            }
            if (criteria.getMaxDayAppointment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxDayAppointment(), Company_.maxDayAppointment));
            }
            if (criteria.getMinDayAppointment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinDayAppointment(), Company_.minDayAppointment));
            }
            if (criteria.getLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLat(), Company_.lat));
            }
            if (criteria.getLng() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLng(), Company_.lng));
            }
            if (criteria.getProfesionalId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfesionalId(),
                    root -> root.join(Company_.profesionals, JoinType.LEFT).get(Profesional_.id)));
            }
            if (criteria.getCalendarYearProfesionalId() != null) {
                specification = specification.and(buildSpecification(criteria.getCalendarYearProfesionalId(),
                    root -> root.join(Company_.calendarYearProfesionals, JoinType.LEFT).get(CalendarYearProfesional_.id)));
            }
            if (criteria.getAppointmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppointmentId(),
                    root -> root.join(Company_.appointments, JoinType.LEFT).get(Appointment_.id)));
            }
            if (criteria.getTypeServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeServiceId(),
                    root -> root.join(Company_.typeServices, JoinType.LEFT).get(TypeService_.id)));
            }
            if (criteria.getPublicHolidayId() != null) {
                specification = specification.and(buildSpecification(criteria.getPublicHolidayId(),
                    root -> root.join(Company_.publicHolidays, JoinType.LEFT).get(PublicHoliday_.id)));
            }
            if (criteria.getTimeBandId() != null) {
                specification = specification.and(buildSpecification(criteria.getTimeBandId(),
                    root -> root.join(Company_.timeBands, JoinType.LEFT).get(TimeBand_.id)));
            }
            if (criteria.getDynamicContentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDynamicContentId(),
                    root -> root.join(Company_.dynamicContents, JoinType.LEFT).get(DynamicContent_.id)));
            }
            if (criteria.getMenuOptionsAvailableId() != null) {
                specification = specification.and(buildSpecification(criteria.getMenuOptionsAvailableId(),
                    root -> root.join(Company_.menuOptionsAvailables, JoinType.LEFT).get(MenuOptionsAvailable_.id)));
            }
            if (criteria.getTimeBandAvailableProfesionalDayId() != null) {
                specification = specification.and(buildSpecification(criteria.getTimeBandAvailableProfesionalDayId(),
                    root -> root.join(Company_.timeBandAvailableProfesionalDays, JoinType.LEFT).get(TimeBandAvailableProfesionalDay_.id)));
            }
        }
        return specification;
    }
}

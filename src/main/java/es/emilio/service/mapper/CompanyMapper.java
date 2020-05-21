package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {


    @Mapping(target = "userExtras", ignore = true)
    @Mapping(target = "removeUserExtra", ignore = true)
    @Mapping(target = "calendarYearUsers", ignore = true)
    @Mapping(target = "removeCalendarYearUser", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "removeAppointment", ignore = true)
    @Mapping(target = "typeServices", ignore = true)
    @Mapping(target = "removeTypeService", ignore = true)
    @Mapping(target = "publicHolidays", ignore = true)
    @Mapping(target = "removePublicHoliday", ignore = true)
    @Mapping(target = "timeBands", ignore = true)
    @Mapping(target = "removeTimeBand", ignore = true)
    @Mapping(target = "dynamicContents", ignore = true)
    @Mapping(target = "removeDynamicContent", ignore = true)
    @Mapping(target = "menuOptionsAvailables", ignore = true)
    @Mapping(target = "removeMenuOptionsAvailable", ignore = true)
    @Mapping(target = "timeBandAvailableUserDays", ignore = true)
    @Mapping(target = "removeTimeBandAvailableUserDay", ignore = true)
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}

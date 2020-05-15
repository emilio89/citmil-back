package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.CalendarYearProfesionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalendarYearProfesional} and its DTO {@link CalendarYearProfesionalDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, TimeBandAvailableProfesionalDayMapper.class})
public interface CalendarYearProfesionalMapper extends EntityMapper<CalendarYearProfesionalDTO, CalendarYearProfesional> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "timeBandAvailableProfesionalDay.id", target = "timeBandAvailableProfesionalDayId")
    CalendarYearProfesionalDTO toDto(CalendarYearProfesional calendarYearProfesional);

    @Mapping(target = "profesionals", ignore = true)
    @Mapping(target = "removeProfesional", ignore = true)
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "timeBandAvailableProfesionalDayId", target = "timeBandAvailableProfesionalDay")
    @Mapping(target = "timeBands", ignore = true)
    @Mapping(target = "removeTimeBand", ignore = true)
    CalendarYearProfesional toEntity(CalendarYearProfesionalDTO calendarYearProfesionalDTO);

    default CalendarYearProfesional fromId(Long id) {
        if (id == null) {
            return null;
        }
        CalendarYearProfesional calendarYearProfesional = new CalendarYearProfesional();
        calendarYearProfesional.setId(id);
        return calendarYearProfesional;
    }
}

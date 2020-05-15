package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.TimeBandAvailableProfesionalDayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeBandAvailableProfesionalDay} and its DTO {@link TimeBandAvailableProfesionalDayDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface TimeBandAvailableProfesionalDayMapper extends EntityMapper<TimeBandAvailableProfesionalDayDTO, TimeBandAvailableProfesionalDay> {

    @Mapping(source = "company.id", target = "companyId")
    TimeBandAvailableProfesionalDayDTO toDto(TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay);

    @Mapping(target = "calendarYearProfesionals", ignore = true)
    @Mapping(target = "removeCalendarYearProfesional", ignore = true)
    @Mapping(target = "profesionals", ignore = true)
    @Mapping(target = "removeProfesional", ignore = true)
    @Mapping(source = "companyId", target = "company")
    TimeBandAvailableProfesionalDay toEntity(TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO);

    default TimeBandAvailableProfesionalDay fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay = new TimeBandAvailableProfesionalDay();
        timeBandAvailableProfesionalDay.setId(id);
        return timeBandAvailableProfesionalDay;
    }
}

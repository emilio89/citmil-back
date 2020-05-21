package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.CalendarYearUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalendarYearUser} and its DTO {@link CalendarYearUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, TimeBandAvailableUserDayMapper.class})
public interface CalendarYearUserMapper extends EntityMapper<CalendarYearUserDTO, CalendarYearUser> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "timeBandAvailableUserDay.id", target = "timeBandAvailableUserDayId")
    CalendarYearUserDTO toDto(CalendarYearUser calendarYearUser);

    @Mapping(target = "userExtras", ignore = true)
    @Mapping(target = "removeUserExtra", ignore = true)
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "timeBandAvailableUserDayId", target = "timeBandAvailableUserDay")
    @Mapping(target = "timeBands", ignore = true)
    @Mapping(target = "removeTimeBand", ignore = true)
    CalendarYearUser toEntity(CalendarYearUserDTO calendarYearUserDTO);

    default CalendarYearUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        CalendarYearUser calendarYearUser = new CalendarYearUser();
        calendarYearUser.setId(id);
        return calendarYearUser;
    }
}

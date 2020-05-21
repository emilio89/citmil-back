package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.TimeBandAvailableUserDayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeBandAvailableUserDay} and its DTO {@link TimeBandAvailableUserDayDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface TimeBandAvailableUserDayMapper extends EntityMapper<TimeBandAvailableUserDayDTO, TimeBandAvailableUserDay> {

    @Mapping(source = "company.id", target = "companyId")
    TimeBandAvailableUserDayDTO toDto(TimeBandAvailableUserDay timeBandAvailableUserDay);

    @Mapping(target = "calendarYearUsers", ignore = true)
    @Mapping(target = "removeCalendarYearUser", ignore = true)
    @Mapping(target = "userExtras", ignore = true)
    @Mapping(target = "removeUserExtra", ignore = true)
    @Mapping(source = "companyId", target = "company")
    TimeBandAvailableUserDay toEntity(TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO);

    default TimeBandAvailableUserDay fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimeBandAvailableUserDay timeBandAvailableUserDay = new TimeBandAvailableUserDay();
        timeBandAvailableUserDay.setId(id);
        return timeBandAvailableUserDay;
    }
}

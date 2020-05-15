package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.TimeBandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeBand} and its DTO {@link TimeBandDTO}.
 */
@Mapper(componentModel = "spring", uses = {CalendarYearProfesionalMapper.class, CompanyMapper.class})
public interface TimeBandMapper extends EntityMapper<TimeBandDTO, TimeBand> {

    @Mapping(source = "company.id", target = "companyId")
    TimeBandDTO toDto(TimeBand timeBand);

    @Mapping(target = "removeCalendarYearProfesional", ignore = true)
    @Mapping(source = "companyId", target = "company")
    TimeBand toEntity(TimeBandDTO timeBandDTO);

    default TimeBand fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimeBand timeBand = new TimeBand();
        timeBand.setId(id);
        return timeBand;
    }
}

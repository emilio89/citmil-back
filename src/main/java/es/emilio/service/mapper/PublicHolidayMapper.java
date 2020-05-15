package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.PublicHolidayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicHoliday} and its DTO {@link PublicHolidayDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface PublicHolidayMapper extends EntityMapper<PublicHolidayDTO, PublicHoliday> {

    @Mapping(source = "company.id", target = "companyId")
    PublicHolidayDTO toDto(PublicHoliday publicHoliday);

    @Mapping(source = "companyId", target = "company")
    PublicHoliday toEntity(PublicHolidayDTO publicHolidayDTO);

    default PublicHoliday fromId(Long id) {
        if (id == null) {
            return null;
        }
        PublicHoliday publicHoliday = new PublicHoliday();
        publicHoliday.setId(id);
        return publicHoliday;
    }
}

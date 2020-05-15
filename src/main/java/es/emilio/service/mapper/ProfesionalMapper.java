package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.ProfesionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profesional} and its DTO {@link ProfesionalDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeServiceMapper.class, CalendarYearProfesionalMapper.class, CompanyMapper.class, TimeBandAvailableProfesionalDayMapper.class})
public interface ProfesionalMapper extends EntityMapper<ProfesionalDTO, Profesional> {

    @Mapping(source = "calendarYearProfesional.id", target = "calendarYearProfesionalId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "timeBandAvailableProfesionalDay.id", target = "timeBandAvailableProfesionalDayId")
    ProfesionalDTO toDto(Profesional profesional);

    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "removeAppointment", ignore = true)
    @Mapping(target = "removeTypeService", ignore = true)
    @Mapping(source = "calendarYearProfesionalId", target = "calendarYearProfesional")
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "timeBandAvailableProfesionalDayId", target = "timeBandAvailableProfesionalDay")
    Profesional toEntity(ProfesionalDTO profesionalDTO);

    default Profesional fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profesional profesional = new Profesional();
        profesional.setId(id);
        return profesional;
    }
}

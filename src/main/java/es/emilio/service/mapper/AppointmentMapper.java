package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.AppointmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appointment} and its DTO {@link AppointmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfesionalMapper.class, CompanyMapper.class})
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {

    @Mapping(source = "profesional.id", target = "profesionalId")
    @Mapping(source = "company.id", target = "companyId")
    AppointmentDTO toDto(Appointment appointment);

    @Mapping(source = "profesionalId", target = "profesional")
    @Mapping(source = "companyId", target = "company")
    Appointment toEntity(AppointmentDTO appointmentDTO);

    default Appointment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}

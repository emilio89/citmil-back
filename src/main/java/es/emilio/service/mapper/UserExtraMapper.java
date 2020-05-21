package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.UserExtraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserExtra} and its DTO {@link UserExtraDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TypeServiceMapper.class, CalendarYearUserMapper.class, CompanyMapper.class, TimeBandAvailableUserDayMapper.class})
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "calendarYearUser.id", target = "calendarYearUserId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "timeBandAvailableUserDay.id", target = "timeBandAvailableUserDayId")
    UserExtraDTO toDto(UserExtra userExtra);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "removeAppointment", ignore = true)
    @Mapping(target = "removeTypeService", ignore = true)
    @Mapping(source = "calendarYearUserId", target = "calendarYearUser")
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "timeBandAvailableUserDayId", target = "timeBandAvailableUserDay")
    UserExtra toEntity(UserExtraDTO userExtraDTO);

    default UserExtra fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExtra userExtra = new UserExtra();
        userExtra.setId(id);
        return userExtra;
    }
}

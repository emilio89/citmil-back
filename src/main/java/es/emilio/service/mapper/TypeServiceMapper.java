package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.TypeServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeService} and its DTO {@link TypeServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface TypeServiceMapper extends EntityMapper<TypeServiceDTO, TypeService> {

    @Mapping(source = "company.id", target = "companyId")
    TypeServiceDTO toDto(TypeService typeService);

    @Mapping(source = "companyId", target = "company")
    @Mapping(target = "userExtras", ignore = true)
    @Mapping(target = "removeUserExtra", ignore = true)
    TypeService toEntity(TypeServiceDTO typeServiceDTO);

    default TypeService fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeService typeService = new TypeService();
        typeService.setId(id);
        return typeService;
    }
}

package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.MenuOptionsAvailableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MenuOptionsAvailable} and its DTO {@link MenuOptionsAvailableDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface MenuOptionsAvailableMapper extends EntityMapper<MenuOptionsAvailableDTO, MenuOptionsAvailable> {

    @Mapping(source = "company.id", target = "companyId")
    MenuOptionsAvailableDTO toDto(MenuOptionsAvailable menuOptionsAvailable);

    @Mapping(source = "companyId", target = "company")
    MenuOptionsAvailable toEntity(MenuOptionsAvailableDTO menuOptionsAvailableDTO);

    default MenuOptionsAvailable fromId(Long id) {
        if (id == null) {
            return null;
        }
        MenuOptionsAvailable menuOptionsAvailable = new MenuOptionsAvailable();
        menuOptionsAvailable.setId(id);
        return menuOptionsAvailable;
    }
}

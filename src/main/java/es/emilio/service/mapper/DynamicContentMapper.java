package es.emilio.service.mapper;


import es.emilio.domain.*;
import es.emilio.service.dto.DynamicContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DynamicContent} and its DTO {@link DynamicContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface DynamicContentMapper extends EntityMapper<DynamicContentDTO, DynamicContent> {

    @Mapping(source = "company.id", target = "companyId")
    DynamicContentDTO toDto(DynamicContent dynamicContent);

    @Mapping(source = "companyId", target = "company")
    DynamicContent toEntity(DynamicContentDTO dynamicContentDTO);

    default DynamicContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        DynamicContent dynamicContent = new DynamicContent();
        dynamicContent.setId(id);
        return dynamicContent;
    }
}

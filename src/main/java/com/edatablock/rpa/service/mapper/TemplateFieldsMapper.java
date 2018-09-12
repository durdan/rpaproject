package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.TemplateFieldsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TemplateFields and its DTO TemplateFieldsDTO.
 */
@Mapper(componentModel = "spring", uses = {InputTemplateMapper.class})
public interface TemplateFieldsMapper extends EntityMapper<TemplateFieldsDTO, TemplateFields> {

    @Mapping(source = "inputTemplate.id", target = "inputTemplateId")
    TemplateFieldsDTO toDto(TemplateFields templateFields);

    @Mapping(source = "inputTemplateId", target = "inputTemplate")
    TemplateFields toEntity(TemplateFieldsDTO templateFieldsDTO);

    default TemplateFields fromId(Long id) {
        if (id == null) {
            return null;
        }
        TemplateFields templateFields = new TemplateFields();
        templateFields.setId(id);
        return templateFields;
    }
}

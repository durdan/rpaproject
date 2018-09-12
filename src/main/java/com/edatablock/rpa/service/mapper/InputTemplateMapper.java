package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.InputTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InputTemplate and its DTO InputTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface InputTemplateMapper extends EntityMapper<InputTemplateDTO, InputTemplate> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.clientEmailAddress", target = "clientClientEmailAddress")
    InputTemplateDTO toDto(InputTemplate inputTemplate);

    @Mapping(source = "clientId", target = "client")
    InputTemplate toEntity(InputTemplateDTO inputTemplateDTO);

    default InputTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        InputTemplate inputTemplate = new InputTemplate();
        inputTemplate.setId(id);
        return inputTemplate;
    }
}

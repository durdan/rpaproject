package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.OutputTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OutputTemplate and its DTO OutputTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, ClientDataOcrMapper.class})
public interface OutputTemplateMapper extends EntityMapper<OutputTemplateDTO, OutputTemplate> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "clientDataOcr.id", target = "clientDataOcrId")
    OutputTemplateDTO toDto(OutputTemplate outputTemplate);

    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "clientDataOcrId", target = "clientDataOcr")
    OutputTemplate toEntity(OutputTemplateDTO outputTemplateDTO);

    default OutputTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        OutputTemplate outputTemplate = new OutputTemplate();
        outputTemplate.setId(id);
        return outputTemplate;
    }
}

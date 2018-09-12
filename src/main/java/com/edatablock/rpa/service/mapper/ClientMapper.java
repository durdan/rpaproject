package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {

    @Mapping(source = "orgName.id", target = "orgNameId")
    @Mapping(source = "orgName.orgName", target = "orgNameOrgName")
    ClientDTO toDto(Client client);

    @Mapping(target = "outputTemplates", ignore = true)
    @Mapping(source = "orgNameId", target = "orgName")
    @Mapping(target = "inputTemplates", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}

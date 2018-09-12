package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.ClientEmailDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClientEmailDomain and its DTO ClientEmailDomainDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ClientEmailDomainMapper extends EntityMapper<ClientEmailDomainDTO, ClientEmailDomain> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.clientName", target = "clientClientName")
    ClientEmailDomainDTO toDto(ClientEmailDomain clientEmailDomain);

    @Mapping(source = "clientId", target = "client")
    ClientEmailDomain toEntity(ClientEmailDomainDTO clientEmailDomainDTO);

    default ClientEmailDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientEmailDomain clientEmailDomain = new ClientEmailDomain();
        clientEmailDomain.setId(id);
        return clientEmailDomain;
    }
}

package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.ClientDataOcrDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClientDataOcr and its DTO ClientDataOcrDTO.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface ClientDataOcrMapper extends EntityMapper<ClientDataOcrDTO, ClientDataOcr> {

    @Mapping(source = "transaction.id", target = "transactionId")
    ClientDataOcrDTO toDto(ClientDataOcr clientDataOcr);

    @Mapping(target = "outputTemplates", ignore = true)
    @Mapping(source = "transactionId", target = "transaction")
    ClientDataOcr toEntity(ClientDataOcrDTO clientDataOcrDTO);

    default ClientDataOcr fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientDataOcr clientDataOcr = new ClientDataOcr();
        clientDataOcr.setId(id);
        return clientDataOcr;
    }
}

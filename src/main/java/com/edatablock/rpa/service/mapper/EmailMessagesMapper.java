package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.EmailMessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmailMessages and its DTO EmailMessagesDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface EmailMessagesMapper extends EntityMapper<EmailMessagesDTO, EmailMessages> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.clientEmailAddress", target = "clientClientEmailAddress")
    EmailMessagesDTO toDto(EmailMessages emailMessages);

    @Mapping(target = "emailAttachments", ignore = true)
    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "emailProcessingError", ignore = true)
    EmailMessages toEntity(EmailMessagesDTO emailMessagesDTO);

    default EmailMessages fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailMessages emailMessages = new EmailMessages();
        emailMessages.setId(id);
        return emailMessages;
    }
}

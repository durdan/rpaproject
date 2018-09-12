package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.EmailProcessingErrorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmailProcessingError and its DTO EmailProcessingErrorDTO.
 */
@Mapper(componentModel = "spring", uses = {EmailMessagesMapper.class})
public interface EmailProcessingErrorMapper extends EntityMapper<EmailProcessingErrorDTO, EmailProcessingError> {

    @Mapping(source = "emailMessages.id", target = "emailMessagesId")
    EmailProcessingErrorDTO toDto(EmailProcessingError emailProcessingError);

    @Mapping(source = "emailMessagesId", target = "emailMessages")
    EmailProcessingError toEntity(EmailProcessingErrorDTO emailProcessingErrorDTO);

    default EmailProcessingError fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailProcessingError emailProcessingError = new EmailProcessingError();
        emailProcessingError.setId(id);
        return emailProcessingError;
    }
}

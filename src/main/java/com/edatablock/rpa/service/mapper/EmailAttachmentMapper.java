package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.EmailAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmailAttachment and its DTO EmailAttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {EmailMessagesMapper.class})
public interface EmailAttachmentMapper extends EntityMapper<EmailAttachmentDTO, EmailAttachment> {

    @Mapping(source = "emailMessages.id", target = "emailMessagesId")
    @Mapping(source = "emailMessages.messageId", target = "emailMessagesMessageId")
    EmailAttachmentDTO toDto(EmailAttachment emailAttachment);

    @Mapping(target = "fileForOCRProcessing", ignore = true)
    @Mapping(source = "emailMessagesId", target = "emailMessages")
    EmailAttachment toEntity(EmailAttachmentDTO emailAttachmentDTO);

    default EmailAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailAttachment emailAttachment = new EmailAttachment();
        emailAttachment.setId(id);
        return emailAttachment;
    }
}

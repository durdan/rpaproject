package com.edatablock.rpa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmailAttachment entity.
 */
public class EmailAttachmentDTO implements Serializable {

    private Long id;

    private String messageId;

    private String clientEmailAddress;

    @NotNull
    private String fileName;

    @NotNull
    private String fileExtension;

    private String fileLocation;

    private Long emailMessagesId;

    private String emailMessagesMessageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Long getEmailMessagesId() {
        return emailMessagesId;
    }

    public void setEmailMessagesId(Long emailMessagesId) {
        this.emailMessagesId = emailMessagesId;
    }

    public String getEmailMessagesMessageId() {
        return emailMessagesMessageId;
    }

    public void setEmailMessagesMessageId(String emailMessagesMessageId) {
        this.emailMessagesMessageId = emailMessagesMessageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailAttachmentDTO emailAttachmentDTO = (EmailAttachmentDTO) o;
        if (emailAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailAttachmentDTO{" +
            "id=" + getId() +
            ", messageId='" + getMessageId() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", fileLocation='" + getFileLocation() + "'" +
            ", emailMessages=" + getEmailMessagesId() +
            ", emailMessages='" + getEmailMessagesMessageId() + "'" +
            "}";
    }
}

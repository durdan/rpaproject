package com.edatablock.rpa.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmailProcessingError entity.
 */
public class EmailProcessingErrorDTO implements Serializable {

    private Long id;

    private String errorMessage;

    @NotNull
    private String messageID;

    private String clientEmailAddress;

    @NotNull
    private String receiveFrom;

    private Instant receivedTime;

    private Long emailMessagesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getReceiveFrom() {
        return receiveFrom;
    }

    public void setReceiveFrom(String receiveFrom) {
        this.receiveFrom = receiveFrom;
    }

    public Instant getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Instant receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Long getEmailMessagesId() {
        return emailMessagesId;
    }

    public void setEmailMessagesId(Long emailMessagesId) {
        this.emailMessagesId = emailMessagesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailProcessingErrorDTO emailProcessingErrorDTO = (EmailProcessingErrorDTO) o;
        if (emailProcessingErrorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailProcessingErrorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailProcessingErrorDTO{" +
            "id=" + getId() +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", messageID='" + getMessageID() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", receiveFrom='" + getReceiveFrom() + "'" +
            ", receivedTime='" + getReceivedTime() + "'" +
            ", emailMessages=" + getEmailMessagesId() +
            "}";
    }
}

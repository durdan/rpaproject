package com.edatablock.rpa.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmailMessages entity.
 */
public class EmailMessagesDTO implements Serializable {

    private Long id;

    @NotNull
    private String messageId;

    @NotNull
    private String emailSubject;

    private String emailBody;

    private String status;

    private String clientEmailAddress;

    @NotNull
    private String receiveFrom;

    @NotNull
    private Instant receivedTime;

    @NotNull
    private Integer numberOfAttachments;

    @NotNull
    private String attachments;

    private Long clientId;

    private String clientClientEmailAddress;

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

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getNumberOfAttachments() {
        return numberOfAttachments;
    }

    public void setNumberOfAttachments(Integer numberOfAttachments) {
        this.numberOfAttachments = numberOfAttachments;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientClientEmailAddress() {
        return clientClientEmailAddress;
    }

    public void setClientClientEmailAddress(String clientClientEmailAddress) {
        this.clientClientEmailAddress = clientClientEmailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailMessagesDTO emailMessagesDTO = (EmailMessagesDTO) o;
        if (emailMessagesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailMessagesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailMessagesDTO{" +
            "id=" + getId() +
            ", messageId='" + getMessageId() + "'" +
            ", emailSubject='" + getEmailSubject() + "'" +
            ", emailBody='" + getEmailBody() + "'" +
            ", status='" + getStatus() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", receiveFrom='" + getReceiveFrom() + "'" +
            ", receivedTime='" + getReceivedTime() + "'" +
            ", numberOfAttachments=" + getNumberOfAttachments() +
            ", attachments='" + getAttachments() + "'" +
            ", client=" + getClientId() +
            ", client='" + getClientClientEmailAddress() + "'" +
            "}";
    }
}

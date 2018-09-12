package com.edatablock.rpa.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OcrProcessingError entity.
 */
public class OcrProcessingErrorDTO implements Serializable {

    private Long id;

    private String clientEmailAddress;

    private String messageId;

    private Integer attachmentId;

    private String fileId;

    private String errorMessage;

    private Instant createdDateTime;

    private String errorType;

    private Long transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OcrProcessingErrorDTO ocrProcessingErrorDTO = (OcrProcessingErrorDTO) o;
        if (ocrProcessingErrorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ocrProcessingErrorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OcrProcessingErrorDTO{" +
            "id=" + getId() +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", attachmentId=" + getAttachmentId() +
            ", fileId='" + getFileId() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", errorType='" + getErrorType() + "'" +
            ", transaction=" + getTransactionId() +
            "}";
    }
}

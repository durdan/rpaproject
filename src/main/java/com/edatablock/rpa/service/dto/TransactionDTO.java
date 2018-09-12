package com.edatablock.rpa.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Transaction entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    private Instant createdDateTime;

    private String status;

    @NotNull
    private String clientEmailAddress;

    @NotNull
    private String messageId;

    @NotNull
    private String fileName;

    private String processType;

    private Instant createDate;

    private String createdBy;

    private Instant updateDate;

    private String updatedBy;

    private Long fileForOCRProcessingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getFileForOCRProcessingId() {
        return fileForOCRProcessingId;
    }

    public void setFileForOCRProcessingId(Long fileForOCRProcessingId) {
        this.fileForOCRProcessingId = fileForOCRProcessingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (transactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", processType='" + getProcessType() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", fileForOCRProcessing=" + getFileForOCRProcessingId() +
            "}";
    }
}

package com.edatablock.rpa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A OcrProcessingError.
 */
@Entity
@Table(name = "ocr_processing_error")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OcrProcessingError implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "attachment_id")
    private Integer attachmentId;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_date_time")
    private Instant createdDateTime;

    @Column(name = "error_type")
    private String errorType;

    @OneToOne
    @JoinColumn(unique = true)
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public OcrProcessingError clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getMessageId() {
        return messageId;
    }

    public OcrProcessingError messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public OcrProcessingError attachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
        return this;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileId() {
        return fileId;
    }

    public OcrProcessingError fileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public OcrProcessingError errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public OcrProcessingError createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getErrorType() {
        return errorType;
    }

    public OcrProcessingError errorType(String errorType) {
        this.errorType = errorType;
        return this;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public OcrProcessingError transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OcrProcessingError ocrProcessingError = (OcrProcessingError) o;
        if (ocrProcessingError.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ocrProcessingError.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OcrProcessingError{" +
            "id=" + getId() +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", attachmentId=" + getAttachmentId() +
            ", fileId='" + getFileId() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", errorType='" + getErrorType() + "'" +
            "}";
    }
}

package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A FileForOCRProcessing.
 */
@Entity
@Table(name = "file_for_ocr_processing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileForOCRProcessing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "file_input_type", nullable = false)
    private String fileInputType;

    @Column(name = "status")
    private String status;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @Column(name = "retry")
    private String retry;

    @Column(name = "created_date_time")
    private Instant createdDateTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "update_time_stamp")
    private Instant updateTimeStamp;

    @Column(name = "update_by")
    private Instant updateBy;

    @OneToOne
    @JoinColumn(unique = true)
    private EmailAttachment emailAttachment;

    @OneToOne
    @JoinColumn(unique = true)
    private UploadFiles uploadFiles;

    @OneToOne(mappedBy = "fileForOCRProcessing")
    @JsonIgnore
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileInputType() {
        return fileInputType;
    }

    public FileForOCRProcessing fileInputType(String fileInputType) {
        this.fileInputType = fileInputType;
        return this;
    }

    public void setFileInputType(String fileInputType) {
        this.fileInputType = fileInputType;
    }

    public String getStatus() {
        return status;
    }

    public FileForOCRProcessing status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public FileForOCRProcessing messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public FileForOCRProcessing clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getRetry() {
        return retry;
    }

    public FileForOCRProcessing retry(String retry) {
        this.retry = retry;
        return this;
    }

    public void setRetry(String retry) {
        this.retry = retry;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public FileForOCRProcessing createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public FileForOCRProcessing createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public FileForOCRProcessing updateTimeStamp(Instant updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
        return this;
    }

    public void setUpdateTimeStamp(Instant updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public Instant getUpdateBy() {
        return updateBy;
    }

    public FileForOCRProcessing updateBy(Instant updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Instant updateBy) {
        this.updateBy = updateBy;
    }

    public EmailAttachment getEmailAttachment() {
        return emailAttachment;
    }

    public FileForOCRProcessing emailAttachment(EmailAttachment emailAttachment) {
        this.emailAttachment = emailAttachment;
        return this;
    }

    public void setEmailAttachment(EmailAttachment emailAttachment) {
        this.emailAttachment = emailAttachment;
    }

    public UploadFiles getUploadFiles() {
        return uploadFiles;
    }

    public FileForOCRProcessing uploadFiles(UploadFiles uploadFiles) {
        this.uploadFiles = uploadFiles;
        return this;
    }

    public void setUploadFiles(UploadFiles uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public FileForOCRProcessing transaction(Transaction transaction) {
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
        FileForOCRProcessing fileForOCRProcessing = (FileForOCRProcessing) o;
        if (fileForOCRProcessing.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileForOCRProcessing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileForOCRProcessing{" +
            "id=" + getId() +
            ", fileInputType='" + getFileInputType() + "'" +
            ", status='" + getStatus() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", retry='" + getRetry() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updateTimeStamp='" + getUpdateTimeStamp() + "'" +
            ", updateBy='" + getUpdateBy() + "'" +
            "}";
    }
}

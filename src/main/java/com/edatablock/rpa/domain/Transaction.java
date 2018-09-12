package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time")
    private Instant createdDateTime;

    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "client_email_address", nullable = false)
    private String clientEmailAddress;

    @NotNull
    @Column(name = "message_id", nullable = false)
    private String messageId;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "process_type")
    private String processType;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToOne
    @JoinColumn(unique = true)
    private FileForOCRProcessing fileForOCRProcessing;

    @OneToOne(mappedBy = "transaction")
    @JsonIgnore
    private OcrProcessingError ocrProcessingError;

    @OneToMany(mappedBy = "transaction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ClientDataOcr> clientDataOcrs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public Transaction createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getStatus() {
        return status;
    }

    public Transaction status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public Transaction clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getMessageId() {
        return messageId;
    }

    public Transaction messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFileName() {
        return fileName;
    }

    public Transaction fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProcessType() {
        return processType;
    }

    public Transaction processType(String processType) {
        this.processType = processType;
        return this;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Transaction createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Transaction createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Transaction updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Transaction updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public FileForOCRProcessing getFileForOCRProcessing() {
        return fileForOCRProcessing;
    }

    public Transaction fileForOCRProcessing(FileForOCRProcessing fileForOCRProcessing) {
        this.fileForOCRProcessing = fileForOCRProcessing;
        return this;
    }

    public void setFileForOCRProcessing(FileForOCRProcessing fileForOCRProcessing) {
        this.fileForOCRProcessing = fileForOCRProcessing;
    }

    public OcrProcessingError getOcrProcessingError() {
        return ocrProcessingError;
    }

    public Transaction ocrProcessingError(OcrProcessingError ocrProcessingError) {
        this.ocrProcessingError = ocrProcessingError;
        return this;
    }

    public void setOcrProcessingError(OcrProcessingError ocrProcessingError) {
        this.ocrProcessingError = ocrProcessingError;
    }

    public Set<ClientDataOcr> getClientDataOcrs() {
        return clientDataOcrs;
    }

    public Transaction clientDataOcrs(Set<ClientDataOcr> clientDataOcrs) {
        this.clientDataOcrs = clientDataOcrs;
        return this;
    }

    public Transaction addClientDataOcr(ClientDataOcr clientDataOcr) {
        this.clientDataOcrs.add(clientDataOcr);
        clientDataOcr.setTransaction(this);
        return this;
    }

    public Transaction removeClientDataOcr(ClientDataOcr clientDataOcr) {
        this.clientDataOcrs.remove(clientDataOcr);
        clientDataOcr.setTransaction(null);
        return this;
    }

    public void setClientDataOcrs(Set<ClientDataOcr> clientDataOcrs) {
        this.clientDataOcrs = clientDataOcrs;
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
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
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
            "}";
    }
}

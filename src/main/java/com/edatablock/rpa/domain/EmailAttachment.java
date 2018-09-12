package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EmailAttachment.
 */
@Entity
@Table(name = "email_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "file_extension", nullable = false)
    private String fileExtension;

    @Column(name = "file_location")
    private String fileLocation;

    @OneToOne(mappedBy = "emailAttachment")
    @JsonIgnore
    private FileForOCRProcessing fileForOCRProcessing;

    @ManyToOne
    @JsonIgnoreProperties("emailAttachments")
    private EmailMessages emailMessages;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public EmailAttachment messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public EmailAttachment clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public EmailAttachment fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public EmailAttachment fileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public EmailAttachment fileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public FileForOCRProcessing getFileForOCRProcessing() {
        return fileForOCRProcessing;
    }

    public EmailAttachment fileForOCRProcessing(FileForOCRProcessing fileForOCRProcessing) {
        this.fileForOCRProcessing = fileForOCRProcessing;
        return this;
    }

    public void setFileForOCRProcessing(FileForOCRProcessing fileForOCRProcessing) {
        this.fileForOCRProcessing = fileForOCRProcessing;
    }

    public EmailMessages getEmailMessages() {
        return emailMessages;
    }

    public EmailAttachment emailMessages(EmailMessages emailMessages) {
        this.emailMessages = emailMessages;
        return this;
    }

    public void setEmailMessages(EmailMessages emailMessages) {
        this.emailMessages = emailMessages;
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
        EmailAttachment emailAttachment = (EmailAttachment) o;
        if (emailAttachment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailAttachment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailAttachment{" +
            "id=" + getId() +
            ", messageId='" + getMessageId() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", fileLocation='" + getFileLocation() + "'" +
            "}";
    }
}

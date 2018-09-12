package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A EmailMessages.
 */
@Entity
@Table(name = "email_messages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "message_id", nullable = false)
    private String messageId;

    @NotNull
    @Column(name = "email_subject", nullable = false)
    private String emailSubject;

    @Column(name = "email_body")
    private String emailBody;

    @Column(name = "status")
    private String status;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @NotNull
    @Column(name = "receive_from", nullable = false)
    private String receiveFrom;

    @NotNull
    @Column(name = "received_time", nullable = false)
    private Instant receivedTime;

    @NotNull
    @Column(name = "number_of_attachments", nullable = false)
    private Integer numberOfAttachments;

    @NotNull
    @Column(name = "attachments", nullable = false)
    private String attachments;

    @OneToMany(mappedBy = "emailMessages")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmailAttachment> emailAttachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Client client;

    @OneToOne(mappedBy = "emailMessages")
    @JsonIgnore
    private EmailProcessingError emailProcessingError;

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

    public EmailMessages messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public EmailMessages emailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
        return this;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public EmailMessages emailBody(String emailBody) {
        this.emailBody = emailBody;
        return this;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getStatus() {
        return status;
    }

    public EmailMessages status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public EmailMessages clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getReceiveFrom() {
        return receiveFrom;
    }

    public EmailMessages receiveFrom(String receiveFrom) {
        this.receiveFrom = receiveFrom;
        return this;
    }

    public void setReceiveFrom(String receiveFrom) {
        this.receiveFrom = receiveFrom;
    }

    public Instant getReceivedTime() {
        return receivedTime;
    }

    public EmailMessages receivedTime(Instant receivedTime) {
        this.receivedTime = receivedTime;
        return this;
    }

    public void setReceivedTime(Instant receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Integer getNumberOfAttachments() {
        return numberOfAttachments;
    }

    public EmailMessages numberOfAttachments(Integer numberOfAttachments) {
        this.numberOfAttachments = numberOfAttachments;
        return this;
    }

    public void setNumberOfAttachments(Integer numberOfAttachments) {
        this.numberOfAttachments = numberOfAttachments;
    }

    public String getAttachments() {
        return attachments;
    }

    public EmailMessages attachments(String attachments) {
        this.attachments = attachments;
        return this;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public Set<EmailAttachment> getEmailAttachments() {
        return emailAttachments;
    }

    public EmailMessages emailAttachments(Set<EmailAttachment> emailAttachments) {
        this.emailAttachments = emailAttachments;
        return this;
    }

    public EmailMessages addEmailAttachment(EmailAttachment emailAttachment) {
        this.emailAttachments.add(emailAttachment);
        emailAttachment.setEmailMessages(this);
        return this;
    }

    public EmailMessages removeEmailAttachment(EmailAttachment emailAttachment) {
        this.emailAttachments.remove(emailAttachment);
        emailAttachment.setEmailMessages(null);
        return this;
    }

    public void setEmailAttachments(Set<EmailAttachment> emailAttachments) {
        this.emailAttachments = emailAttachments;
    }

    public Client getClient() {
        return client;
    }

    public EmailMessages client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public EmailProcessingError getEmailProcessingError() {
        return emailProcessingError;
    }

    public EmailMessages emailProcessingError(EmailProcessingError emailProcessingError) {
        this.emailProcessingError = emailProcessingError;
        return this;
    }

    public void setEmailProcessingError(EmailProcessingError emailProcessingError) {
        this.emailProcessingError = emailProcessingError;
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
        EmailMessages emailMessages = (EmailMessages) o;
        if (emailMessages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailMessages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailMessages{" +
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
            "}";
    }
}

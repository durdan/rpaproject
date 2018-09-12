package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClientDataOcr.
 */
@Entity
@Table(name = "client_data_ocr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientDataOcr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "jhi_value")
    private String value;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @Column(name = "attachment_id")
    private Integer attachmentId;

    @OneToMany(mappedBy = "clientDataOcr")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OutputTemplate> outputTemplates = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clientDataOcrs")
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public ClientDataOcr keyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public ClientDataOcr value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessageId() {
        return messageId;
    }

    public ClientDataOcr messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public ClientDataOcr clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public ClientDataOcr attachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
        return this;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Set<OutputTemplate> getOutputTemplates() {
        return outputTemplates;
    }

    public ClientDataOcr outputTemplates(Set<OutputTemplate> outputTemplates) {
        this.outputTemplates = outputTemplates;
        return this;
    }

    public ClientDataOcr addOutputTemplate(OutputTemplate outputTemplate) {
        this.outputTemplates.add(outputTemplate);
        outputTemplate.setClientDataOcr(this);
        return this;
    }

    public ClientDataOcr removeOutputTemplate(OutputTemplate outputTemplate) {
        this.outputTemplates.remove(outputTemplate);
        outputTemplate.setClientDataOcr(null);
        return this;
    }

    public void setOutputTemplates(Set<OutputTemplate> outputTemplates) {
        this.outputTemplates = outputTemplates;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public ClientDataOcr transaction(Transaction transaction) {
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
        ClientDataOcr clientDataOcr = (ClientDataOcr) o;
        if (clientDataOcr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientDataOcr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientDataOcr{" +
            "id=" + getId() +
            ", keyName='" + getKeyName() + "'" +
            ", value='" + getValue() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", attachmentId=" + getAttachmentId() +
            "}";
    }
}

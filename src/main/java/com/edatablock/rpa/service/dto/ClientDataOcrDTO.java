package com.edatablock.rpa.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClientDataOcr entity.
 */
public class ClientDataOcrDTO implements Serializable {

    private Long id;

    private String keyName;

    private String value;

    private String messageId;

    private String clientEmailAddress;

    private Integer attachmentId;

    private Long transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
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

        ClientDataOcrDTO clientDataOcrDTO = (ClientDataOcrDTO) o;
        if (clientDataOcrDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientDataOcrDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientDataOcrDTO{" +
            "id=" + getId() +
            ", keyName='" + getKeyName() + "'" +
            ", value='" + getValue() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", attachmentId=" + getAttachmentId() +
            ", transaction=" + getTransactionId() +
            "}";
    }
}

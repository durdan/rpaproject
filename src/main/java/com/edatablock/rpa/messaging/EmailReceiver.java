package com.edatablock.rpa.messaging;

import com.edatablock.rpa.email.domain.EmailMessages;
import com.edatablock.rpa.service.ClientEmailDomainService;
import com.edatablock.rpa.service.ClientService;
import com.edatablock.rpa.service.EmailAttachmentService;
import com.edatablock.rpa.service.EmailMessagesService;
import com.edatablock.rpa.service.dto.EmailAttachmentDTO;
import com.edatablock.rpa.service.dto.EmailMessagesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

import static com.edatablock.rpa.config.ActiveMQConfig.MESSAGE_QUEUE;

@Component
public class EmailReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailReceiver.class);

    @Autowired
    public EmailMessagesService emailMessagesService;

    @Autowired
    public EmailAttachmentService attachmentService;

    @Autowired
    public ClientService clientService;

    @Autowired
    public ClientEmailDomainService clientEmailDomainService;


    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }


    @JmsListener(destination = MESSAGE_QUEUE)
    public void receive(EmailMessages message) {
        LOGGER.info("----------received message='{}'", message);
        LOGGER.info("----------received messageID='{}'", message.getMessageId());
        LOGGER.info("----------received message Subject='{}'", message.getEmailSubject());
        LOGGER.info("----------received message Received DateTime='{}'", message.getReceivedTime());
        LOGGER.info("----------received message Attachment Numbers='{}'", message.getNumberOfAttachments().intValue());
        LOGGER.info("----------received message AttachmentNames='{}'", message.getAttachments());

        EmailMessagesDTO email = null;

        email = getEmailMessage(message);
        if (email != null) {
            emailMessagesService.save(email);

            saveAttachments(message);

        }

        //RPA service will store information in database

        //It will create a message for OCR process --- (emailMessage +Template+rules) ---
        //OCR will have listener --- it will run OCR process -- Extract

        latch.countDown();

        ///  Email+attachment+template  sendTothequeue - OCRInputqueue
        //OCR will recieve this
        //OCR will produce output in OCROutputqueue -- emailmessgId_fieldsname

    }


    public EmailMessagesDTO getEmailMessage(EmailMessages message) {
        EmailMessagesDTO email = new EmailMessagesDTO();
        email.setMessageId(message.getMessageId());
        email.setEmailSubject(message.getEmailSubject());
        email.setNumberOfAttachments(message.getNumberOfAttachments());
        email.setReceivedTime(message.getReceivedTime());
        email.setReceiveFrom(message.getReceiveFrom());
        email.setAttachments(message.getAttachments());
        //search clientemaildomain table using receivedfrom we will get the client details in return after that we can insert this data in attachemnt table
        return email;
    }


    public void saveAttachments(EmailMessages message) {
        String[] attachments = message.getAttachments().split(",");
        if (attachments.length > 0) {
            for (int i = 0; i < attachments.length; i++) {
                EmailAttachmentDTO emailAttachmentDTO = new EmailAttachmentDTO();
                emailAttachmentDTO.setFileName(attachments[i]);
                LOGGER.info("File Name >>>>>>"+attachments[i]);
                String[] fileExtension=attachments[i].split(Pattern.quote("."));
                LOGGER.info("File Extension" + fileExtension.length);
                if(fileExtension.length>0) {
                    LOGGER.info("File Extension" + fileExtension.length);
                    emailAttachmentDTO.setFileExtension(fileExtension[1]);
                }

                // emailAttachmentDTO.setClientEmailAddress();
                emailAttachmentDTO.setMessageId(message.getMessageId());
                attachmentService.save(emailAttachmentDTO);
            }

        }


    }



//        public String getClientId(String emailAddress){
//
//            clientService.
//
//        }

}


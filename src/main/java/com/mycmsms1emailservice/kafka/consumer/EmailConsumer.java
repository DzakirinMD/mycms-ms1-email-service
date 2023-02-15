package com.mycmsms1emailservice.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycmsms1emailservice.domain.EmailDetails;
import com.mycmsms1emailservice.service.EmailService;
import com.mycmsms1emailservice.service.dto.InhouseTransferEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    private final EmailService emailService;

    @Autowired
    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeInhouseTransfer(String message) {

        LOGGER.info("Inhouse Event received in email-service => {}", message);

        // TODO: Use Spring auto-configuration for mapping string message to object
        ObjectMapper om = new ObjectMapper();

        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            InhouseTransferEventDTO inhouseTransferEventDTO = om.readValue(message, InhouseTransferEventDTO.class);

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(inhouseTransferEventDTO.getInhouseTransfer().getOrderEmailRecipient());
            emailDetails.setSubject("Automated order receipt for: " + inhouseTransferEventDTO.getInhouseTransfer().getOrderName());
            emailDetails.setMsgBody("Hi !\n\n" +
                    "Your order has been created. Below are the details : \n" +
                    "\nItem: " + inhouseTransferEventDTO.getInhouseTransfer().getOrderName() +
                    "\nTotal quantity: " + inhouseTransferEventDTO.getInhouseTransfer().getOrderQty() +
                    "\nTotal price: RM " + inhouseTransferEventDTO.getInhouseTransfer().getOrderPrice() +
                    "\nOrder Status: " + inhouseTransferEventDTO.getStatus() +
                    "\n\nThank You !!"
            );

            String status = emailService.sendSimpleEmail(emailDetails);

            LOGGER.info("Automated order email has been send in email-service => {}", status);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.toString());
        }
    }
}

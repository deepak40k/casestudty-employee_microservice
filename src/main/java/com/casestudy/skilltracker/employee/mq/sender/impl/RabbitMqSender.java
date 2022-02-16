package com.casestudy.skilltracker.employee.mq.sender.impl;

import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.mq.sender.MessageSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender implements MessageSender<AssociateProfileResponse> {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    public void send(AssociateProfileResponse associateProfile) {
        rabbitTemplate.convertAndSend(exchange, routingKey, associateProfile);
    }
}


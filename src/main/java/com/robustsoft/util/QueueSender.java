package com.robustsoft.util;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by JoinHan on 2017/3/31.
 */
@Component("queueSender")
public class QueueSender {

    @Resource(name = "jmsQueueTemplate")
    private JmsTemplate jmsQueueTemplate;// 通过@Qualifier修饰符来注入对应的bean

    public void send(String destination, final Object message) {
        jmsQueueTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return jmsQueueTemplate.getMessageConverter().toMessage(message, session);
            }
        });

    }

}
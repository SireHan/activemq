package com.robustsoft.util;

import com.robustsoft.activemq.QueueDefination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by JoinHan on 2017/3/31.
 */
@Component("queueReciver")
public class QueueReciver {
    private static final Logger log = LoggerFactory.getLogger(QueueReciver.class);

    @JmsListener(destination= QueueDefination.TEST_MQ,concurrency="5-10")
    public void onMessagehehe(Message message, Session session) throws JMSException {
            System.out.println(message.toString());
            System.out.println(session);
            //throw new RuntimeException("ccc");
            message.acknowledge();
    }
}

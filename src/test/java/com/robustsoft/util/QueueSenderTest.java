package com.robustsoft.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by JoinHan on 2017/3/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring.xml")
public class QueueSenderTest {
    @Autowired
    private QueueSender queueSender;
    @Autowired
    private QueueReciver  queueReciver;

    @Test
    public void send() throws Exception {
        queueSender.send("test-MQ","my first message");
    }

}
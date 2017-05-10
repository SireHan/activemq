package com.robustsoft.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JoinHan on 2017/3/24.
 */
public class Sender {
    private static final Logger LOGGER= LoggerFactory.getLogger(Receiver.class);
    //ActiveMq 的默认用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
//    private static final String USERNAME = "admin";
    //ActiveMq 的默认登录密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
//    private static final String PASSWORD = "admin";
    //ActiveMQ 默认代理地址 "failover://tcp://localhost:61616"  服务器地址不同IP修改不同的IP
//    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String BROKEN_URL = "failover://tcp://192.168.247.135:61616";

    AtomicInteger count = new AtomicInteger(0);
    //链接工厂
    ConnectionFactory connectionFactory;
    //链接对象
    Connection connection;
    //事务管理
    Session session;
    ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<MessageProducer>();

    public void init(){
        try {
            //创建一个链接工厂
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
            //从工厂中创建一个链接
            connection  = connectionFactory.createConnection();
            //开启链接
            connection.start();
            //创建一个事务（这里通过参数可以设置事务的级别）
            session = connection.createSession(true,Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send message.
     * 往队列名 queueName 里面添加消息
     * @param queueName 队列名
     */
    public void sendMessage(String queueName){
        try {
            //创建一个消息队列
            Queue queue = session.createQueue(queueName);
            //消息生产者
            MessageProducer messageProducer = null;
            if(threadLocal.get()!=null){
                messageProducer = threadLocal.get();
            }else{
                messageProducer = session.createProducer(queue);
                threadLocal.set(messageProducer);
            }
            while(true){
                Thread.sleep(1000);
                int num = count.getAndIncrement();
                //创建一条消息
                TextMessage msg = session.createTextMessage(Thread.currentThread().getName()+
                        "productor:我是大帅哥，我现在正在生产东西！,count:"+num);
                LOGGER.debug(Thread.currentThread().getName()+
                        "productor:我是大帅哥，我现在正在生产东西！,count:"+num);
                //发送消息
                messageProducer.send(msg);
                //持久化
                messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

                //提交事务
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void distory() {
        if(connection!=null){
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

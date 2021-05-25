package com.atguigu.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic_Persist {

    public static final String ACTIVEMQ_URL = "tcp://39.103.201.193:61616";
    public static final String TOPIC_NAME = "Topic-Persist";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();

        // 第一个参数事务，第二个参数签收； 事务偏向生产者，签收签收偏向消费者
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();

        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = (TextMessage) session.createTextMessage("msg-persist-" + i);
            messageProducer.send(textMessage);
        }

        // 关闭资源
        session.close();
        connection.close();

        /*
            1、一定要先运行一次消费者，等于向MQ注册，类似于我订阅了这个主题，
            然后再运行生产者发送消息，此时，
            无论消费者是否在线，都会接收到消息，不在线的话，下次连接的时候，会把没有接受的消息都接收下来
         */
    }
}

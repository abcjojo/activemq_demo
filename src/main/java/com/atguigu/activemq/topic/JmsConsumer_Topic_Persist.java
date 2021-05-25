package com.atguigu.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer_Topic_Persist {

    public static final String ACTIVEMQ_URL = "tcp://39.103.201.193:61616";
    public static final String TOPIC_NAME = "Topic-Persist";

    public static void main(String[] args) throws JMSException {

        // 1、创建连接工厂，按照给定的url地址，采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2、通过连接工厂，获取连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z4");

        // 3、创建回话session
        // 两个参数 第一个时事务，第二个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4、创建目的地 具体是队列还是主题topic
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark..");
        connection.start();

        Message message = topicSubscriber.receive();
        while (null != message) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("*************收到的持久化topic：" + textMessage.getText());
            message = topicSubscriber.receive(3000L);
        }

        session.close();
        connection.close();

    }
}

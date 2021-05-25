package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {

//    public static final String ACTIVEMQ_URL = "tcp://39.103.201.193:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {

        // 1、创建连接工厂，按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // activemq消息被重复消费时，默认重发时间间隔为1秒 默认重发次数为6次，之后就会进入死信队列
        // 设置最大重复消费次数
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);


        // 2、通过连接工厂获取连接connection，并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 3、创建会话session，  两个参数，第一个参数：事务， 第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4、创建目的地，具体是队列还是主题topic
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5、创建消费者
        MessageConsumer consumer = session.createConsumer(queue);

        /**
         *  同步阻塞方式：receive()
         *  订阅者或接收者调用MessageConsumer的receive()方法来接受消息，receive方法在接收到消息之前（或者超时之前）一直阻塞
         *
        while (true) {
            TextMessage textMessage = (TextMessage) consumer.receive();
            if (null != textMessage) {
                System.out.println("消费者接收到消息：" + textMessage.getText());
            }else {
                break;
            }
        }

        // 6、关闭资源
        consumer.close();
        session.close();
        connection.close();

         */

        // 通过监听的方式来消费消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者接收到消息broker：" + textMessage.getText());
                        System.out.println("消费者接收到消息属性broker：" + textMessage.getStringProperty("c01"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

                // 消费MapMessage消息
                if (null != message && message instanceof MapMessage) {
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println("消费者接收到消息：" + mapMessage.getString("k1"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();


    }
}

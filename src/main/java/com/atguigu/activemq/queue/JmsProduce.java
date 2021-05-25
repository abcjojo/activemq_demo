package com.atguigu.activemq.queue;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.util.UUID;

public class JmsProduce {

    //    public static final String ACTIVEMQ_URL = "tcp://39.103.201.193:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {

        // 1、创建连接工厂，按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 使用异步投递  (未使用事务且持久化消息的场景下，会默认使用同步发送机制，此时需要手动设置异步投递)
        activeMQConnectionFactory.setUseAsyncSend(true);

        // 2、通过连接工厂获取连接connection，并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 3、创建会话session，  两个参数，第一个参数：事务， 第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4、创建目的地，具体是队列还是主题topic
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5、创建消息的生产者
//        MessageProducer producer = session.createProducer(queue);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue);

        /**
         *  持久化的消息，服务器宕机后没有被消费的消息依旧存在，只是没有入队，当服务器再次启动，消息任就会被消费。
         * 但是非持久化的消息，服务器宕机后消息永远丢失。 而当你没有注明是否是持久化还是非持久化时，默认是持久化的消息。
         */
        // ActiveMQ默认持久化消息
        // 对消息不进行持久化  mq宕机时会丢失消息
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 对消息进行持久化
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 延时定时投递参数
        long delay = 3 * 1000;  // 延时3秒执行
        long period = 4 * 1000; // 每4秒执行一次
        int repeat = 5;         // 重复执行5次


        // 6、使用MessageProducer生产3条消息发送到MQ队列里面
        for (int i = 0; i < 3; i++) {
            // 7、创建消息
            TextMessage textMessage = session.createTextMessage("MessageListener---" + i);
            // 设置消息属性
            textMessage.setStringProperty("c01", "vip");

            // 延迟定时重复
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);

            // 发送异步消息回调
//            textMessage.setJMSMessageID(UUID.randomUUID().toString() + "----orderAtguigu");
//            String msgID = textMessage.getJMSMessageID();
//            producer.send(textMessage, new AsyncCallback() {
//                @Override
//                public void onSuccess() {
//                    System.out.println(msgID + " has been success send!");
//                }
//
//                @Override
//                public void onException(JMSException e) {
//                    System.out.println(msgID + " has been fail send!");
//                }
//            });

            // 8、通过MessageProducer发送给MQ
            producer.send(textMessage);


//            // mapMessage
//            MapMessage mapMessage = session.createMapMessage();
//            mapMessage.setString("k1", "-----MapMessage---v1" + i);
//            producer.send(mapMessage);
        }

        // 9、释放资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("**********消息发布完成*****");
    }
}

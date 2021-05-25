package com.atguigu.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class SpringMQ_Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Producer producer = (SpringMQ_Producer) ctx.getBean("springMQ_Producer");

//        producer.jmsTemplate.send(new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage textMessage = session.createTextMessage("******Spring和ActiveMQ的整合case......");
//                return textMessage;
//            }
//        });

        producer.jmsTemplate.send(session -> {
            TextMessage textMessage = session.createTextMessage("******Spring和ActiveMQ的整合case for Listener ......");
            return textMessage;
        });

        System.out.println("***********send task over");
    }

}

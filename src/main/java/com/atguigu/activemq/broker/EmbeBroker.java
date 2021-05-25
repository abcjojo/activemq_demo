package com.atguigu.activemq.broker;

import org.apache.activemq.broker.BrokerService;

public class EmbeBroker {


    public static void main(String[] args) throws Exception {
        // activemq 也支持在vm中通信基于嵌入式的broker
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}

package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @auther zzyy
 * @create 2019-09-10 15:41
 */
public class JMSProduce_Topic
{
    public static final String MQ_URL = "tcp://192.168.111.142:61616";
    public static final String MyTOPIC = "topic0508";

    public static void main(String[] args) throws JMSException
    {
        //1 通过ConnectionFactory工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);

        //2 获得connection对象并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 通过connection对象获得session对象
        // 第一个参数叫mq的事务/第二个参数叫消息的签收，此时忽略用默认
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4 通过session获得目的地
        Topic topic = session.createTopic(MyTOPIC);
        //5 通过session获得消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);

        //6 开始生产3条消息发送到Activemq上
        for (int i = 1; i <=3; i++)
        {
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            //7 用messageProducer发送消息到mq
            messageProducer.send(textMessage);
        }
        //8 释放资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****run is ok");


    }
}

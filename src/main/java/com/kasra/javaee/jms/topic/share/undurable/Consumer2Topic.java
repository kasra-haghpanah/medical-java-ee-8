package com.kasra.javaee.jms.topic.share.undurable;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kasra.haghpanah on 22/02/2017.
 */
@MessageDriven(
        mappedName = "jms/MyTopic"
        ,activationConfig = {
        @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "java:comp/DefaultJMSConnectionFactory"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/MyTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        //@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "NewsType = 'Sports2' OR NewsType = 'Opinion2'"),
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "MyID2"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "MySub2"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "NonDurable"),
        @ActivationConfigProperty( propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge" ),
        @ActivationConfigProperty( propertyName = "endpointPoolMaxSize", propertyValue = "1" ),
        @ActivationConfigProperty( propertyName = "endpointPoolResizeCount", propertyValue = "1" ),
        @ActivationConfigProperty( propertyName = "endpointPoolSteadySize", propertyValue = "0" ),
        @ActivationConfigProperty(propertyName = "sharedSubscription",propertyValue = "TRUE"),
        //@ActivationConfigProperty(propertyName = "addressList",propertyValue = "192.168.22.142:8084")
        }
)
public class Consumer2Topic implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    AtomicLong count = new AtomicLong(0);

    @Override
    public void onMessage(Message message) {
        long i;

        try {
            if (message instanceof TextMessage) {
                i = count.incrementAndGet();

                int x = message.getIntProperty("JMSXDeliveryCount");
                // Comment out the following line to receive many messages
                System.out.println("Reading message: " + message.getBody(String.class));
            } else {
                System.err.println("Message is not a TextMessage");
            }
            message.acknowledge();
        } catch (JMSException e) {
            mdc.setRollbackOnly();
            System.err.println("Exception in onMessage(): " + e.toString());
        }
    }

    /*
     * Retrieve the count value.
     */
    public long getCount() {
        return count.get();
    }

}

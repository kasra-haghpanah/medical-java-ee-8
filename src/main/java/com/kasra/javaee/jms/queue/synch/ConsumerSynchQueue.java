package com.kasra.javaee.jms.queue.synch;

import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.model.Sick;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 01/12/2016.
 */
@Named
@SessionScoped
public class ConsumerSynchQueue implements Serializable {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    // create-jms-resource --restype javax.jms.Queue --property Name=PhysicalQueue jms/MyQueue2
    @Resource(lookup = "jms/MyQueue2")
    private Queue queue;

    public Sick recieve() {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSConsumer consumer = context.createConsumer(queue);
            //context.stop();
            consumer.setMessageListener(null);
            Message message = consumer.receive(1000);
            return (Sick)message;
            //context.start();
        }
    }

}

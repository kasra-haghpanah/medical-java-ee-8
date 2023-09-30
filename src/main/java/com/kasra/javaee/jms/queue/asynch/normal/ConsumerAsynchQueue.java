package com.kasra.javaee.jms.queue.asynch.normal;

import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.model.Sick;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

/**
 * Created by kasra.haghpanah on 01/12/2016.
 */
public class ConsumerAsynchQueue {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    // create-jms-resource --restype javax.jms.Queue --property Name=PhysicalQueue jms/MyQueue
    @Resource(lookup = "jms/MyQueue")
    private Queue queue;


    public void recieve() {
        try (JMSContext context = connectionFactory.createContext()) {
            JMSConsumer consumer = context.createConsumer(queue);
            //context.stop();
            consumer.setMessageListener(new ConsumerAsynchQueue.MyMessageListener());
            //context.start();
        }
    }

    @MessageDriven(mappedName = "jms/MyQueue")
    public static class MyMessageListener implements MessageListener {

        @EJB
        ISickRepository sickRepository;

        @Override
        public void onMessage(Message message) {

            try {
                Object object = ((ObjectMessage) message).getObject();

                if (object instanceof Sick) {
                    sickRepository.save((Sick) object);
                    System.out.println(object.toString());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }


        }
    }
}

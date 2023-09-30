package com.kasra.javaee.jms.queue.synch;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Created by kasra.haghpanah on command/12/2016.
 */
@Named
@SessionScoped
public class ProducerSynchQueue implements Serializable {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;


    // create-jms-resource --restype javax.jms.Queue --property Name=PhysicalQueue jms/MyQueue2
    @Resource(lookup = "jms/MyQueue2")
    private Queue queue;

    public <T extends Serializable> void send(T entity) {
        // for Transactional
        try (JMSContext context = connectionFactory.createContext(/*JMSContext.SESSION_TRANSACTED*/)) {
            JMSProducer producer = context.createProducer();

         /*
         * In a try-with-resources block, create context.
         * Create QueueBrowser.
         * Check for messages on queue.
         */
            //////////////////////////////////////////////////////////////
            QueueBrowser browser = context.createBrowser(queue);
            Enumeration msgs = browser.getEnumeration();

            String result = "";
            while (msgs.hasMoreElements()) {
                Message tempMsg = (Message) msgs.nextElement();
                result = result + "  " + tempMsg.toString();
            }
            //////////////////////////////////////////////////////////////

            producer.send(queue, context.createObjectMessage(entity));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

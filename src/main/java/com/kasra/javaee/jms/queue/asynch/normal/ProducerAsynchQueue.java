package com.kasra.javaee.jms.queue.asynch.normal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Created by kasra.haghpanah on command/12/2016.
 */
@Named
public class ProducerAsynchQueue implements Serializable {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    // create-jms-resource --restype javax.jms.Queue --property Name=PhysicalQueue jms/MyQueue
    @Resource(lookup = "jms/MyQueue")
    private Queue queue;

    public <T extends Serializable> String send(T entity) {
        // for Transactional
        String result = "";
        try (JMSContext context = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED)) {
            JMSProducer producer = context.createProducer();

         /*
         * In a try-with-resources block, create context.
         * Create QueueBrowser.
         * Check for messages on queue.
         */
            //////////////////////////////////////////////////////////////
            QueueBrowser browser = context.createBrowser(queue);
            Enumeration msgs = browser.getEnumeration();


            while (msgs.hasMoreElements()) {
                Message tempMsg = (Message) msgs.nextElement();
                result = result + "  " + tempMsg.toString();
            }
            //////////////////////////////////////////////////////////////

            producer.send(queue, context.createObjectMessage(entity));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }
}

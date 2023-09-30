package com.kasra.javaee.jms.queue.asynch.browser;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Created by kasra.haghpanah on 21/02/2017.
 */
@Named
@SessionScoped
public class BrowserQueue implements Serializable {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/MyQueue")
    private Queue queue;

    public String getBrowser() {
        QueueBrowser browser;

        String result = "";
        /*
         * In a try-with-resources block, create context.
         * Create QueueBrowser.
         * Check for messages on queue.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            browser = context.createBrowser(queue);
            Enumeration msgs = browser.getEnumeration();

            if (!msgs.hasMoreElements()) {
                return "No messages in queue";
            } else {
                while (msgs.hasMoreElements()) {
                    Message tempMsg = (Message) msgs.nextElement();
                    result = result + "  " + tempMsg.toString();
                }
            }
        } catch (JMSException e) {
            return e.toString();
        }

        return result;
    }
}

package com.kasra.javaee.jms.topic.share.undurable;

import com.kasra.javaee.model.Person;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 22/02/2017.
 */
@Named
@SessionScoped
public class ProducerTopic implements Serializable {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/MyTopic")
    private Topic topic;


    public void send(List<Person> personList) {
        try (JMSContext context = connectionFactory.createContext();) {
            JMSProducer producer = context.createProducer();


            for (Person person : personList) {
                producer.setDeliveryDelay(1000);
                producer.send(topic, context.createTextMessage(person.getFirstName() + ":" + person.getLastName()));
            }

//            producer.setDeliveryDelay(1000);
//            producer.send(topic, context.createTextMessage("Hello!-4th"));
//            producer.setDeliveryDelay(1000);
//            producer.send(topic, context.createTextMessage("Hello!-5th"));
        }
    }

}

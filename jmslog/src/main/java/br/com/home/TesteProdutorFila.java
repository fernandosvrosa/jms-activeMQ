package br.com.home;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by fernandor on 13/06/17.
 */
public class TesteProdutorFila {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("LOG");

        MessageProducer producer = session.createProducer(fila);

        Message message = session.createTextMessage("[ERROR]   symbol:   class MessageProducer 4\n");
        producer.send(message, DeliveryMode.NON_PERSISTENT, 4, 80000);

        session.close();
        connection.close();
        context.close();
    }
}

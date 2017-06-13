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
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");

        MessageProducer producer = session.createProducer(fila);

        for (int i = 0; i < 10; i ++ ){
            Message message = session.createTextMessage("<pedido><id>"+ i +"</id></pedido> \n");
            // confirma o que recebeu para activeMQ
            message.acknowledge();
            producer.send(message);
        }

        session.close();
        connection.close();
        context.close();
    }
}

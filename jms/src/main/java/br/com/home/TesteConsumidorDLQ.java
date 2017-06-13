package br.com.home;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

/**
 * Created by fernandor on 13/06/17.
 */
public class TesteConsumidorDLQ {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("DLQ");
        MessageConsumer consumer = session.createConsumer(fila);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                System.out.print(message);
            }
        });


        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}

package br.com.home;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

/**
 * Created by fernandor on 13/06/17.
 */
public class TesteConsumidorFila {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);

        Connection connection = factory.createConnection();
        connection.start();
        // trabalhando com transacao
        final Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.print(textMessage.getText());
                    session.commit();
                } catch (JMSException e) {
                    try {
                        session.rollback();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        });


        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}

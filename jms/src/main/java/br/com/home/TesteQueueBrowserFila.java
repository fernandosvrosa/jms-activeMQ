package br.com.home;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Enumeration;

/**
 * Created by fernandor on 13/06/17.
 */
public class TesteQueueBrowserFila {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");
        QueueBrowser browser = session.createBrowser((Queue) fila);
        Enumeration msgs = browser.getEnumeration();

        if ( !msgs.hasMoreElements() ) {
            System.out.println("No messages in queue");
        } else {
            while (msgs.hasMoreElements()) {
                Message tempMsg = (Message)msgs.nextElement();
                System.out.println("Message: " + tempMsg);
            }
        }



        session.close();
        connection.close();
        context.close();
    }
}

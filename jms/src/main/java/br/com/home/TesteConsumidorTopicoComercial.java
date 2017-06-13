package br.com.home;

import br.com.home.modelo.Pedido;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

/**
 * Created by fernandor on 13/06/17.
 */
public class TesteConsumidorTopicoComercial {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);

        Connection connection = factory.createConnection();
        connection.setClientID("comercial");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topico = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assianatura");

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                ObjectMessage objectMessage = (ObjectMessage) message;

                try {
                    Pedido pedido = (Pedido) objectMessage.getObject();
                    System.out.print(pedido.getCodigo());
                } catch (JMSException e) {
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

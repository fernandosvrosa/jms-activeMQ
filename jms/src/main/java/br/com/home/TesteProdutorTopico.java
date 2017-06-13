package br.com.home;

import br.com.home.modelo.Pedido;
import br.com.home.modelo.PedidoFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by fernandor on 13/06/17.
 */
public class TesteProdutorTopico {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);
        Connection connection = factory.createConnection();

        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");
        MessageProducer producer = session.createProducer(topico);

        Pedido pedido = new PedidoFactory().geraPedidoComValores();

//        StringWriter writer = new StringWriter();
//        JAXB.marshal(pedido, writer);
//        String xml = writer.toString();
//
//        System.out.print(xml);

        Message message = session.createObjectMessage(pedido);
        message.setBooleanProperty("ebook", true);
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }
}

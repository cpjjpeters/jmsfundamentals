package be.belfius.jms.basics;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JmsContextDemo {

	public static void main(String[] args) throws NamingException {
		System.out.println("Using jms 2.0 API");

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext =cf.createContext();) {
			jmsContext.createProducer().send(queue, "Arise arg1");
			
			String receiveBody = jmsContext.createConsumer(queue).receiveBody(String.class);
			System.out.println(receiveBody);
		} 
	}
}

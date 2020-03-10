package be.belfius.jms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePropertiesDemo {

	public static void main(String[] args) throws NamingException, JMSException {

		System.out.println("MessageTypesDemo jms 2.0 API");

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext =cf.createContext();) {
			
			JMSProducer producer = jmsContext.createProducer();
			TextMessage textMessage = jmsContext.createTextMessage("I am a created text message 1");
			textMessage.setBooleanProperty("loggedIn",true);
			textMessage.setStringProperty("userToken", "abc123");
			producer.send(queue, textMessage);
			
			Message messageReceived =  jmsContext.createConsumer(queue).receive(5000);
			System.out.println(""+ messageReceived);
			System.out.println( messageReceived.getBooleanProperty("loggedIn"));
			System.out.println( messageReceived.getStringProperty("userToken"));
			
		} 
	}

}

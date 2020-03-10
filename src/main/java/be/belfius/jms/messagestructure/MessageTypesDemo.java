package be.belfius.jms.messagestructure;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypesDemo {

	public static void main(String[] args) throws NamingException, JMSException {

		System.out.println("MessageTypesDemo jms 2.0 API");

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext =cf.createContext();) {
			
			JMSProducer producer = jmsContext.createProducer();
			TextMessage textMessage = jmsContext.createTextMessage("I am a created text message 1");
			BytesMessage bytesMessage = jmsContext.createBytesMessage();
			StreamMessage streamMessage = jmsContext.createStreamMessage();
			MapMessage mapMessage = jmsContext.createMapMessage();
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			Patient patient = new Patient();
			patient.setId(456);
			patient.setName("Paul");
			objectMessage.setObject(patient);
			bytesMessage.writeUTF("John");
			bytesMessage.writeLong(123L);
			streamMessage.writeBoolean(true);
			streamMessage.writeFloat(2.5F);
			mapMessage.setBoolean("isCreated", true);
			
//			producer.send(queue, textMessage);
//			producer.send(queue, bytesMessage);
//			producer.send(queue, streamMessage);
//			producer.send(queue, mapMessage);
			producer.send(queue, objectMessage);
			
//			BytesMessage messageReceived =  (BytesMessage) jmsContext.createConsumer(queue).receive(5000);
//			System.out.println(""+ messageReceived.readUTF());
//			System.out.println(""+ messageReceived.readLong());
//			StreamMessage messageReceived =  (StreamMessage) jmsContext.createConsumer(queue).receive(5000);
//			System.out.println("streamMessage: "+messageReceived.readBoolean());
//			System.out.println(messageReceived.readFloat());
//			MapMessage messageReceived =  (MapMessage) jmsContext.createConsumer(queue).receive(5000);
//			System.out.println("mapMessage: "+messageReceived);
//			System.out.println(messageReceived.getBoolean("isCreated"));
			ObjectMessage messageReceived =  (ObjectMessage) jmsContext.createConsumer(queue).receive(5000);
			System.out.println("objectMessage: "+messageReceived);
			Patient object = (Patient) messageReceived.getObject();
			System.out.println(patient.getId());
			System.out.println(patient.getName());
			
			
		} 
	}

}

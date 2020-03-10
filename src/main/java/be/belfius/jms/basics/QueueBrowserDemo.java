package be.belfius.jms.basics;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowserDemo {

	public static void main(String[] args) throws NamingException {

		InitialContext initialContext = null;
		Connection connection = null;
		try {
			System.out.println("QueueBrowserDemo");
			initialContext = new InitialContext();
			ConnectionFactory cf =(ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection =cf.createConnection();
			Session session = connection.createSession();
			Queue queue =(Queue) initialContext.lookup("queue/myQueue");
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("I am the first of me");
			producer.send(message);
			TextMessage message2 = session.createTextMessage("I am the second destiny");
			producer.send(message2);
			System.out.println("Message Sent: "+ message.getText());
			System.out.println("Message Sent: "+ message2.getText());
			
			QueueBrowser browser = session.createBrowser(queue);
			Enumeration enumeration = browser.getEnumeration();
			int i=1;
			while(enumeration.hasMoreElements()) {
				TextMessage eachMessage = (TextMessage) enumeration.nextElement();
				System.out.println(i+"Browsing: " +eachMessage.getText());
				i++;
			}
			// should not count to 3, so why?
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			TextMessage messageR = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received: "+ messageR.getText());
			messageR = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received: "+ messageR.getText());
			
			
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(initialContext!=null) {
				initialContext.close();
			}
		}
		
		if(connection!=null) {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}

}

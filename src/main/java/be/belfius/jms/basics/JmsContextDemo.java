package be.belfius.jms.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JmsContextDemo {

	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws NamingException, IOException {
		System.out.println("Using jms 2.0 API");
		String payload = "payload";
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext =cf.createContext();) {
			jmsContext.createProducer().send(queue, createStringFromFile());
//			"D:\\Data\\17L07462.txt"
//			"D:\\Data\\19L03549.txt"
//			"D:\\Data\\19L03553.txt"
//			"D:\\Data\\20L00003.txt"
//			"D:\\Data\\20L00029.txt"
			
//			String receiveBody = jmsContext.createConsumer(queue).receiveBody(String.class);
//			System.out.println(receiveBody);
		} 
	}
	
	public static String createStringFromFile() throws IOException{
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader("D:\\Data\\17L07462.txt"));
			char[] buf = new char[1024];
			int numRead = 0;
			while((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				sb.append(readData);
				buf = new char[1024];
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		String returnStr = sb.toString();
		return returnStr;
	}
}

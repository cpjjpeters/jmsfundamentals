package be.belfius.jms.grouping;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

class MyListener implements MessageListener{

		private String name;
		private Map<String, String> receivedMessages;
		MyListener(String name,Map<String, String> receivedMessages){
			this.name = name;
			this.receivedMessages = receivedMessages;
		}
		@Override
		public void onMessage(Message message) {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println(""+textMessage.getText());
				System.out.println("Listener name:"+name);
				receivedMessages.put(textMessage.getText(), name);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
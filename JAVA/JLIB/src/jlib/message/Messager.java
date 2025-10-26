//*************************************************************************************************
package jlib.message;
//*************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//*************************************************************************************************
public class Messager {

	//=============================================================================================
	private List<Message> input;	
	private List<Message> output;
	private Map<Integer, List<MessageReceiver>> subscribers;
	//=============================================================================================
	
	//=============================================================================================
	public Messager() {
		input = new ArrayList<Message>();
		output = new ArrayList<Message>();
		subscribers = new HashMap<Integer, List<MessageReceiver>>();
	}
	//=============================================================================================

	//=============================================================================================
	public void subscribe(MessageReceiver subscriber, int ... channels) {
		for (int i=0; i<channels.length; i++) {
			int channel = channels[i];
			var receivers = subscribers.get(channel);
			if (receivers == null) {
				receivers = new ArrayList<>();
				subscribers.put(channel, receivers);
			}
			if (!receivers.contains(subscriber)) {
				receivers.add(subscriber);
			}
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public synchronized void post(Message message) {
		input.add(message);
	}
	//=============================================================================================
	
	//=============================================================================================
	private synchronized void swap() {
		var temp = input;
		input = output;
		output = temp;
	}
	//=============================================================================================

	//=============================================================================================
	private void deliver(Message message) {
		var channel = message.channel;
		var receivers = subscribers.get(channel);
		if (receivers != null) {
			for (var receiver : receivers) {
				receiver.receive(message);
			}
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	public void update() {		
		swap();
		for (var message: output) {
			 deliver(message);
		}
		output.clear();
	}
	//=============================================================================================
	
}
//*************************************************************************************************

package delma.dda.simulator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MessageCenter {
	private Map<Node, Map<Integer, Message>> map;

	public MessageCenter() {
		map = new HashMap<>();
	}

	public void clear() {
		map.clear();
	}

	public Message receive(Node node, int port) {
		System.out.println("(" + node + ", " + port + ") " + map);
		if(port > node.ports()) {
			return null;
		}
		Node tempNode = node.get(port);
		Message temp = map.getOrDefault(tempNode, Collections.<Integer, Message>emptyMap()).get(tempNode.get(node));
		System.out.println("receive (" + node.get(port) + ", " + node.get(port).get(node) + ") = " + temp);
		return temp;

	}

	public void send(Node node, int port, Message message) {
		System.out.println("send (" + node + ", " + port + ") = " + message);
		map.computeIfAbsent(node, (Map) -> new HashMap<Integer, Message>()).put(port, message);
	}
}

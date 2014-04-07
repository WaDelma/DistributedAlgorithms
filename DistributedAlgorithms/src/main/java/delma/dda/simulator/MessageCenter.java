package delma.dda.simulator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import delma.dda.simulator.PortGraph.Node;

public class MessageCenter {
	private Map<Node, Map<Integer, Message>> map;
	private static final Map<Integer, Message> EMPTY = Collections.emptyMap();

	public MessageCenter() {
		map = new HashMap<>();
	}

	public void clear() {
		map.clear();
	}

	public Message receive(Node node, int port) {
		if (port > node.ports()) {
			return null;
		}
		Node tempNode = node.get(port);
		Message temp = map.getOrDefault(tempNode, EMPTY).get(tempNode.get(node));
		if (temp != null) {
			System.out.println("receive " + temp + " to " + node + ":"
					+ port + " from " + tempNode + ":" + tempNode.get(node));
		}
		return temp;
	}

	public void send(Node node, int port, Message message) {
		map.computeIfAbsent(node, (Map) -> new HashMap<Integer, Message>())
		.put(port, message);
		System.out.println("send " + message +  " from " + node + ":" + port + " to " + node.get(port) + ":" + node.get(port).get(node));
	}
}

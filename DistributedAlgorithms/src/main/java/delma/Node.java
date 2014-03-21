package delma;

import java.util.HashMap;
import java.util.Map;

public class Node {
	private Map<Node, Integer> ports;

	public Node() {
		ports = new HashMap<>();
	}

	public void add(Node... nodes) {
		int port = ports.size();
		for (Node node : nodes) {
			if(ports.containsKey(node)){
				continue;
			}
			ports.put(node, port);
			node.add(this);
			port++;
		}
	}

	public Map<Node, Integer> getPorts() {
		return ports;
	}

	@Override
	public String toString() {
		return "" + ports.size();
	}

}

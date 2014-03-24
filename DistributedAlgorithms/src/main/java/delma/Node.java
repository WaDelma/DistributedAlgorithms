package delma;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node {
	private Map<Node, Integer> nodeToPort;
	private Map<Integer, Node> portToNode;

	public Node() {
		portToNode = new HashMap<>();
		nodeToPort = new HashMap<>();
	}

	@Override
	public String toString() {
		return Integer.toHexString(Objects.hashCode(this)) + "|" + ports();
	}

	public void set(int port, Node node) {
		portToNode.put(port, node);
		nodeToPort.put(node, port);
	}

	public Node get(int port) {
		return portToNode.get(port);
	}

	public Integer get(Node node) {
		return nodeToPort.get(node);
	}

	public int ports() {
		return nodeToPort.size();
	}

}

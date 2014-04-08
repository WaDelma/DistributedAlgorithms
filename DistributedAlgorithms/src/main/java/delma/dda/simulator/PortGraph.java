package delma.dda.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PortGraph {
	private List<Node> nodes;
	private MessageCenter center;

	public PortGraph() {
		center = new MessageCenter();
		nodes = new ArrayList<>();
	}

	public PortGraph(List<Node> nodes) {
		this();
		this.nodes = nodes;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void init(Algorithm algo, Map<Node, Map<State, Object>> states) {
		nodes.stream().forEach(
				node -> algo.init(node.ports(), states.get(node)));
	}

	public boolean tick(Algorithm algo, Map<Node, Map<State, Object>> states) {
		nodes.stream().forEach(
				node -> algo.send(node, center, states.get(node)));
		nodes.stream().forEach(
				node -> algo.receive(node, center, states.get(node)));
		center.clear();
		return nodes.stream().anyMatch(
				node -> algo.isRunning(states.get(node)));
	}

	@Override
	public String toString() {
		return "PortGraph: " + nodes;
	}

	public static class Node {
		private Map<Node, Integer> nodeToPort;
		private Map<Integer, Node> portToNode;
		private String label = null;

		public Node() {
			portToNode = new HashMap<>();
			nodeToPort = new HashMap<>();
		}

		public Node(String label) {
			this();
			this.label = label;
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

		public void set(int port, Node node) {
			portToNode.put(port, node);
			nodeToPort.put(node, port);
		}

		public String getLabel() {
			return label == null ? Integer.toHexString(Objects.hashCode(this))
					: label;
		}

		@Override
		public String toString() {
			return  getLabel() + ":" + ports();
		}

	}
}

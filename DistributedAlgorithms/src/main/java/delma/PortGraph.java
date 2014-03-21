package delma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PortGraph {
	private List<Node> nodes;

	public PortGraph() {
		nodes = new ArrayList<>();
	}

	public PortGraph(List<Node> nodes) {
		this.nodes = nodes;
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void init(Algorithm algo, Map<Node, GettableSet<State>> states) {
		for (Node node : nodes) {
			algo.init(node.getPorts().size(), states.get(node));
		}
	}

	public boolean tick(Algorithm algo, Map<Node, GettableSet<State>> states) {
		Map<Node, Message[]> createdMessages = new HashMap<>();
		for (Node node : nodes) {
			Message[] messages = new Message[node.getPorts().size()];
			algo.send(messages, states.get(node));
			createdMessages.put(node, messages);
		}
		boolean running = false;
		for (Node node : nodes) {
			GettableSet<State> nodeStates = states.get(node);
			algo.receive(toReceivedMessages(createdMessages, node), nodeStates);
			if (algo.isRunning(nodeStates)) {
				running = true;
			}
		}
		return running;
	}

	private Message[] toReceivedMessages(Map<Node, Message[]> createdMessages, Node node) {
		Map<Node, Integer> ports = node.getPorts();
		Message[] messages = new Message[ports.size()];
		for (Entry<Node, Integer> port : ports.entrySet()) {
			int portIndex = port.getKey().getPorts().get(node);
			Message[] portMessages = createdMessages.get(port.getKey());
			if (portMessages[portIndex] == null) {
				continue;
			}
			messages[port.getValue()] = portMessages[portIndex];
			portMessages[portIndex] = null;
		}
		return messages;
	}

	@Override
	public String toString() {
		return "PortGraph: " + nodes;
	}
}

package delma;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void init(Algorithm algo, Map<Node, GettableSet<State>> states) {
		for (Node node : nodes) {
			algo.init(node.ports(), states.get(node));
		}
	}

	public boolean tick(Algorithm algo, Map<Node, GettableSet<State>> states) {
		for (Node node : nodes) {
			algo.send(node, center, states.get(node));
		}
		boolean running = false;
		for (Node node : nodes) {
			GettableSet<State> nodeStates = states.get(node);
			algo.receive(node, center, nodeStates);
			if (algo.isRunning(nodeStates)) {
				running = true;
			}
		}
		center.clear();
		return running;
	}

	@Override
	public String toString() {
		return "PortGraph: " + nodes;
	}
}

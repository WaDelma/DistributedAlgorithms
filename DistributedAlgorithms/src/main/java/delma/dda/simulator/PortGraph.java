package delma.dda.simulator;

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
		nodes.parallelStream().forEach((Node node) -> algo.init(node.ports(), states.get(node)));
	}

	public boolean tick(Algorithm algo, Map<Node, GettableSet<State>> states) {
		nodes.parallelStream().forEach((Node node) -> algo.send(node, center, states.get(node)));
		nodes.parallelStream().forEach((Node node) -> algo.receive(node, center, states.get(node)));
		center.clear();
		return nodes.parallelStream().allMatch((Node node) -> algo.isRunning(states.get(node)));
	}

	@Override
	public String toString() {
		return "PortGraph: " + nodes;
	}
}

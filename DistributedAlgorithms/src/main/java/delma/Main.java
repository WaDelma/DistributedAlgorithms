package delma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delma.dda.simulator.Algorithm;
import delma.dda.simulator.PortGraph;
import delma.dda.simulator.PortGraph.Node;
import delma.dda.simulator.State;
import delma.dda.simulator.bmm.BMM;
import delma.dda.simulator.bmm.BMM.BMMState;
import delma.dda.simulator.bmm.BMM.Color;

public class Main {

	private static void connect(Node node1, int port1, Node node2, int port2) {
		node1.set(port1, node2);
		node2.set(port2, node1);
	}

	public static void main(String[] args) {
//		BMMTest();
	}

	private static void BMMTest() {
		List<Node> nodes = new ArrayList<>();
		nodes.add(new Node("Node1"));
		nodes.add(new Node("Node2"));
		nodes.add(new Node("Node3"));
		nodes.add(new Node("Node4"));
		nodes.add(new Node("Node5"));
		nodes.add(new Node("Node6"));
		nodes.add(new Node("Node7"));
		nodes.add(new Node("Node8"));
		connect(nodes.get(0), 1, nodes.get(4), 2);
		connect(nodes.get(1), 1, nodes.get(4), 1);
		connect(nodes.get(2), 1, nodes.get(6), 2);
		connect(nodes.get(3), 1, nodes.get(6), 3);

		connect(nodes.get(0), 2, nodes.get(6), 1);
		connect(nodes.get(1), 2, nodes.get(5), 1);
		connect(nodes.get(3), 2, nodes.get(7), 2);

		connect(nodes.get(1), 3, nodes.get(7), 1);
		Map<Node, Map<State, Object>> states = new HashMap<>();
		for (int i = 0; i < nodes.size(); i++) {
			Map<State, Object> set = new HashMap<>();
			if (i < 4) {
				set.put(BMMState.COLOR, Color.WHITE);
			} else {
				set.put(BMMState.COLOR, Color.BLACK);
			}
			states.put(nodes.get(i), set);
		}
		simulate(new BMM(), new PortGraph(nodes), states);
	}

	private static void simulate(Algorithm algo, PortGraph graph,
			Map<Node, Map<State, Object>> states) {
		graph.init(algo, states);
		print("INITIALIZE:", graph.getNodes(), states);
		boolean flag = true;
		for (int n = 1; flag; n++) {
			flag = graph.tick(algo, states);
			print("ROUND " + n + ":", graph.getNodes(), states);
			if(n > 6) {
				break;
			}
		}
	}

	private static void print(String string, List<Node> nodes,
			Map<Node, Map<State, Object>> states) {
		System.out.println(string);
		nodes.forEach((node) -> System.out.println(node + "="
				+ states.get(node)));
	}

}

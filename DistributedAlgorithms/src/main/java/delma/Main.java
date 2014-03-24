package delma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delma.bmm.BMM;
import delma.bmm.Color;

public class Main {

	public static void main(String[] args) {
		List<Node> nodes = new ArrayList<>();
		nodes.add(new Node());
		nodes.add(new Node());
		nodes.add(new Node());
		nodes.add(new Node());
		nodes.add(new Node());
		nodes.add(new Node());
		nodes.add(new Node());
		nodes.add(new Node());
		connect(nodes.get(0), 1, nodes.get(4), 2);
		connect(nodes.get(1), 1, nodes.get(4), 1);
		connect(nodes.get(2), 1, nodes.get(6), 2);
		connect(nodes.get(3), 1, nodes.get(6), 3);

		connect(nodes.get(0), 2, nodes.get(6), 1);
		connect(nodes.get(1), 2, nodes.get(5), 1);
		connect(nodes.get(3), 2, nodes.get(7), 2);

		connect(nodes.get(1), 3, nodes.get(7), 1);
		PortGraph graph = new PortGraph(nodes);
		Map<Node, GettableSet<State>> states = new HashMap<>();
		for(int i = 0; i < nodes.size(); i++) {
			GettableSet<State> set = new GettableSet<>();
			if(i < 4) {
				set.add(Color.WHITE);
			}else {
				set.add(Color.BLACK);
			}
			states.put(nodes.get(i), set);
		}
		Algorithm algo = new BMM();
		graph.init(algo, states);
		int round = 0;
		System.out.println("BEFORE:");
		print(nodes, states);
		while(graph.tick(algo, states)) {
			round++;
			System.out.println("ROUND: " + round);
			print(nodes, states);
			if(round == 6){
				break;
			}
		}
		System.out.println("AFTER:");
		print(nodes, states);
	}

	private static void connect(Node node1, int port1, Node node2, int port2) {
		node1.set(port1, node2);
		node2.set(port2, node1);
	}

	private static void print(List<Node> nodes,
			Map<Node, GettableSet<State>> states) {
		for(Node node: nodes){
			System.out.println(node + "=" + states.get(node));
		}
	}

}

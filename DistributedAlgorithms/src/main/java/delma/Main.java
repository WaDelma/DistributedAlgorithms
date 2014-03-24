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
		nodes.get(1).add(nodes.get(4), nodes.get(5), nodes.get(7));
		nodes.get(0).add(nodes.get(4), nodes.get(6));
		nodes.get(2).add(nodes.get(6));
		nodes.get(3).add(nodes.get(6), nodes.get(7));
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
		int i = 0;
		System.out.println("BEFORE:");
		print(nodes, states);
		while(graph.tick(algo, states)) {
			i++;
			System.out.println(i);
			print(nodes, states);
			if(i == 6){
				break;
			}
		}
		System.out.println("AFTER:");
		print(nodes, states);
	}

	private static void print(List<Node> nodes,
			Map<Node, GettableSet<State>> states) {
		for(Node node: nodes){
			System.out.println(node.getPorts().size() + ":" + states.get(node));
		}
	}

}

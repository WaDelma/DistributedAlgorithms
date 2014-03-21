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
		nodes.get(0).add(nodes.get(4), nodes.get(6));
		nodes.get(1).add(nodes.get(4), nodes.get(5), nodes.get(7));
		nodes.get(2).add(nodes.get(6));
		nodes.get(3).add(nodes.get(6), nodes.get(7));
		PortGraph graph = new PortGraph(nodes);
		Map<Node, GettableSet<State>> states = new HashMap<>();
		for(int i = 0; i < nodes.size(); i++) {
			GettableSet<State> set = new GettableSet<>();
			if(i < 3) {
				set.add(Color.WHITE);
			}else {
				set.add(Color.BLACK);
			}
			states.put(nodes.get(i), set);
		}
		Algorithm algo = new BMM();
		graph.init(algo, states);
		System.out.println(states);
		while(graph.tick(algo, states)) {
			System.out.println(states);
		}
	}

}

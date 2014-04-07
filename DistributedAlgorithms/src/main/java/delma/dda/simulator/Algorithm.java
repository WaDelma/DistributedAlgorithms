package delma.dda.simulator;

import java.util.Map;

import delma.dda.simulator.PortGraph.Node;


public interface Algorithm {
	void init(int length, Map<State, Object> states);

	boolean isRunning(Map<State, Object>  nodeStates);

	void receive(Node node, MessageCenter center, Map<State, Object> states);

	void send(Node node, MessageCenter center, Map<State, Object>  states);
}

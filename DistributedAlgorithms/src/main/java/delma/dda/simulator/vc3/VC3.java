package delma.dda.simulator.vc3;

import java.util.Map;

import delma.dda.simulator.Algorithm;
import delma.dda.simulator.MessageCenter;
import delma.dda.simulator.PortGraph.Node;
import delma.dda.simulator.State;

public class VC3 implements Algorithm {

	private Algorithm algo;

	public VC3(Algorithm algo) {
		this.algo = algo;
	}

	@Override
	public void init(int length, Map<State, Object> states) {

	}

	@Override
	public boolean isRunning(Map<State, Object> nodeStates) {
		return false;
	}

	@Override
	public void receive(Node node, MessageCenter center,
			Map<State, Object> states) {

	}

	@Override
	public void send(Node node, MessageCenter center, Map<State, Object> states) {

	}

}

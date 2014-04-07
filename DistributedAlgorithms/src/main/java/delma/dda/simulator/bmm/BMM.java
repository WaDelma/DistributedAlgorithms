package delma.dda.simulator.bmm;

import java.util.Map;

import delma.dda.simulator.Algorithm;
import delma.dda.simulator.Message;
import delma.dda.simulator.MessageCenter;
import delma.dda.simulator.PortGraph.Node;
import delma.dda.simulator.State;

public class BMM implements Algorithm {

	public enum BMMMessage implements Message {
		MATCHED, PROPOSAL, ACCEPT
	}

	public static enum BMMState implements State {
		EVEN, RUNNING, POTENTIAL, MATCHED, COLOR, ALL, ROUND
	}

	public static enum Color {
		WHITE, BLACK;
	}

	@Override
	public void init(int length, Map<State, Object> states) {
		states.put(BMMState.ALL, length);
		states.put(BMMState.ROUND, 1);
		states.put(BMMState.EVEN, Boolean.FALSE);
		states.put(BMMState.RUNNING, Boolean.TRUE);
		states.put(BMMState.POTENTIAL, -1);
	}

	@Override
	public boolean isRunning(Map<State, Object> states) {
		return states.get(BMMState.RUNNING) == Boolean.TRUE;
	}

	@Override
	public void receive(Node node, MessageCenter center,
			Map<State, Object> states) {
		if (states.get(BMMState.RUNNING) == Boolean.FALSE) {
			return;
		}
		boolean black = states.get(BMMState.COLOR) == Color.BLACK;
		boolean matched = states.containsKey(BMMState.MATCHED);
		boolean even = states.get(BMMState.EVEN) == Boolean.TRUE;
		int round = (int) states.get(BMMState.ROUND);
		int index = round / 2 + 1;
		int potential = (int) states.get(BMMState.POTENTIAL);
		int all = (int) states.get(BMMState.ALL);

		if (even) {
			states.put(BMMState.EVEN, Boolean.FALSE);
		} else {
			states.put(BMMState.EVEN, Boolean.TRUE);
		}
		states.put(BMMState.ROUND, round + 1);

		if (black) {
			if (!matched) {
				if (even) {
					if (potential != -1) {
						states.put(BMMState.MATCHED, potential);
						states.put(BMMState.RUNNING, Boolean.FALSE);
					}
					if (all == 0) {
						states.put(BMMState.RUNNING, Boolean.FALSE);
					}
				} else {
					for (int port = 1; port <= node.ports(); port++) {
						Message message = center.receive(node, port);
						if (message == BMMMessage.MATCHED) {
							states.put(BMMState.ALL, all - 1);
						}
						if (message == BMMMessage.PROPOSAL) {
							if (potential == -1 || port < potential) {
								states.put(BMMState.POTENTIAL, port);
								potential = port;
							}
						}
					}
				}
			}
		} else {
			if (matched) {
				states.put(BMMState.RUNNING, Boolean.FALSE);
			} else if (even) {
				for (int port = 1; port <= node.ports(); port++) {
					if (center.receive(node, port) == BMMMessage.ACCEPT) {
						states.put(BMMState.MATCHED, port);
					}
				}
			} else if (index > node.ports()) {
				states.put(BMMState.RUNNING, Boolean.FALSE);
			}
		}
	}

	@Override
	public void send(Node node, MessageCenter center, Map<State, Object> states) {
		if (states.get(BMMState.RUNNING) == Boolean.FALSE) {
			return;
		}
		boolean black = states.get(BMMState.COLOR) == Color.BLACK;
		boolean matched = states.containsKey(BMMState.MATCHED);
		boolean even = states.get(BMMState.EVEN) == Boolean.TRUE;
		int round = (int) states.get(BMMState.ROUND);
		int index = round / 2 + 1;
		int potential = (int) states.get(BMMState.POTENTIAL);

		if (black) {
			if (!matched && even && potential != -1) {
				center.send(node, potential, BMMMessage.ACCEPT);
			}
		} else if (!even) {
			if (matched) {
				for (int port = 1; port <= node.ports(); port++) {
					center.send(node, port, BMMMessage.MATCHED);
				}
			} else if (index <= node.ports()) {
				center.send(node, index, BMMMessage.PROPOSAL);
			}
		}
	}
}

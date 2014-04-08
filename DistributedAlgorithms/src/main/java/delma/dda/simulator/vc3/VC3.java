package delma.dda.simulator.vc3;

import java.util.HashMap;
import java.util.Map;

import delma.dda.simulator.Algorithm;
import delma.dda.simulator.Message;
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
		Node black = new Node("Simulated " + node.getLabel() + ": Black");
		Node white = new Node("Simulated " + node.getLabel() + ": White");
		MessageCenter simulatorCenter = new MessageCenter();
		for (int i = 1; i <= node.ports(); i++) {
			CompositeMessage msg = (CompositeMessage) center.receive(node, i);
			simulatorCenter.send(black, i, msg.getFirst());
			simulatorCenter.send(white, i, msg.getSecond());
		}
		Map<State, Object> simulatorStates = new HashMap<>();
		states.forEach((state, value) -> {
			simulatorStates.put(((CompositeState)state).getFirst(), ((Composite<?>)value).getFirst());
		});
		algo.receive(black, simulatorCenter, simulatorStates);

		simulatorStates.clear();
		states.forEach((state, value) -> {
			simulatorStates.put(((CompositeState)state).getSecond(), ((Composite<?>)value).getSecond());
		});
		algo.receive(white, simulatorCenter, simulatorStates);
		//TODO: We need some way to combine splitted States
	}

	@Override
	public void send(Node node, MessageCenter center, Map<State, Object> states) {
	}

	public static class Composite<T> {
		private final T first;
		private final T second;

		public Composite(T first, T second) {
			this.first = first;
			this.second = second;
		}

		public T getFirst() {
			return first;
		}

		public T getSecond() {
			return second;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (getFirst() == null ? 0 : getFirst().hashCode());
			result = prime * result + (getSecond() == null ? 0 : getSecond().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || !(obj instanceof CompositeMessage)) {
				return false;
			}
			CompositeMessage other = (CompositeMessage) obj;
			boolean flagM1 = getFirst() == other.getFirst();
			if (getFirst() != null) {
				flagM1 = getFirst().equals(other.getFirst());
			}
			boolean flagM2 = getSecond() == other.getSecond();
			if (getSecond() != null) {
				flagM2 = getSecond().equals(other.getSecond());
			}
			return flagM1 && flagM2;
		}
	}

	public static class CompositeMessage extends Composite<Message> implements Message {
		public CompositeMessage(Message first, Message second) {
			super(first, second);
		}
	}

	public static class CompositeState extends Composite<State> implements State {
		public CompositeState(State first, State second) {
			super(first, second);
		}
	}
}

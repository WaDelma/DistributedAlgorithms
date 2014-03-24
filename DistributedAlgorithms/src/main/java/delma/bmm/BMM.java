package delma.bmm;

import delma.Algorithm;
import delma.GettableSet;
import delma.Message;
import delma.MessageCenter;
import delma.Node;
import delma.State;

public class BMM implements Algorithm {

	@Override
	public void init(int length, GettableSet<State> states) {
		states.add(new All(length));
		states.add(new Round(1));
		states.add(Type.RUNNING);
		states.add(new Potential());
	}

	@Override
	public void receive(Node node, MessageCenter center,
			GettableSet<State> states) {
		if (!states.contains(Type.RUNNING)) {
			return;
		}
		boolean black = states.contains(Color.BLACK);
		boolean matched = states.contains(Matched.class);
		int round = states.get(Round.class).getRounds();
		boolean even = even(round);
		Potential potential = states.get(Potential.class);
		All all = states.get(All.class);

		if (black && !matched && even && potential.isNonEmpty()) {
			states.add(new Matched(potential.get()));
			states.add(Type.STOPPED);
		}
		if (black && !matched && even && all.isEmpty()) {
			states.add(Type.STOPPED);
		}
		for (int port = 1; port <= node.ports(); port++) {
			Message message = center.receive(node, port);
			if (black && !matched && !even && message == BMMMessage.MATCHED) {
				states.add(new All(all.get() - 1));
			}
			if (black && !matched && !even && message == BMMMessage.PROPOSAL) {
				states.add(new Potential(states.get(Potential.class), port));
			}
			if (!black && !matched && even && message == BMMMessage.ACCEPT) {
				states.add(new Matched(port));
			}
		}
		if (!black && !matched && !even && round > node.ports()) {
			states.add(Type.STOPPED);
		}
		if (!black && matched) {
			states.add(Type.STOPPED);
		}
		states.add(new Round(round + 1));
	}

	@Override
	public void send(Node node, MessageCenter center, GettableSet<State> states) {
		if (!states.contains(Type.RUNNING)) {
			return;
		}
		boolean black = states.contains(Color.BLACK);
		boolean matched = states.contains(Matched.class);
		int round = states.get(Round.class).getRounds();
		boolean even = even(round);
		Potential potential = states.get(Potential.class);

		if (black && !matched && even && potential.isNonEmpty()) {
			center.send(node, potential.get(), BMMMessage.ACCEPT);
		}
		if (!black && matched && !even) {
			for (int port = 1; port <= node.ports(); port++) {
				center.send(node, port, BMMMessage.MATCHED);
			}
		}
		if (!black && !matched && !even && round <= node.ports()) {
			center.send(node, round, BMMMessage.PROPOSAL);
		}

	}

	private boolean even(int round) {
		return (round & 1) == 0;
	}

	@Override
	public boolean isRunning(GettableSet<State> states) {
		return states.contains(Type.RUNNING);
	}
}

package delma.bmm;

import delma.Algorithm;
import delma.GettableSet;
import delma.Message;
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
	public void receive(Message[] messages, GettableSet<State> states) {
		if (states.contains(Color.BLACK)) {
			if (!states.contains(Matched.class)
					&& states.contains(Type.RUNNING)) {
				int round = states.get(Round.class).getRounds();
				states.add(new Round(round + 1));
				if (round % 2 == 0) {
					Potential potential = states.get(Potential.class);
					if (potential.isNonEmpty()) {
						states.add(new Matched(potential.get()));
						states.add(Type.STOPPED);
					}else if (states.get(All.class).isEmpty()) {
						states.add(Type.STOPPED);
					}
				} else {
					for (Message message : messages) {
						if (message == BMMMessage.MATCHED) {
							states.add(new All(
									states.get(All.class).get() - 1));
						} else if (message == BMMMessage.PROPOSAL) {
							Potential potential = states
									.get(Potential.class);
							potential.set(round - 1);
						}
					}
				}
			}
		} else {
			if (states.contains(Type.RUNNING)) {
				if (states.contains(Matched.class)) {
					states.add(Type.STOPPED);
				} else {
					int round = states.get(Round.class).getRounds();
					states.add(new Round(round + 1));
					if (round % 2 == 0) {
						for (int i = 0; i < messages.length; i++) {
							if (messages[i] == BMMMessage.ACCEPT) {
								states.add(new Matched(i));
							}
						}
					} else {
						if (round > messages.length) {
							states.add(Type.STOPPED);
						}
					}
				}
			}
		}
	}

	@Override
	public void send(Message[] messages, GettableSet<State> states) {
		if (states.contains(Color.BLACK)) {
			if (states.contains(Type.RUNNING)
					|| !states.contains(Matched.class)) {
				if (states.get(Round.class).getRounds() % 2 == 0) {
					Potential potential = states.get(Potential.class);
					if (potential.isNonEmpty()) {
						messages[potential.get()] = BMMMessage.ACCEPT;
					}
				}
			}
		} else {
			if (states.contains(Type.RUNNING)) {
				int round = states.get(Round.class).getRounds();
				if (states.contains(Matched.class)) {
					if (round % 2 == 1) {
						for (int i = 0; i < messages.length; i++) {
							messages[i] = BMMMessage.MATCHED;
						}
					}
				} else {
					if (round % 2 == 1) {
						if (round < messages.length) {
							messages[round - 1] = BMMMessage.PROPOSAL;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isRunning(GettableSet<State> states) {
		return states.contains(Type.RUNNING);
	}
}

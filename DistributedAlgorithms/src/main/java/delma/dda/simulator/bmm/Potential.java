package delma.dda.simulator.bmm;

import delma.dda.simulator.State;

public class Potential extends State {
	private int smallest = Integer.MAX_VALUE;

	public Potential() {
		super("POTENTIAL");
	}

	public Potential(Potential potential, int round) {
		this();
		smallest = Math.min(potential.smallest, round);
	}

	public int get() {
		return smallest;
	}

	public boolean isNonEmpty() {
		return smallest != Integer.MAX_VALUE;
	}

	@Override
	public String toString() {
		return super.toString() + ":" + (isNonEmpty() ? smallest : "empty");
	}
}

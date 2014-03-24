package delma.bmm;

import delma.State;

public class Potential extends State {
	private int smallest = Integer.MAX_VALUE;

	public Potential() {
		super("POTENTIAL");
	}

	public void set(int integer) {
		if (smallest > integer) {
			smallest = integer;
		}
	}

	public int get() {
		return smallest;
	}

	@Override
	public String toString() {
		return super.toString() + ":" + (isNonEmpty() ? smallest : "empty");
	}

	public boolean isNonEmpty() {
		return smallest != Integer.MAX_VALUE;
	}
}

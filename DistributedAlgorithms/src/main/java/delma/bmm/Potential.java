package delma.bmm;

import delma.State;

public class Potential extends State {
	private int smallest;

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
		return super.toString() + ":" + smallest;
	}
}

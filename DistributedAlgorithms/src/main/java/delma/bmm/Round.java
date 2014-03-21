package delma.bmm;

import delma.State;

public class Round extends State {
	private int times;

	public Round(int times) {
		super("ROUND");
		this.times = times;
	}

	public int getRounds() {
		return times;
	}

	@Override
	public String toString() {
		return super.toString() + ":" + times;
	}
}

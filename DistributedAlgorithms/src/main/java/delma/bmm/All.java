package delma.bmm;

import delma.State;

public class All extends State {
	private int amount;

	public All(int amount) {
		super("ALL");
		this.amount = amount;
	}

	public int get() {
		return amount;
	}

	public boolean isEmpty() {
		return amount <= 0;
	}

	@Override
	public String toString() {
		return super.toString() + ":" + amount;
	}
}

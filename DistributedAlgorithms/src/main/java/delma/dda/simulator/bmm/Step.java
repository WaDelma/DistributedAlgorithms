package delma.dda.simulator.bmm;

import delma.dda.simulator.State;

public class Step extends State {
	public static final State EVEN = new Step("EVEN");
	public static final State ODD = new Step("ODD");

	public Step(String name) {
		super(name);
	}
}

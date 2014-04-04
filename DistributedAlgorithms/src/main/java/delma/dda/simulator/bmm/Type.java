package delma.dda.simulator.bmm;

import delma.dda.simulator.State;

public class Type extends State {
	public static Type RUNNING = new Type("RUNNING");
	public static Type STOPPED = new Type("STOPPED");

	private Type(String name) {
		super(name);
	}
}

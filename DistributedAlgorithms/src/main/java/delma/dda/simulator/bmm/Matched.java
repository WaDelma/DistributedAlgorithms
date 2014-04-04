package delma.dda.simulator.bmm;

import delma.dda.simulator.State;

public class Matched extends State {
	public static final Matched UNMATCHED = new Matched(-1);
	private int port;

	public Matched(int port) {
		super("MATCHED:" + port);
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return super.toString() + ":" + port;
	}

}

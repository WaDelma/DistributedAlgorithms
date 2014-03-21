package delma.bmm;

import delma.State;

public class Matched extends State {
	private int port;

	public Matched(int port) {
		super("MATCHED");
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

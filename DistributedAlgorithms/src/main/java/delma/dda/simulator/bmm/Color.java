package delma.dda.simulator.bmm;

import delma.dda.simulator.State;

public class Color extends State{
	public static Color BLACK = new Color("BLACK");
	public static Color WHITE = new Color("WHITE");

	private Color(String name) {
		super(name);
	}
}

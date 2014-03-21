package delma.bmm;

import delma.State;

public class Color extends State{
	public static Color BLACK = new Color("BLACK");
	public static Color WHITE = new Color("WHITE");

	private Color(String name) {
		super(name);
	}
}

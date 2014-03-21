package delma;


public class State {
	private String name;

	public State(String name) {
		this.name = name;
	}
	@Override
	public final int hashCode() {
		return name.hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if(this.getClass().isAssignableFrom(obj.getClass())) {
			return name.equals(((State) obj).name);
		}
		return false;
	}
	@Override
	public String toString() {
		return name;
	}
}

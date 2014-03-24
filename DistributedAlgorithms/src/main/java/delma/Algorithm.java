package delma;


public interface Algorithm {
	void init(int length, GettableSet<State> states);

	void receive(Node node, MessageCenter center, GettableSet<State> states);

	void send(Node node, MessageCenter center, GettableSet<State> states);

	boolean isRunning(GettableSet<State> nodeStates);
}

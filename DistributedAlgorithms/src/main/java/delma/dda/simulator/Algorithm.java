package delma.dda.simulator;


public interface Algorithm {
	void init(int length, GettableSet<State> states);

	boolean isRunning(GettableSet<State> nodeStates);

	void receive(Node node, MessageCenter center, GettableSet<State> states);

	void send(Node node, MessageCenter center, GettableSet<State> states);
}

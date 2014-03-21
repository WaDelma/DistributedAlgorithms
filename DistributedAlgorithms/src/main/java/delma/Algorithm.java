package delma;


public interface Algorithm {
	void init(int length, GettableSet<State> states);

	void receive(Message[] messages, GettableSet<State> states);

	void send(Message[] messages, GettableSet<State> states);

	boolean isRunning(GettableSet<State> nodeStates);
}

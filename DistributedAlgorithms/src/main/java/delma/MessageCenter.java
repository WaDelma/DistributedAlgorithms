package delma;

import java.util.HashMap;
import java.util.Map;

public class MessageCenter {
	private Map<Pair<Node, Integer>, Message> map;

	public MessageCenter() {
		map = new HashMap<>();
	}

	public void send(Node node, int port, Message message) {
		System.out.println("send (" + node + ", " + port + ") = " + message);
		map.put(new Pair<>(node, port),  message);
	}

	public Message receive(Node node, int port) {
		System.out.println("(" + node + ", " + port + ") " + map);
		if(port > node.ports()) {
			return null;
		}
		Message temp = map.get(new Pair<>(node.get(port), node.get(port).get(node)));
		System.out.println("receive (" + node.get(port) + ", " + node.get(port).get(node) + ") = " + temp);
		return temp;
	}

	public void clear() {
		map.clear();
	}

	private static class Pair<T, E> {
		private final T t;
		private final E e;

		private Pair(T t, E e) {
			this.t = t;
			this.e = e;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (e == null ? 0 : e.hashCode());
			result = prime * result + (t == null ? 0 : t.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || !(obj instanceof Pair)) {
				return false;
			}
			Pair<?, ?> other = (Pair<?, ?>) obj;
			boolean eFlag = e == null ? other.e == null : e.equals(other.e);
			boolean tFlag = t == null ? other.t == null : t.equals(other.t);
			return eFlag && tFlag;
		}

		@Override
		public String toString() {
			return "(" + t + ", " + e + ")";
		}

	}
}

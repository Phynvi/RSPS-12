package ethos;

public enum ServerState {

	PUBLIC_PRIMARY(43595), PUBLIC_SECONDARY(43595), PRIVATE(43595);

	private int port;

	ServerState(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

}

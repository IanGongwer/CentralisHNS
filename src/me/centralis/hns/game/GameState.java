package me.centralis.hns.game;

public enum GameState {
	Lobby, InGame, End;

	private static GameState state;

	public static void setState(GameState state) {
		GameState.state = state;
	}

	public static boolean isState(GameState state) {
		return GameState.state == state;
	}

	public static GameState getState() {
		return state;
	}

	public static boolean isLobby() {
		return GameState.isState(GameState.Lobby);
	}

	public static boolean isInGame() {
		return GameState.isState(GameState.InGame);
	}

	public static boolean isEnd() {
		return GameState.isState(GameState.End);
	}
}
package w3r3w0lf;

import java.net.Socket;

public class Player {
	// Steuerung + A, Steuerung + Shift + F = Einrücken
	boolean isAlive;
	Socket playerSocket;
	String playerName;
	playerRole playerRole;

	public enum playerRole {
		villager, werewolf, witch, armor, girl, hunter, seer
	}

	public void enumPlayerRole(playerRole PlayerRole) {
		this.playerRole = PlayerRole;
	}

	public void SendMessage(String msg) {

	}

	public void turnStart() {

	}

	public void turnEnd() {

	}
}

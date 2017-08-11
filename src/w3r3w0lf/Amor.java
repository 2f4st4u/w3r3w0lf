package w3r3w0lf;

import java.net.Socket;

public class Amor extends Player {

	public Amor(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}

	@Override
	public void TurnStart() {
		SendMessage("amor;selectLovers");
		String message1 = GetMessage();
		String message2 = GetMessage();
		if (!manager.PlayerExists(message1) || !manager.PlayerExists(message2)) {
			return;
		}
		Player player1 = manager.GetPlayerByName(message1);
		Player player2 = manager.GetPlayerByName(message2);
		
		if (!player1.isAlive || !player2.isAlive)
		{
			return;
		}
		
		player1.SendMessage("lover;" + player2.playerName);
		player1.lover = player2;
		player2.SendMessage("lover;" + player1.playerName);
		player2.lover = player1;
	}
}

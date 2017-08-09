package w3r3w0lf;

import java.net.Socket;

public class Werewolf extends Player {

	public Werewolf(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}
	
	@Override
	public void TurnStart() {
		if (!isAlive)
		{
			return;
		}
		
		SendMessage("werewolf;selectTarget");
		String response = GetMessage();
		if (!manager.PlayerExists(response))
		{
			return;
		}
		
		manager.BroadcastToRole(PlayerRole.werewolf, "werewolf;selected;" + playerName + ";" + response);
	}
}

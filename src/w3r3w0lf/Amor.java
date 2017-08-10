package w3r3w0lf;

import java.net.Socket;

public class Amor extends Player {

	public Amor(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}

	
	@Override
	public void TurnStart()
	{
		super.TurnStart();
		SendMessage("armor;selectLovers;");
		String lover1 = GetMessage();
		String lover2 = GetMessage();
		if (!manager.PlayerExists(lover1)||!manager.PlayerExists(lover2)) {
			return;
		}
		
		BindPlayers(lover1,lover2);
		//manager.Broadcast("hunterKilled;" + response);
	}
	
	private void BindPlayers(String lover1, String lover2)
	{
		manager.GetPlayerByName(lover1).SendMessage("lover;" + lover2);
		manager.GetPlayerByName(lover2).SendMessage("lover;" + lover1);
		manager.GetPlayerByName(lover1).lover = manager.GetPlayerByName(lover2);
		manager.GetPlayerByName(lover2).lover = manager.GetPlayerByName(lover1);
	}
}
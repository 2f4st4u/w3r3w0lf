package w3r3w0lf;

import java.net.Socket;

public class Witch extends Player {
	
	public Boolean hasKilled;
	public Boolean hasSaved;
	
	public Witch(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}

	@Override
	public void TurnStart() {
		if (!hasKilled)
		{
			SendMessage("witch;selectKillTarget");
			String killTarget = GetMessage();
			if (!manager.PlayerExists(killTarget))
			{
				return;
			}
			manager.KillPlayer(killTarget);
			hasKilled = true;
		}
		
		String message = "witch;savePlayer";
		
		for (Player ply : manager.killedPlayers)
		{
			message += ";" + ply.playerName;
		}
		
		SendMessage(message);
		String save = GetMessage();
		if (!manager.PlayerExists(save))
		{
			return;
		}
		
		for (Player ply : manager.killedPlayers)
		{
			if (ply.playerName == save)
			{
				manager.killedPlayers.remove(ply);
			}
		}
	}
}
package w3r3w0lf;

import java.net.Socket;

public class Witch extends Player {
	
	public Boolean hasKilled = false;
	public Boolean hasSaved = false;
	
	public Witch(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}

	@Override
	public void TurnStart() {
		if (!isAlive)
		{
			return;
		}
		
		if (!hasKilled)
		{
			SendMessage("witch;selectKillTarget");
			String killTarget = GetMessage();
			if (manager.PlayerExists(killTarget))
			{
				manager.KillPlayer(killTarget);
				hasKilled = true;
			}
		}
		
		if (!hasSaved)
		{
			String message = "witch;savePlayer";
		
			for (Player ply : manager.killedPlayers)
			{
				message += ";" + ply.playerName;
			}
		
			SendMessage(message);
			Player removedPlayer = null;
			String save = GetMessage();
			if (manager.PlayerExists(save))
			{
				for (Player ply : manager.killedPlayers)
				{
					if (ply.playerName.equals(save))
					{			
						removedPlayer = ply;
						}
				}
			}
			if (removedPlayer != null){
				manager.killedPlayers .remove(removedPlayer);
				hasSaved = true;
			}
		
			
		}
	}
}
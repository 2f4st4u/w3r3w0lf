package w3r3w0lf;

import java.util.*;
import w3r3w0lf.Player.PlayerRole;

public class GameManager {
	List<PlayerRole> availableRoles;
	List<Player> players;
	int round = 0;
	
	public void Initialize(List<LobbyClient> clients, List<PlayerRole> roles)
	{
		availableRoles = roles;
		players = new ArrayList<Player>();
		Random rand = new Random();
		
		for (Iterator<LobbyClient> i = clients.iterator(); i.hasNext();)
		{
			LobbyClient client = i.next();
			int randomInt = rand.nextInt(clients.size());
			switch (availableRoles.get(randomInt))
			{
			case armor:
				break;
			case girl:
				break;
			case hunter:
				break;
			case none:
				break;
			case seer:
				break;
			case villager:
				break;
			case werewolf:
				players.add(new Werewolf(client.playerSocket, client.playerName, PlayerRole.werewolf, this));
				break;
			case witch:
				break;
			default:
				break;}
			
			availableRoles.remove(randomInt);
		}
	}
	
	public void StartGame()
	{
		for(Player player : players)
		{
			player.SendMessage("role;" + player.role);
		}
		
		NextRound();
	}
	
	private void NextRound()
	{
		round++;
		
		//List<Thread> threadList = new ArrayList<Thread>();
		for (Player ply : GetPlayersByRole(Player.PlayerRole.werewolf))
		{
			Werewolf werewolf = (Werewolf)ply;
			werewolf.TurnStart();
			//Thread th = new Thread(werewolf);
			//th.run();
			//threadList.add(th);
		}
		/*for (Thread th : threadList)
		{
			try {
				th.join(60000);
			} catch (InterruptedException e) {}
		}*/
		
		
	}
	
	public void Broadcast(String msg)
	{
		for(Player player : players)
		{
			player.SendMessage(msg);
		}
	}
	
	public void BroadcastToRole(PlayerRole role, String msg)
	{
		for (Iterator<Player> i = players.iterator(); i.hasNext();)
		{
			Player player = i.next();
			if (player.role == role) 
			{
				 player.SendMessage(msg);
			}
		}
	}
	
	public Boolean PlayerExists(String name)
	{
		for (Iterator<Player> i = players.iterator(); i.hasNext();)
		{
			if (i.next().playerName.equals(name))
			{
				return true;
			}
		}
		return false;
	}

	public Player GetPlayerByName(String name)
	{
		for (Iterator<Player> i = players.iterator(); i.hasNext();)
		{
			Player ply = i.next();
			if (ply.playerName.equals(name))
			{
				return ply;
			}
		}
		return null;
	}
	
	public void KillPlayer(String name)
	{
		Player ply = GetPlayerByName(name);
		if (ply == null)
		{
			return;
		}
		
		ply.Killed();
	}
	
	public List<Player> GetPlayersByRole(PlayerRole role)
	{
		List<Player> returnList = new ArrayList<Player>();
		for(Player player : players)
		{
			if (player.role == role)
			{
				returnList.add(player);
			}
		}
		
		return returnList;
	}
}

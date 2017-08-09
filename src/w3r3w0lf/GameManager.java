package w3r3w0lf;

import java.util.*;
import w3r3w0lf.Player.PlayerRole;

public class GameManager {
	List<PlayerRole> availableRoles;
	List<Player> players;
	
	public void Initialize(List<LobbyClient> clients, List<PlayerRole> roles)
	{
		availableRoles = new ArrayList<PlayerRole>();
		players = new ArrayList<Player>();
		
		for (Iterator<LobbyClient> i = clients.iterator(); i.hasNext();)
		{
			
		}
	}
	
	public void Broadcast(String msg)
	{
		for (Iterator<Player> i = players.iterator(); i.hasNext();)
		{
			i.next().SendMessage(msg);
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
}

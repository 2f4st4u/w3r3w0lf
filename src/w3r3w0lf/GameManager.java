package w3r3w0lf;

import java.util.*;
import w3r3w0lf.Player.PlayerRole;

public class GameManager {
	List<PlayerRole> availableRoles;
	List<Player> players;
	List<Player> killedPlayers;
	int round = 0;
	
	public void Initialize(List<LobbyClient> clients, List<PlayerRole> roles)
	{
		availableRoles = roles;
		players = new ArrayList<Player>();
		killedPlayers = new ArrayList<Player>();
		Random rand = new Random();
		
		for (Iterator<LobbyClient> i = clients.iterator(); i.hasNext();)
		{
			LobbyClient client = i.next();
			int randomInt = rand.nextInt(clients.size() - 1);
			switch (availableRoles.get(randomInt))
			{
			case armor:
				break;
			case girl:
				break;
			case hunter:
				players.add(new Hunter(client.playerSocket, client.playerName, PlayerRole.hunter, this));
				break;
			case none:
				break;
			case seer:
				break;
			case villager:
				players.add(new Player(client.playerSocket, client.playerName, PlayerRole.villager, this));
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
		Broadcast("round;" + round);
		
		if (round == 1)
		{
			if (GetPlayerByRole(PlayerRole.armor) != null)
			{
				Broadcast("turn;armor");
				GetPlayerByRole(PlayerRole.armor).TurnStart();
			}
		}
		
		Werewolves();
		OnNightEnd();
		DayVote();
		if (!CheckWin())
		{
			NextRound();
		}
	}

	private void Werewolves()
	{
		List<Vote> votes = new ArrayList<Vote>();
		Vote biggestVote = null;
		
		Broadcast("turn;werewolves");
		
		for (Player ply : GetPlayersByRole(Player.PlayerRole.werewolf))
		{
			Werewolf werewolf = (Werewolf)ply;
			werewolf.TurnStart();
			Boolean voted = false;
			for (Vote v : votes)
			{
				if (werewolf.vote == null || werewolf.vote.equals(""))
				{
					break;
				}
				
				if (v.identifier.equals(werewolf.vote))
				{
					v.numOfVotes++;
					voted = true;
					break;
				}
			}
			if (!voted)
			{
				votes.add(new Vote(werewolf.vote));
			}
		}
		
		for (Vote v : votes)
		{
			if (biggestVote == null)
			{
				biggestVote = v;
				continue;
			}
			
			if (v.numOfVotes > biggestVote.numOfVotes)
			{
				biggestVote = v;
			}
		} 
		
		KillPlayer(biggestVote.identifier);
	}
	
	private void OnNightEnd()
	{
		Broadcast("nightend");
		
		for(Player player : killedPlayers)
		{
			Broadcast("killed;" + player.playerName);
			player.Killed();
		}
		
		killedPlayers.clear();
	}
	
	private void DayVote()
	{
		List<Vote> votes = new ArrayList<Vote>();
		Vote biggestVote = null;
		
		for (Player ply : players)
		{
			ply.Vote();
			if (ply.vote == null || ply.vote.equals(""))
			{
				continue;
			}
			
			Boolean voted = false;
			for (Vote v : votes)
			{
				
				
				if (v.identifier.equals(ply.vote))
				{
					v.numOfVotes++;
					voted = true;
					break;
				}
			}
			if (!voted)
			{
				votes.add(new Vote(ply.vote));
			}
		}
		
		for (Vote v : votes)
		{
			if (biggestVote == null)
			{
				biggestVote = v;
				continue;
			}
			
			if (v.numOfVotes > biggestVote.numOfVotes)
			{
				biggestVote = v;
			}
		} 
		if (biggestVote == null || GetPlayerByName(biggestVote.identifier) == null)
		{
			return;
		}
		GetPlayerByName(biggestVote.identifier).Killed();
		Broadcast("executed;" + GetPlayerByName(biggestVote.identifier).playerName);
	}
	
	private boolean CheckWin()
	{
		int numOfWerewolfes = 0;
		int numOfVillagers = 0;
		for (Player ply : players)
		{
			if (ply.role == Player.PlayerRole.werewolf)
			{
				if (ply.isAlive)
					numOfWerewolfes++;
			}
			else if(ply.isAlive)
			{
				numOfVillagers++;
			}
		}
		
		if (numOfWerewolfes == 0)
		{
			Broadcast("win;villager");
			return true;
		}
		else if (numOfVillagers == 0)
		{
			Broadcast("win;werewolf");
			return true;
		}
		
		return false;
	}
	
	public void Broadcast(String msg)
	{
		for(Player player : players)
		{
			player.SendMessage(msg);
		}
	}
	
	public void Broadcast(String msg, Boolean dead)
	{
		for(Player player : players)
		{
			if (!dead && player.isAlive)
			{
				player.SendMessage(msg);
			}
			else if (dead)
			{
				player.SendMessage(msg);
			}
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
	
	public Player GetPlayerByRole(PlayerRole role)
	{
		for (Player ply : players)
		{
			if (ply.role == role)
			{
				return ply;
			}
		}
		return null;
	}
	
	public void KillPlayer(String name)
	{
		Player ply = GetPlayerByName(name);
		if (ply == null || killedPlayers.contains(ply))
		{
			return;
		}
		if (ply.lover != null)
		{
			killedPlayers.add(ply.lover);
		}
		killedPlayers.add(ply);
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

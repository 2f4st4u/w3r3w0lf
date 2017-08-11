package w3r3w0lf;

import java.util.*;
import w3r3w0lf.Player.PlayerRole;

public class GameManager {
	public List<PlayerRole> availableRoles;
	public List<Player> players;
	public List<Player> killedPlayers;
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
			int randomInt = rand.nextInt(availableRoles.size());
			switch (availableRoles.get(randomInt))
			{
			case amor:
				players.add(new Amor(client.playerSocket, client.playerName, PlayerRole.amor, this));
				break;
			case girl:
				break;
			case hunter:
				players.add(new Hunter(client.playerSocket, client.playerName, PlayerRole.hunter, this));
				break;
			case none:
				break;
			case seer:
				players.add(new Seer(client.playerSocket, client.playerName, PlayerRole.seer, this));
				break;
			case villager:
				players.add(new Player(client.playerSocket, client.playerName, PlayerRole.villager, this));
				break;
			case werewolf:
				players.add(new Werewolf(client.playerSocket, client.playerName, PlayerRole.werewolf, this));
				break;
			case witch:
				players.add(new Witch(client.playerSocket, client.playerName, PlayerRole.witch, this));
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
			if (GetPlayerByRole(PlayerRole.amor) != null)
			{
				Broadcast("turn;amor");
				GetPlayerByRole(PlayerRole.amor).TurnStart();
			}
		}
		
		if (CheckWin())
		{
			return;
		}
		
		Werewolves();
		if (GetPlayerByRole(PlayerRole.seer) != null)
		{
			Broadcast("turn;seer");
			GetPlayerByRole(PlayerRole.seer).TurnStart();
		}
		if (GetPlayerByRole(PlayerRole.witch) != null)
		{
			Broadcast("turn;witch");
			GetPlayerByRole(PlayerRole.witch).TurnStart();
		}
		OnNightEnd();
		if (CheckWin())
		{
			return;
		}
		DayVote();
		if (CheckWin())
		{
			return;
		}
		
		NextRound();
	}

	private void Werewolves()
	{
		List<Vote> votes = new ArrayList<Vote>();
		Vote biggestVote = null;
		
		Broadcast("turn;werewolves");
		
		for (Player ply : GetPlayersByRole(Player.PlayerRole.werewolf))
		{
			ply.TurnStart();
			Boolean voted = false;
			for (Vote v : votes)
			{
				if (ply.vote == null || ply.vote.equals(""))
				{
					break;
				}
				
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
		
		KillPlayer(biggestVote.identifier);
	}
	
	private void OnNightEnd()
	{
		Broadcast("nightend");
		
		for(Player player : killedPlayers)
		{
			player.Killed(" was killed in the night.");
			if (player.lover != null)
			{
				player.lover.Killed(" died of a broken hearth.");
			}
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
		GetPlayerByName(biggestVote.identifier).Killed(" was executed by vote.");
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
		else if (numOfWerewolfes + numOfVillagers == 2)
		{
			for (Player ply : players)
			{
				if (ply.isAlive && ply.lover != null && ply.lover.isAlive)
				{
					Broadcast("win;love");
					return true;
				}
			}
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
		if (ply == null || killedPlayers.contains(ply) || !ply.isAlive)
		{
			return;
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

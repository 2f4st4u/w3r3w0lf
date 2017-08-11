package w3r3w0lf;

import java.io.IOException;
import java.net.*;
import java.util.*;

import w3r3w0lf.Player.PlayerRole;

public class LobbyManager {
	public List<LobbyClient> connectedClients;
	public ServerSocket serverSocket;
	private LobbyListener listener;
	private GameManager manager;
	
	public void Initialize(int port) throws IOException
	{
		connectedClients = new ArrayList<LobbyClient>();
		serverSocket = new ServerSocket(port);
		StartListening();
	}
	
	public void StartListening()
	{
		if (serverSocket == null)
		{
			return;
		}
		
		listener = new LobbyListener();
		listener.manager = this;
		new Thread(listener).start();
	}
	
	public void StopListening()
	{
		if (listener == null)
		{
			return;
		}
		listener.Stop();
	}
	
	public void CloseServer()
	{
		StopListening();
		for (Iterator<LobbyClient> i = connectedClients.iterator(); i.hasNext();)
		{
			LobbyClient client = i.next();
			client.SendMessage("serverstop");
			try {
				client.playerSocket.close();
			} catch (IOException e) {}
		}
	}
	
	public void ChangeName(String name)
	{
		for (LobbyClient client : connectedClients)
		{
			client.SendMessage("servername;" + name);
		}
	}
	
	public void StartGame(List<PlayerRole> roles)
	{
		for (Iterator<LobbyClient> i = connectedClients.iterator(); i.hasNext();)
		{
			LobbyClient client = i.next();
			client.SendMessage("startgame");
		}
		
		for (int i = 0; i <= connectedClients.size() - roles.size(); i++)
		{
			roles.add(PlayerRole.villager);
		}
		
		manager = new GameManager();
		manager.Initialize(connectedClients, roles);
		manager.StartGame();
	}
}

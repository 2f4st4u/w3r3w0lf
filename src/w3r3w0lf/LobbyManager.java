package w3r3w0lf;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class LobbyManager {
	public List<LobbyClient> connectedClients;
	public ServerSocket serverSocket;
	private LobbyListener listener;
	
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
}

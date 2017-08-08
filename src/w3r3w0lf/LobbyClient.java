package w3r3w0lf;

import java.net.*;

public class LobbyClient {
	Socket playerSocket;
	String playerName;
	
	LobbyClient(Socket sock, String name)
	{
		playerSocket = sock;
		playerName = name;
	}
}

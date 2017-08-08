package w3r3w0lf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class LobbyClient {
	Socket playerSocket;
	String playerName;
	
	LobbyClient(Socket sock, String name)
	{
		playerSocket = sock;
		playerName = name;
	}
	
	public Boolean isConnected()
	{
		try {
			new DataOutputStream(playerSocket.getOutputStream()).writeBytes("ping\n");
			if (new BufferedReader(new InputStreamReader(playerSocket.getInputStream())).readLine().equals("pong"))
			{
				return true;
			}
			else 
			{
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}
}

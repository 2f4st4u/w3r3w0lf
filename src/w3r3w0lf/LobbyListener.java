package w3r3w0lf;

import java.io.*;
import java.net.*;

public class LobbyListener implements Runnable {
	private Boolean shouldRun = true;
	public LobbyManager manager;
	
	@Override
	public void run() {
		while (shouldRun && manager.serverSocket != null)
		{
			try {
				Socket client = manager.serverSocket.accept();
				//client.setSoTimeout(2000);
				
				String name = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
				Boolean disconnect = false;
				for(LobbyClient cl : manager.connectedClients)
				{
					if (cl.playerName.equals(name))
					{
						new LobbyClient(client, name).SendMessage("disconnect;Name already exists!");
						client.close();
						disconnect = true;
						break;
					}
					
					cl.SendMessage("playerJoined;" + cl.playerName);
				}
				
				if (disconnect)
				{
					continue;
				}
				
				System.out.print("Client connected!\n");
				System.out.print("Name: " + name + "\n");
				manager.connectedClients.add(new LobbyClient(client, name));
				
			} catch (IOException e) {
				e.printStackTrace();
				Stop();
			}
		}
	}
	
	public void Stop()
	{
		shouldRun = false;
	}

}

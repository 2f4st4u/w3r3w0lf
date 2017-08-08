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
				client.setSoTimeout(2000);
				System.out.print("Client connected!\n");
				String name = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
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

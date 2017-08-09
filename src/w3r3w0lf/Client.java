package w3r3w0lf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client implements Runnable {
	Socket serverSocket;
	
	public Boolean Connect(InetAddress host, int port, String name)
	{
		try {
			serverSocket = new Socket(host, port);
			new DataOutputStream(serverSocket.getOutputStream()).writeBytes(name + "\n");;
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String WaitForMessage() throws IOException
	{
		return new BufferedReader(new InputStreamReader(serverSocket.getInputStream())).readLine();
	}
	
	private void SendMessage(String msg)
	{
		try {
			new DataOutputStream(serverSocket.getOutputStream()).writeBytes(msg + "\n");
		} catch (IOException e) {
			return;
		}
	}
	
	private void ProcessMessage(String msg)
	{
		if (msg.equals("ping"))
		{
			SendMessage("pong");
		}
		else if (msg.equals("serverstop"))
		{
			System.out.print("Server was stopped!\n");
		}
		else if (msg.equals("startgame")) 
		{
			System.out.print("Game was started!\n");
		}
	}

	@Override
	public void run() {
		while (true) 
		{
			try {
				String input = WaitForMessage();
				if (input == null)
				{
					Thread.yield();
					continue;
				}
				
				ProcessMessage(input);
				
			} catch (IOException e) {
				return;
			}
			
		}
	}
}

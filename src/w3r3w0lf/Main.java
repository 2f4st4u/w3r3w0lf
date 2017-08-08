package w3r3w0lf;

import java.util.*;
import java.io.IOException;
import java.net.InetAddress;



public class Main {
public static void main(String[] args) throws IOException
{
	Scanner scan = new Scanner(System.in);
	
	System.out.print("Do you want to host or join?\n");
	String answer = scan.nextLine();
	if (answer.equalsIgnoreCase("host"))
	{
		System.out.print("Server starting!\n");
		LobbyManager lobbyManager = new LobbyManager();
		lobbyManager.Initialize(1337);
		while (true)
		{
			String input = scan.nextLine();
			if (input.equalsIgnoreCase("stop"))
			{
				
				lobbyManager.StopListening();
				System.out.print("Stopped Server!\n");
				System.exit(0);
			}
		}
	}
	else if (answer.equalsIgnoreCase("join"))
	{
		System.out.print("Enter the IP Address:\n");
		InetAddress ip = InetAddress.getByName(scan.nextLine());
		System.out.print("Enter port number:\n");
		int port = scan.nextInt();
		scan.nextLine();
		System.out.print("Enter your name:\n");
		String name = scan.nextLine();
		
		Client player = new Client();
		player.Connect(ip, port, name);
	}
	scan.close();
	System.exit(0);
}
}

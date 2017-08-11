package w3r3w0lf;

import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;



public class Main extends Application{
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
				lobbyManager.CloseServer();
				System.out.print("Stopped Server!\n");
				System.exit(0);
			}
			else if (input.equalsIgnoreCase("start"))
			{

				List<Player.PlayerRole> roles = new ArrayList<Player.PlayerRole>();
				roles.add(Player.PlayerRole.werewolf);
				roles.add(Player.PlayerRole.villager);
				roles.add(Player.PlayerRole.witch);
				
				lobbyManager.StartGame(roles);
			}
			else if (input.equalsIgnoreCase("list"))
			{
				for (Iterator<LobbyClient> i = lobbyManager.connectedClients.iterator(); i.hasNext();)
				{
					LobbyClient client = i.next();
					if (client.isConnected())
					{
						System.out.print(client.playerName + ": connected\n");
					}
					else 
					{
						System.out.print(client.playerName + ": disconnected\n");
						i.remove();
					}
				}
			}
		}
	}
	else if (answer.equalsIgnoreCase("join"))
	{
		/*System.out.print("Enter the IP Address:\n");
		InetAddress ip = InetAddress.getByName(scan.nextLine());
		System.out.print("Enter port number:\n");
		int port = scan.nextInt();
		scan.nextLine();*/
		System.out.print("Enter your name:\n");
		String name = scan.nextLine();
		
		Client player = new Client();
		player.Connect(InetAddress.getByName("localhost"), 1337, name);
		player.run();
		scan.close();
		System.exit(0);
	}
	scan.close();
	System.out.println("Das Dorf schläft ein.....\n");
	launch(args);
	System.exit(0);
}

@Override	
	public void start(Stage arg0) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\resources\\gui_start.fxml"));
	    Scene sc = new Scene(loader.load());
		ExpController c = new ExpController();
		arg0.setScene(sc);
		arg0.show();
	}
}

/*@Override
public void start(Stage arg0) throws Exception {
	// TODO Auto-generated method stub
	
}
*/

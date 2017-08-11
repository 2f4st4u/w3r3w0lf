package w3r3w0lf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

import w3r3w0lf.Player.PlayerRole;

public class Client implements Runnable {
	Socket serverSocket;
	Player.PlayerRole role;
	String playerName;
	Scanner scan = new Scanner(System.in);
	List<String> players = new ArrayList<String>();

	public Boolean Connect(InetAddress host, int port, String name) {
		try {
			playerName = name;
			serverSocket = new Socket(host, port);
			new DataOutputStream(serverSocket.getOutputStream()).writeBytes(name + "\n");
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private String WaitForMessage() {
		try {
			return new BufferedReader(new InputStreamReader(serverSocket.getInputStream())).readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private void SendMessage(String msg) {
		try {
			new DataOutputStream(serverSocket.getOutputStream()).writeBytes(msg + "\n");
		} catch (IOException e) {
			return;
		}
	}

	private void ProcessMessage(String msg) {
		if (msg.equals("ping")) {
			SendMessage("pong");
		} else if (msg.equals("serverstop")) {
			OnServerStop();
		} else if (msg.equals("startgame")) {
			OnStartGame();
		} else if (msg.startsWith("role;")) {
			OnRoleAssign(Player.PlayerRole.valueOf(msg.replaceFirst("role;", "")));
		} else if (msg.startsWith("round;")) {
			OnRoundStart(Integer.parseInt(msg.replaceFirst("round;", "")));
		} else if (msg.startsWith("win;")) {
			OnGameEnd(msg.replaceFirst("win;", ""));
		} else if (msg.equals("vote")) {
			OnVote();
		} else if (msg.startsWith("turn;")) {
			OnTurn(msg.replaceFirst("turn;", ""));
		} else if (msg.startsWith("killed;")) {
			OnPlayerKilled(msg.replaceFirst("killed;", "").split(";")[0], msg.replaceFirst("killed;", "").split(";")[1]);
		} else if (msg.equals("nightend")) {
			OnNightEnd();
		} else if (msg.startsWith("lover;")) {
			OnLover(msg.replaceFirst("lover;", ""));
		} else if (msg.startsWith("playerJoined;")) {
			OnPlayerJoined(msg.replaceFirst("playerJoined;", ""));
		}
			
		else if (msg.startsWith("werewolf;") && role == Player.PlayerRole.werewolf) {
			ProcessWerewolf(msg.replaceFirst("werewolf;", ""));
			return;
		}
		else if (msg.startsWith("witch;") && role == Player.PlayerRole.witch) {
			ProcessWitch(msg.replaceFirst("witch;", ""));
			return;
		}
		else if (msg.startsWith("hunter;") && role == Player.PlayerRole.hunter) {
			ProcessHunter(msg.replaceFirst("hunter;", ""));
			return;
		}
		else if (msg.startsWith("amor;") && role == Player.PlayerRole.amor) {
			ProcessAmor(msg.replaceFirst("amor;", ""));
			return;
		}
		else if (msg.startsWith("seer;") && role == Player.PlayerRole.seer) {
			ProcessSeer(msg.replaceFirst("seer;", ""));
			return;
		}
	}

	private void ProcessWerewolf(String msg) {
		if (msg.equals("selectTarget")) {
			System.out.print("Select a person to kill:\n");
			String person = scan.nextLine();
			SendMessage(person);
		}
	}

	private void ProcessWitch(String msg) {
		if (msg.equals("selectKillTarget")) {
			System.out.print("Select a person to kill:\n");
			String person = scan.nextLine();
			SendMessage(person);
		} 
		else if (msg.startsWith("savePlayer;")) {
			System.out.print("Select a person to heal:\n");
			String[] playerNames = msg.replaceFirst("savePlayer;", "").split(";");
			for (String playerName : playerNames)
			{
				System.out.print(playerName + "\n");
			}
			String person = scan.nextLine();
			SendMessage(person);
		}
	}

	private void ProcessHunter(String msg) {
		if (msg.equals("selectTarget")) {
			System.out.print("Select a person to shoot:\n");
			String person = scan.nextLine();
			SendMessage(person);
		}
	}
	
	private void ProcessAmor(String msg)
	{
		if (msg.equals("selectLovers")) {
			System.out.print("Select first lover:\n");
			SendMessage(scan.nextLine());
			System.out.print("Select second lover:\n");
			SendMessage(scan.nextLine());
		}
	}
	
	private void ProcessSeer(String msg)
	{
		if (msg.equals("whoWantToSee")) {
			System.out.print("Select a person to check:\n");
			String person = scan.nextLine();
			SendMessage(person);
			System.out.print(WaitForMessage() + "\n");
		}
	}

	@Override
	public void run() {
		while (true) {
				String input = WaitForMessage();
				if (input == null) {
					Thread.yield();
					continue;
				}

				ProcessMessage(input);

		}
	}

	private void OnServerStop() {
		System.out.print("Server stopped!\n");
	}

	private void OnStartGame() {
		System.out.print("Game started!\n");
	}

	private void OnRoleAssign(Player.PlayerRole role) {
		this.role = role;
		System.out.print("You are: " + role + "\n");
	}
	
	private void OnRoundStart(int round)
	{
		System.out.print("Round: " + round + "\n");
	}
	
	private void OnGameEnd(String winners)
	{
		if (winners.equals("villager"))
		{
			System.out.print("Villagers won!\n");
		}
		else if (winners.equals("werewolf")) 
		{
			System.out.print("Werewolves won!\n");
		}
		else if (winners.equals("love"))
		{
			System.out.print("The lovers won!\n");
		}
		else 
		{
			System.out.print("I don't even know who won, this wasn't supposed to happen O_O\n");
		}
	}
	
	private void OnVote()
	{
		System.out.print("Select a person to execute:\n");
		String person = scan.nextLine();
		SendMessage(person);
	}
	
	private void OnTurn(String turn)
	{
		if (turn.equals("werewolves") && role != PlayerRole.werewolf)
		{
			System.out.print("Werewolves are selecting...\n");
		}
		if (turn.equals("amor") && role != PlayerRole.amor)
		{
			System.out.print("Amor is spreading love...\n");
		}
		if (turn.equals("seer") && role != PlayerRole.seer)
		{
			System.out.print("Seer is checking a card...\n");
		}
	}
	
	private void OnPlayerKilled(String name, String reason)
	{
		if (name.equals(playerName))
		{
			System.out.print(("You" + reason + "\n").replaceAll("was", "were"));
			return;
		}
		
		System.out.print(name + reason + "\n");
	}
	
	private void OnNightEnd()
	{
		System.out.print("The village wakes up\n");
	}
	
	private void OnLover(String name)
	{
		System.out.print("You're in love with " + name + "\n");
	}
	
	private void OnPlayerJoined(String name)
	{
		players.add(name);
	}
}

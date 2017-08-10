package w3r3w0lf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

import w3r3w0lf.Player.PlayerRole;

public class Client implements Runnable {
	Socket serverSocket;
	Player.PlayerRole role;
	String playerName;
	Scanner scan = new Scanner(System.in);

	public Boolean Connect(InetAddress host, int port, String name) {
		try {
			playerName = name;
			serverSocket = new Socket(host, port);
			new DataOutputStream(serverSocket.getOutputStream()).writeBytes(name + "\n");
			;
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private String WaitForMessage() throws IOException {
		return new BufferedReader(new InputStreamReader(serverSocket.getInputStream())).readLine();
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
		} else if (msg.equals("died")) {
			OnDied();
		} else if (msg.startsWith("round;")) {
			OnRoundStart(Integer.parseInt(msg.replaceFirst("round;", "")));
		} else if (msg.startsWith("win;")) {
			OnGameEnd(msg.replaceFirst("win;", ""));
		} else if (msg.equals("vote")) {
			OnVote();
		} else if (msg.startsWith("turn;")) {
			OnTurn(msg.replaceFirst("turn;", ""));
		} else if (msg.startsWith("killed;")) {
			OnPlayerKilled(msg.replaceFirst("killed;", ""));
		} else if (msg.equals("nightend")) {
			OnNightEnd();
		} else if (msg.startsWith("executed;")) {
			OnPlayerExecuted(msg.replaceFirst("executed;", ""));
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
		
		/*else 
		{
			System.out.print("Unknown message: " + msg + "\n");
		}*/
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
		} else if (msg.equals("selectHealTarget")) {
			System.out.print("Select a person to heal:\n");
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

	@Override
	public void run() {
		while (true) {
			try {
				String input = WaitForMessage();
				if (input == null) {
					Thread.yield();
					continue;
				}

				ProcessMessage(input);

			} catch (IOException e) {
				return;
			}

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

	private void OnDied() {
		System.out.print("You died!\n");
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
		else 
		{
			System.out.print("Werewolves won!\n");
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
		if (turn.equals("armor"))
		{
			System.out.print("Armor is spreading love...\n");
		}
	}
	
	private void OnPlayerKilled(String name)
	{
		if (name.equals(playerName))
		{
			return;
		}
		
		System.out.print(name + " was killed.\n");
	}
	
	private void OnNightEnd()
	{
		System.out.print("The village wakes up\n");
	}
	
	private void OnPlayerExecuted(String name)
	{
		
	}
}

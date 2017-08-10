package w3r3w0lf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable {
	Socket serverSocket;
	Player.PlayerRole role;

	public Boolean Connect(InetAddress host, int port, String name) {
		try {
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
		} else if (msg.equals("killed")) {
			OnKilled();
		}

		if (msg.startsWith("werewolf;") && role == Player.PlayerRole.werewolf) {
			ProcessWerewolf(msg.replaceFirst("werewolf;", ""));
		}
		if (msg.startsWith("witch;") && role == Player.PlayerRole.witch) {
			ProcessWitch(msg.replaceFirst("witch;", ""));
		}
		if (msg.startsWith("hunter;") && role == Player.PlayerRole.hunter) {
			ProcessHunter(msg.replaceFirst("hunter;", ""));
		}
	}

	private void ProcessWerewolf(String msg) {
		if (msg.equals("selectTarget")) {
			System.out.print("Select a person to kill:\n");
			Scanner scan = new Scanner(System.in);
			String person = scan.nextLine();
			scan.close();
			SendMessage(person);
		}
	}

	private void ProcessWitch(String msg) {
		if (msg.equals("selectKillTarget")) {
			System.out.print("Select a person to kill:\n");
			Scanner scan = new Scanner(System.in);
			String person = scan.nextLine();
			scan.close();
			SendMessage(person);
		} else if (msg.equals("selectHealTarget")) {
			System.out.print("Select a person to heal:\n");
			Scanner scan = new Scanner(System.in);
			String person = scan.nextLine();
			scan.close();
			SendMessage(person);
		}
	}

	private void ProcessHunter(String msg) {
		if (msg.equals("selectTarget")) {
			System.out.print("Select a person to kill:\n");
			Scanner scan = new Scanner(System.in);
			String person = scan.nextLine();
			SendMessage(person);
			scan.close();
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
		System.out.print("Server started!\n");
	}

	private void OnStartGame() {
		System.out.print("Game started!\n");
	}

	private void OnRoleAssign(Player.PlayerRole role) {
		this.role = role;
		System.out.print("You are: " + role + "\n");
	}

	private void OnKilled() {
		System.out.print("You were killed!\n");
	}
}

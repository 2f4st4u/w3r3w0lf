package w3r3w0lf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Player implements Runnable {
	// Steuerung + A, Steuerung + Shift + F = Einrücken
	boolean isAlive = true;
	Socket playerSocket;
	String playerName;
	PlayerRole role;
	GameManager manager;
	String vote;
	Player lover;

	public enum PlayerRole {
		none, villager, werewolf, witch, amor, girl, hunter, seer
	}
	
	public Player(Socket sock, String name, PlayerRole role, GameManager manager)
	{
		this.playerSocket = sock;
		this.playerName = name;
		this.manager = manager;
		this.role = role;
	}
	
	public void SendMessage(String msg) {
		try {
			new DataOutputStream(playerSocket.getOutputStream()).writeBytes(msg + "\n");
		} catch (IOException e) {
			return;
		}
	}
	public String GetMessage()
	{
		try {
			return new BufferedReader(new InputStreamReader(playerSocket.getInputStream())).readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
	public void TurnStart() {
		vote = null;
	}

	public void TurnEnd() {
		SendMessage("turnend");
	}
	
	public void Killed(String reason)
	{
		if (!this.isAlive)
		{
			return;
		}
		
		this.isAlive = false;
		manager.Broadcast("killed;" + this.playerName + ";" + reason);
	}
	
	public void Vote()
	{
		this.vote = null;
		
		if (!isAlive)
		{
			return;
		}
		
		SendMessage("vote");
		String response = GetMessage();
		if (!manager.PlayerExists(response))
		{
			return;
		}
		this.vote = response;
	}

	@Override
	public void run() {
		TurnStart();
		TurnEnd();
	}
	
}

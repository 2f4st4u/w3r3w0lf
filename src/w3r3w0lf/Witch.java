package w3r3w0lf;

import java.net.Socket;

public class Witch extends Player {

	public Witch(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}

	@Override
	public void TurnStart() {
		SendMessage("witch;WantToKill");
		String witchKillResponse = GetMessage();
		if (witchKillResponse.equals("yes")) {
			SendMessage("witch;selectKillTarget");
			String response = GetMessage();
			if (!manager.PlayerExists(response)) {
				return;
			}
		}
		SendMessage("witch;WantToHeal");
		String witchHealResponse = GetMessage();
		if (witchHealResponse.equals("yes")) {
			SendMessage("witch;selectHealTarget");
			String response = GetMessage();
			if (!manager.PlayerExists(response)) {
				return;
			}
		}
	}
}
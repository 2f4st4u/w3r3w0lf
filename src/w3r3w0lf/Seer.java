package w3r3w0lf;

import java.net.Socket;

public class Seer extends Player {

	public Seer(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}

	@Override
	public void TurnStart() {
		SendMessage("seer;whoWantToSee");
		String SeerWhoWantToSee = GetMessage();
		if (!manager.PlayerExists(SeerWhoWantToSee)) {
			return;
		}
		if (manager.GetPlayerByName(SeerWhoWantToSee).role.name().equals("werewolf")) {
			SendMessage(SeerWhoWantToSee + " is on the dark side.");
		} else if (!manager.GetPlayerByName(SeerWhoWantToSee).role.name().equals("werewolf")) {
			SendMessage(SeerWhoWantToSee + " is on the light side.");
		}
	}
}
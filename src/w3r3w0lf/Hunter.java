package w3r3w0lf;

import java.net.Socket;

public class Hunter extends Player {

	public Hunter(Socket sock, String name, PlayerRole role, GameManager manager) {
		super(sock, name, role, manager);
	}

	@Override
	public void TurnStart() {
		SendMessage("Hunter;selectTarget");
		String response = GetMessage();
		if (!manager.PlayerExists(response)) {
			return;
		}

	}

	@Override
	public void Killed()
	{
		super.Killed();
		SendMessage("Hunter;selectTarget");
	}
}
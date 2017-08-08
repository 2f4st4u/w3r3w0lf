package w3r3w0lf;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Client {
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
}

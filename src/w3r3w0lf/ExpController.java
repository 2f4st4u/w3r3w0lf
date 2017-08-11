package w3r3w0lf;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExpController  {

    @FXML
    private TextField tbox_playername;

    @FXML
    private Button btn_joinGame;

    @FXML
    private TextField tbox_ipadress;

    @FXML
    private TextField tbox_hostname;

    @FXML
    private Button btn_createGame;

    @FXML
    void mhostgame(ActionEvent event) {
    			Stage stage = new Stage();
    		    FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\resources\\gui_lobby_CreateGame.fxml"));
    		    Scene crga = null;
				try {
					crga = new Scene(loader.load());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ExpControllerCreateGame d = new ExpControllerCreateGame();
    			stage.setScene(crga);
    			stage.show();
    			
    			Stage stage1= (Stage) btn_joinGame.getScene().getWindow();
    			stage1.close();	
    			
    			LobbyManager lobbyManager = new LobbyManager();
    			try {
					lobbyManager.Initialize(1337);
				} catch (IOException e) {
					return;
				}
    			
    			
    		}                 
    	
    
    @FXML
    void mjoingame(ActionEvent event) {
    
		Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\resources\\gui_lobby_CreateGame.fxml"));
	    Scene jcrga = null;
		try {
			jcrga = new Scene(loader.load());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExpControllerCreateGame d = new ExpControllerCreateGame();
		stage.setScene(jcrga);
		stage.show();
		
		Stage stage1= (Stage) btn_joinGame.getScene().getWindow();
		stage1.close();
		
		Client player = new Client();
		try {
			player.Connect(InetAddress.getByName(tbox_ipadress.textProperty().get()), 1337,tbox_playername.textProperty().get() );
			player.run();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    }

}

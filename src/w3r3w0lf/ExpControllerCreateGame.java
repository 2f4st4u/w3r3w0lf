package w3r3w0lf;

import java.io.IOException;
import java.net.InetAddress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExpControllerCreateGame {

	    @FXML
	    private ListView<?> list_playername_lobby;

	    @FXML
	    private Label label_playercount;

	    @FXML
	    private TextField tbox_gamename;

	    @FXML
	    private CheckBox ckbox_hunter;

	    @FXML
	    private Button btn_start;

	    @FXML
	    private Button btn_disconnect;

	    @FXML
	    private CheckBox ckbox_witch;

	    @FXML
	    private CheckBox ckbox_armor;

	    @FXML
	    private TextField tbox_citizencount;

	    @FXML
	    private TextField tbox_werewolfescount;

	    @FXML
	    private TextField tbox_playercount;

	    @FXML
	    private CheckBox ckbox_seer;

	    @FXML
	    private Label label_hostIP;

	    @FXML
	    void mbackmenue(ActionEvent event) {

	    	Stage stage1= (Stage) btn_start.getScene().getWindow();
			stage1.close();
	    	
	    }

	    @FXML
	    void mgamestart(ActionEvent event) {

	    	Stage stage = new Stage();
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\resources\\gui_ingame.fxml"));
		    Scene scrga = null;
			try {
				scrga = new Scene(loader.load());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ExpControllerIngame e = new ExpControllerIngame();
			stage.setScene(scrga);
			stage.show();
			
			Stage stage1= (Stage) btn_start.getScene().getWindow();
			stage1.close();
			

	    	
	    }

	}

	


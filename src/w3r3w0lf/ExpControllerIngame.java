package w3r3w0lf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class ExpControllerIngame {

	ObservableList<Button> data;
	    @FXML
	    private ImageView img_background;

	    @FXML
	    private ListView<String> list_log;

	    @FXML
	    private ListView<Button> list_players;

	    @FXML
	    private ImageView img_playercard;

	    @FXML
	    private Button btn_accept;

	    @FXML
	    private Label label_header;
	    
	    
	    public ExpControllerIngame() {
			data = FXCollections.observableArrayList();
		}

		@FXML
		void btn_ingame_accept(ActionEvent event) {
		
		}

		@FXML
		void list_players(ActionEvent event) {
			
		}
		public void handleMouseClick(){
			Button b = new Button();
			data.add(b);
			b.setText("Debug Button 1");
			list_players.setItems(data);
			
		}
	}

	

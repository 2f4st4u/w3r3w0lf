//package w3r3w0lf;
//
//import java.io.File;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//public class IngameController {
//    @FXML
//    private ImageView img_background;
//
//	@FXML
//	private ListView<String> list_log;
//
//	@FXML
//	private ListView<String> list_players;
//
//	@FXML
//	private ImageView img_playercard;
//
//	@FXML
//	private Button btn_accept;
//
//	@FXML
//	private Label label_header;
//
//	public IngameController() {
//		
//	}
//	
//	@FXML
//	public void a(){
//		File file = new File("C:\\Users\\lukamuenz17\\Downloads\\w3r3w0lf\\src\\resources\\Village%20by%20Night[1].jpg");
//		System.out.println(file.getAbsolutePath());
//		Image img = new Image("file://C:\\Users\\lukamuenz17\\Downloads\\w3r3w0lf\\src\\resources\\Village%20by%20Night[1].jpg");
//		System.out.println(img == null);
//		System.out.println(img_background == null);
//		img_background.setImage(img);
//		
//	}
//}
package w3r3w0lf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class IngameController {
	
	
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
	
	public IngameController() {
		data = FXCollections.observableArrayList(b);
	}

	@FXML
	void btn_ingame_accept(ActionEvent event) {
	
	}

	@FXML
	void list_players(ActionEvent event) {
		
	}
	
	@FXML
	public void handleMouseClick(){
		Button b = new Button();
		data.add(b);
		b.setText("Debug Button 1");
		list_players.setItems(data);
		
	}

}
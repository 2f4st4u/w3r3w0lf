package w3r3w0lf;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class IngameController {

    @FXML
    private ImageView img_background;

    @FXML
    private ListView<String	> list_log;

    @FXML
    private ListView<String> list_players;

    @FXML
    private ImageView img_playercard;

    @FXML
    private Button btn_accept;

@FXML
private Label label_header;
    
    public IngameController() {
    }
}

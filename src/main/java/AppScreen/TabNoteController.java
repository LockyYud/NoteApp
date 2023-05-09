package AppScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TabNoteController implements Initializable {
    @FXML
    public static TabPane tabNote = new TabPane();
    @FXML
    private TextArea titleEvent;
    @FXML
    private TextArea contentEvent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public TextArea getTitleEvent() {
        return titleEvent;
    }

    public void setTitleEvent(TextArea titleEvent) {
        this.titleEvent = titleEvent;
    }

    public TextArea getContentEvent() {
        return contentEvent;
    }

    public void setContentEvent(TextArea contentEvent) {
        this.contentEvent = contentEvent;
    }
}

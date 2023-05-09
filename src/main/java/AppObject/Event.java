package AppObject;

import javafx.scene.control.Button;

public class Event {
    private ContentEvent contentEvent;
    private Button buttonEvent = new Button();
    public Event(ContentEvent contentEvent_) {
        contentEvent = contentEvent_;
        buttonEvent.setText(contentEvent.getEvent_title());
        buttonEvent.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
    }

    public Button getButton() {
        return buttonEvent;
    }
}

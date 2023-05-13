package AppObject;

import AppScreen.EventContentController;
import AppScreen.Main;
import Manage.ManageEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.time.ZonedDateTime;

public class Event {
    private static ToggleGroup buttonEvent = new ToggleGroup();
    public ContentEvent getContentEvent() {
        return contentEvent;
    }

    private ContentEvent contentEvent;
    private ToggleButton button = new ToggleButton();
    public Event(ContentEvent contentEvent_) {
        contentEvent = contentEvent_;
        button.setPrefSize(270,30);
        button.setMinSize(270,30);
        button.setText(contentEvent.getTitle());
        button.setToggleGroup(buttonEvent);
        button.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
        button.setOnMouseClicked(Event -> {
            EventContentController.assignEvent(this);
            if(!Main.calendarView.getChildren().contains(Main.detailEvent)){
                Main.calendarView.getChildren().add(Main.detailEvent);
            }
            ManageEvent.idEventSelected = this.getId();
        });
    }
    public int getId() {
        return contentEvent.getId();
    }
    public ToggleButton getButton() {
        return button;
    }

    public String getTitle() {
        return contentEvent.getTitle();
    }
    public String getBody() {
        return contentEvent.getBody();
    }
    public ZonedDateTime getUpdated_at() {
        return contentEvent.getUpdated_at();
    }
    public ZonedDateTime getCreated_at() {
        return contentEvent.getCreated_at();
    }
    public ZonedDateTime getEndTime() {
        return contentEvent.getEnd_time();
    }
    public ZonedDateTime getStartTime() {
        return contentEvent.getStart_time();
    }
    public void save(String titleEvent, String bodyEvent, ZonedDateTime timeStart, ZonedDateTime timeEnd) {
        contentEvent.setBody(bodyEvent);
        contentEvent.setTitle(titleEvent);
        contentEvent.setEnd_time(timeEnd);
        contentEvent.setStart_time(timeStart);
        ManageEvent.writeDataofEventtoFile(contentEvent);
    }
}

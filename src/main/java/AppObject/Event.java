package AppObject;

import AppScreen.EventContentController;
import AppScreen.Main;
import Manage.ManageEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.time.ZonedDateTime;

public class Event {
    private static ToggleGroup buttonEvent = new ToggleGroup();
    public ContentEvent getContentEvent() {
        return contentEvent;
    }
    private ContentEvent contentEvent;
    private ToggleButton button = new ToggleButton();
    private ToggleButton buttonImportant = new ToggleButton();
    private ToggleButton buttonWeek = new ToggleButton();
    private ToggleButton buttonDay = new ToggleButton();
    public Event(ContentEvent contentEvent_) {
        contentEvent = contentEvent_;
        button.setPrefSize(270,30);
        button.setMinSize(270,30);
        button.setMaxSize(270,30);
        button.setText(contentEvent.getTitle());
        button.setToggleGroup(buttonEvent);
        button.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
        button.setOnMouseClicked(Event -> {
            if(Event.getButton().equals(MouseButton.PRIMARY)){
                ManageEvent.idEventSelected = this.getId();
                if(button.isSelected()){
                    EventContentController.assignEvent(this);
                    if (!Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().add(Main.detailEvent);
                    }
                } else {
                    if (Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().remove(Main.detailEvent);
                    }
                }
            }
        });

        buttonDay.setText(button.getText());
        buttonDay.setOnMouseClicked(Event -> {
            if(Event.getButton().equals(MouseButton.PRIMARY)){
                ManageEvent.idEventSelected = this.getId();
                if(buttonDay.isSelected()){
                    EventContentController.assignEvent(this);
                    if (!Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().add(Main.detailEvent);
                    }
                } else {
                    if (Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().remove(Main.detailEvent);
                    }
                }
            }
        });
        buttonDay.setPrefSize(270, button.getPrefHeight());
        buttonDay.setMinSize(270, button.getMinHeight());
        buttonDay.setMaxSize(270, button.getMinHeight());
        buttonDay.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
        buttonDay.setToggleGroup(buttonEvent);

        buttonImportant.setText(button.getText());
        buttonImportant.setOnMouseClicked(Event -> {
            if(Event.getButton().equals(MouseButton.PRIMARY)){
                ManageEvent.idEventSelected = this.getId();
                if(buttonImportant.isSelected()){
                    EventContentController.assignEvent(this);
                    if (!Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().add(Main.detailEvent);
                    }
                } else {
                    if (Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().remove(Main.detailEvent);
                    }
                }
            }
        });
        buttonImportant.setPrefSize(270, button.getPrefHeight());
        buttonImportant.setMinSize(270, button.getMinHeight());
        buttonImportant.setMaxSize(270, button.getMinHeight());
        buttonImportant.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
        buttonImportant.setToggleGroup(buttonEvent);

        buttonWeek.setText(button.getText());
        buttonWeek.setOnMouseClicked(Event -> {
            if(Event.getButton().equals(MouseButton.PRIMARY)){
                ManageEvent.idEventSelected = this.getId();
                if(buttonWeek.isSelected()){
                    EventContentController.assignEvent(this);
                    if (!Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().add(Main.detailEvent);
                    }
                } else {
                    if (Main.calendarView.getChildren().contains(Main.detailEvent)) {
                        Main.calendarView.getChildren().remove(Main.detailEvent);
                    }
                }
            }
        });
        buttonWeek.setPrefSize(270, button.getPrefHeight());
        buttonWeek.setMinSize(270, button.getMinHeight());
        buttonWeek.setMaxSize(270, button.getMinHeight());
        buttonWeek.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
        buttonWeek.setToggleGroup(buttonEvent);

        if(contentEvent.isImportant()) {
            setGraphic(Note.class.getResource("/AppObject/Icon/importantoff.png").toExternalForm());
        }
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
    public void save(String titleEvent, String bodyEvent, String location, ZonedDateTime timeStart, ZonedDateTime timeEnd) {
        contentEvent.setBody(bodyEvent);
        contentEvent.setTitle(titleEvent);
        contentEvent.setEnd_time(timeEnd);
        contentEvent.setStart_time(timeStart);
        contentEvent.setLocation(location);
        ManageEvent.writeDataofEventtoFile(contentEvent);
    }

    public ToggleButton getButtonImportant() {
        return buttonImportant;
    }

    public ToggleButton getButtonWeek() {
        return buttonWeek;
    }

    public ToggleButton getButtonDay() {
        return buttonDay;
    }
    public void setGraphic(String url) {
        button.setGraphic(new ImageView(new Image(url, 20,20,true,false)));
        buttonWeek.setGraphic(new ImageView(new Image(url, 20,20,true,false)));
        buttonImportant.setGraphic(new ImageView(new Image(url, 20,20,true,false)));
        buttonDay.setGraphic(new ImageView(new Image(url, 20,20,true,false)));
    }

    public void setText(String text) {
        button.setText(text);
        buttonWeek.setText(text);
        buttonImportant.setText(text);
        buttonDay.setText(text);
    }
}

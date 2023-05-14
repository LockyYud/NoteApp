package AppScreen;

import AppObject.Event;
import Manage.ManageEvent;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    @FXML
    public AnchorPane pageCalendar;

    private static DatePicker calendar = new DatePicker();
    private static DatePickerSkin calendarView;
    private static List<LocalDate> dayhaveEvent = new ArrayList<>();

    @FXML
    private VBox listImportantEvent;
    @FXML
    private VBox listWeekEvent;
    @FXML
    private VBox listTodayEvent;
    @FXML
    private HBox _listlist;
    @FXML
    private Label empty1;

    @FXML
    private Label empty2;

    @FXML
    private Label empty3;
    private static HBox listlist;

    private LocalDate today;

    public static VBox _listImportantEvent;
    private static TranslateTransition listup = new TranslateTransition();
    private static TranslateTransition calendardown = new TranslateTransition();

    private static VBox _listWeekEvent;
    private static VBox _listTodayEvent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listlist = _listlist;
        listup.setNode(listlist);
        listup.setDuration(Duration.seconds(0.5));
        listup.setFromY(300);
        listup.setToY(0);
        today = LocalDate.now();

        for(Event e : ManageEvent.getListRoot()) {
            dayhaveEvent.add(e.getStartTime().toLocalDate());
        }
        calendar.setPrefSize(600,600);
        calendar.getStylesheets().add(this.getClass().getResource("calendar.css").toExternalForm());
        calendar.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if(dayhaveEvent.contains(item)) {
                                this.setBackground(Background.fill(Color.YELLOW));
                            }
                        }
                        this.setOnMouseClicked(e -> {
                            if(e.getButton().equals(MouseButton.PRIMARY)) {
                                ListObjectController.uploadListEvent(ManageEvent.filterbyDay(calendar.getValue()));
                            }
                        });
                    }
                };
            }
        });
        calendarView = new DatePickerSkin(calendar);
        calendardown.setNode(calendarView.getPopupContent());
        calendardown.setDuration(Duration.seconds(0.5));
        calendardown.setFromY(-300);
        calendardown.setToY(0);

        Text text = new Text();

        pageCalendar.getChildren().add(calendarView.getPopupContent());
        AnchorPane.setLeftAnchor(calendarView.getPopupContent(),0.0);
        AnchorPane.setRightAnchor(calendarView.getPopupContent(),0.0);
//        AnchorPane.setBottomAnchor(calendarView.getPopupContent(),0.0);
        AnchorPane.setTopAnchor(calendarView.getPopupContent(),50.0);

        pushDayEvent();
        pushImportantEvent();
        pushWeekEvent();
        _listImportantEvent = listImportantEvent;
        _listWeekEvent = listWeekEvent;
        _listTodayEvent = listTodayEvent;


        AnimationTimer empty = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!listTodayEvent.getChildren().isEmpty()) {
                    empty3.setOpacity(0);
                }
                if(!listWeekEvent.getChildren().isEmpty()) {
                    empty2.setOpacity(0);
                }
                if (!listImportantEvent.getChildren().isEmpty()) {
                    empty1.setOpacity(0);
                }
            }
        };
        empty.start();
    }

    private void pushImportantEvent() {
        for(Event e : ManageEvent.getListRoot()) {
            if(e.getContentEvent().isImportant()) {
                listImportantEvent.getChildren().add(e.getButtonImportant());
            }
        }
    }
    private void pushDayEvent() {
        for(Event e : ManageEvent.getListRoot()) {
            if(e.getContentEvent().getStart_time().toLocalDate().equals(today)) {
                listTodayEvent.getChildren().add(e.getButtonDay());
            }
        }
    }
    private void pushWeekEvent() {
        for(Event e : ManageEvent.getListRoot()) {
            if(e.getContentEvent().getStart_time().toLocalDate().getYear() == today.getYear()
                    && today.getDayOfYear() - today.getDayOfWeek().getValue() < e.getContentEvent().getStart_time().toLocalDate().getDayOfYear()
                    && today.getDayOfYear() - today.getDayOfWeek().getValue() + 8 > e.getContentEvent().getStart_time().toLocalDate().getDayOfYear()) {
                listWeekEvent.getChildren().add(e.getButtonWeek());
            }
        }
    }

    public static void listUp() {
        calendardown.play();
        listup.play();
    }

    public static void deleteButton(ToggleButton day, ToggleButton week, ToggleButton important) {
        if (_listWeekEvent.getChildren().contains(week)) {
            _listWeekEvent.getChildren().remove(week);
        }
        if(_listTodayEvent.getChildren().contains(day)) {
            _listTodayEvent.getChildren().remove(day);
        }
        if(_listImportantEvent.getChildren().contains(important)) {
            _listImportantEvent.getChildren().remove(important);
        }
    }

    public static LocalDate getDaySelected() {
        return calendar.getValue();
    }
}

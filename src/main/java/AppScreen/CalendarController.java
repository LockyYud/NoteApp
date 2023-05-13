package AppScreen;

import AppObject.Event;
import Manage.ManageEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    @FXML
    public AnchorPane pageCalendar;

    private static DatePicker calendar = new DatePicker();
    private static DatePickerSkin calendarView;
    private static List<LocalDate> dayhaveEvent = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        pageCalendar.getChildren().add(calendarView.getPopupContent());
        AnchorPane.setLeftAnchor(calendarView.getPopupContent(),0.0);
        AnchorPane.setRightAnchor(calendarView.getPopupContent(),0.0);
//        AnchorPane.setBottomAnchor(calendarView.getPopupContent(),0.0);
        AnchorPane.setTopAnchor(calendarView.getPopupContent(),50.0);
    }
}

package AppScreen;

import AppObject.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    private ZonedDateTime today;
    private ZonedDateTime dateFocus;
    private List<Label> days = new ArrayList<>();
    private GridPane calendarView = new GridPane();
    public static AnchorPane detailEvent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        calendarView.getChildren().removeAll();
        hehe.getChildren().add(calendarView);
        today = ZonedDateTime.now();
        dateFocus = ZonedDateTime.now();
        year.setText(Integer.toString(today.getYear()));
        month.setText(today.getMonth().toString());
        for(int i = 0; i < 42; i++) {
            Label label = new Label();
            label.setPrefWidth(80);
            label.setPrefHeight(50);
            label.setMinHeight(50);
            label.setStyle("-fx-background-color: #a9f382");
            label.setStyle("-fx-font-weight: bold");
            label.setAlignment(Pos.CENTER);
            label.setOnMouseClicked(Event -> {
                if(!Main.calendarView.getChildren().contains(Main.detailEvent)){
                    Main.calendarView.getChildren().add(Main.detailEvent);
                }
            });
            days.add(label);
            calendarView.add(label,i%7,(int) (i/7));
        }
        setCalendarView();
    }
    private void setCalendarView() {
        for(int i = 0; i < 42; i++) {
            days.get(i).setStyle("-fx-background-color: #ffffff");
            days.get(i).setStyle("-fx-font-weight: normal");
        }
        int daymonth = dateFocus.getDayOfMonth();
        int dayweek = dateFocus.getDayOfWeek().getValue();
        int indexofToday = ((Integer)(daymonth - dayweek)/7 + 1) * 7 + dayweek;
        System.out.println(dateFocus.getDayOfYear());
        System.out.println(today.getDayOfYear());
        for(int i = dateFocus.getMonth().length(false); i >= 1;i--) {
            days.get(indexofToday - daymonth + i).setText(Integer.toString(i));
            days.get(indexofToday - daymonth + i).setStyle("-fx-font-weight: bold");
        }
        if(dateFocus.getDayOfYear() - today.getDayOfYear() == 0) {
            System.out.println("hehe");
            days.get(indexofToday).setStyle("-fx-background-color: #a9f382;" +
                    "-fx-font-weight: bold");
        }
        for(int i = indexofToday - daymonth + dateFocus.getMonth().length(false) + 1; i < 42; i++) {
            days.get(i).setText(Integer.toString(i - (indexofToday - daymonth + dateFocus.getMonth().length(false))));
        }
        int lastDayoflastMonth = dateFocus.getMonth().minus(1).length(false);
        for(int i = 0; i <= indexofToday - daymonth; i++) {
            days.get(indexofToday - daymonth - i).setText(Integer.toString(lastDayoflastMonth - i));
        }
    }
    @FXML
    private HBox hehe;
    @FXML
    private Text year;
    @FXML
    private Text month;
    @FXML
    private Button monthAfter;
    @FXML
    private Button monthPrevious;
    @FXML
    private void clickMonthAfter() {
        dateFocus = dateFocus.plusMonths(1);
        month.setText(dateFocus.getMonth().toString());
        year.setText(Integer.toString(dateFocus.getYear()));
        setCalendarView();
    }
    @FXML
    private void clickMonthPrevious() {
        dateFocus = dateFocus.plusMonths(-1);
        month.setText(dateFocus.getMonth().toString());
        year.setText(Integer.toString(dateFocus.getYear()));
        setCalendarView();
    }
}

package AppScreen;

import AppObject.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


public class EventContentController implements Initializable {
    @FXML
    private StackPane save;
    @FXML
    private ImageView notSaved;
    @FXML
    private AnchorPane page;
    @FXML
    private ImageView haveSaved;
    @FXML
    private TextField hourFrom;
    @FXML
    private TextField hourTo;
    @FXML
    private TextField minuteFrom;
    @FXML
    private TextField minuteTo;
    @FXML
    private TextField title;
    @FXML
    private TextArea body;
    @FXML
    private DatePicker datePicker;
    @FXML
    private StackPane deleteBut;
    @FXML
    private StackPane importantBut;
    @FXML
    private Text created;
    @FXML
    private Text updated;
    private static DatePicker dateHappenEvent;
    private static TextField titleEvent;
    private static TextArea bodyEvent;
    public static Event event;
    public static TextField hFrom;
    public static TextField hTo;
    public static TextField mFrom;
    public static TextField mTo;
    private static LocalDateTime timeStart;
    private static LocalDateTime timeEnd;
    private static ZoneId zoneId;
    private static Text create;
    private static Text update;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hFrom = hourFrom;
        hTo = hourTo;
        mFrom = minuteFrom;
        mTo = minuteTo;
        titleEvent = title;
        bodyEvent = body;
        dateHappenEvent = datePicker;
        create = created;
        update = updated;
        title.setOnKeyReleased(Event -> {
            event.getButton().setText(title.getText());
            haveSaved.setOpacity(0);
        });
        body.setOnKeyReleased(Event -> {
            haveSaved.setOpacity(0);
        });
        datePicker.setOnMouseClicked(e -> {
            if(event.getStartTime().toLocalDateTime().getDayOfYear() != datePicker.getValue().getDayOfYear()){
                haveSaved.setOpacity(0);
            }
        });
        hourTo.setOnKeyReleased(e -> {
            haveSaved.setOpacity(0);
        });
        hourFrom.setOnKeyReleased(e -> {
            haveSaved.setOpacity(0);
        });
        minuteTo.setOnKeyReleased(e -> {
            haveSaved.setOpacity(0);
        });
        minuteFrom.setOnKeyReleased(e -> {
            haveSaved.setOpacity(0);
        });


        UnaryOperator<TextFormatter.Change> hourFilter = change -> {
            String text = change.getControlNewText();
            String Demo_Text = change.getControlNewText();
            if (Demo_Text.matches("-?([2][0-4]*)?")) {
                return change;
            }
            if (Demo_Text.matches("-?([1][0-9]*)?")) {
                return change;
            }
            if (Demo_Text.matches("-?([0][0-9]*)?")) {
                return change;
            }
            return null;
        };
        UnaryOperator<TextFormatter.Change> minuteFilter = change -> {
            String text = change.getControlNewText();
            String Demo_Text = change.getControlNewText();
            if (Demo_Text.matches("-?([0-5][0-9]*)?")) {
                return change;
            }
            if (Demo_Text.matches("-?([6][0]*)?")) {
                return change;
            }
            return null;
        };

        hourFrom.setTextFormatter(new TextFormatter<>(hourFilter));
        hourTo.setTextFormatter(new TextFormatter<>(hourFilter));
        minuteFrom.setTextFormatter(new TextFormatter<>(minuteFilter));
        minuteTo.setTextFormatter(new TextFormatter<>(minuteFilter));
        addTextLimiter(minuteFrom,2);
        addTextLimiter(minuteTo,2);
        addTextLimiter(hourFrom,2);
        addTextLimiter(hourTo,2);

        deleteBut.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)) {
                MainController.openBoxDelete();
            }
        });

//        page.setOnMouse(e -> {
//            if(e.isPrimaryButtonDown()) {
//                close();
//            }
//        });
    }

    private void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
    @FXML
    private void close() {
        Main.calendarView.getChildren().remove(Main.detailEvent);
    }
    @FXML
    private void saveEvent() {

        try {
            timeEnd = LocalDateTime.of(datePicker.getValue()
                    , LocalTime.of(Integer.parseInt(hourTo.getText()),Integer.parseInt(minuteTo.getText())));
            timeStart = LocalDateTime.of(datePicker.getValue()
                    , LocalTime.of(Integer.parseInt(hourFrom.getText()),Integer.parseInt(minuteFrom.getText())));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        event.save(titleEvent.getText(), bodyEvent.getText(), timeStart.atZone(zoneId), timeEnd.atZone(zoneId));
        haveSaved.setOpacity(1);
    }
    public static void assignEvent(Event e) {
        event = e;
        timeEnd = e.getEndTime().toLocalDateTime();
        timeStart = e.getStartTime().toLocalDateTime();
        zoneId = e.getStartTime().getZone();
        titleEvent.setText(e.getTitle());
        bodyEvent.setText(e.getBody());
        hFrom.setText(Integer.toString(timeStart.getHour()));
        hTo.setText(Integer.toString(timeEnd.getHour()));
        mFrom.setText(Integer.toString(timeStart.getMinute()));
        mTo.setText(Integer.toString(timeEnd.getMinute()));
        dateHappenEvent.setValue(timeStart.toLocalDate());
        update.setText(e.getUpdated_at().toLocalDate().toString());
        create.setText(e.getCreated_at().toLocalDate().toString());
    }
}

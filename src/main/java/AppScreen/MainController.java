package AppScreen;

import AppObject.ContentEvent;
import AppObject.ContentNote;
import AppObject.Event;
import AppObject.Note;
import Manage.ManageEvent;
import Manage.ManageNote;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane mainPage;
    @FXML
    private AnchorPane sectionContentView;
    @FXML
    private Button openList;
    public static Button _openList;
    public static AnchorPane contentView;
    public static Stage definitely_delete = new Stage();


    public static TranslateTransition close = new TranslateTransition();
    private TranslateTransition open = new TranslateTransition();

    public static Transition closeContentView = new Transition() {
        {
            setCycleDuration(Duration.seconds(0.45));
        }
        @Override
        protected void interpolate(double frac) {
            contentView.setPrefWidth(290 * frac + 930);
        }
    };
    public static Transition openContentView = new Transition() {
        {
            setCycleDuration(Duration.seconds(0.45));
        }
        @Override
        protected void interpolate(double frac) {
            contentView.setPrefWidth(-290 * frac + 1220);
        }
    };
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane.setBottomAnchor(Main.listView,0.0);
        AnchorPane.setTopAnchor(Main.listView,0.0);
        AnchorPane.setLeftAnchor(Main.listView,60.0);

        AnchorPane.setBottomAnchor(Main.noteView,0.0);
        AnchorPane.setTopAnchor(Main.noteView,0.0);
        AnchorPane.setRightAnchor(Main.noteView,0.0);
        AnchorPane.setLeftAnchor(Main.noteView,0.0);

        AnchorPane.setBottomAnchor(Main.calendarView,0.0);
        AnchorPane.setTopAnchor(Main.calendarView,0.0);
        AnchorPane.setLeftAnchor(Main.calendarView,0.0);
        AnchorPane.setRightAnchor(Main.calendarView,0.0);


//        AnchorPane.setBottomAnchor(Main.detailEvent,0.0);
        AnchorPane.setTopAnchor(Main.detailEvent,50.0);
        AnchorPane.setLeftAnchor(Main.detailEvent,50.0);
//        AnchorPane.setRightAnchor(Main.detailEvent,0.0);


        mainPage.getChildren().add(1,Main.listView);
        mainPage.getChildren().set(0,sectionContentView);
        FadeTransition appear = new FadeTransition(Duration.seconds(0.5),openList);

        _openList = openList;
        contentView = sectionContentView;

        close.setNode(sectionContentView);
        close.setDuration(Duration.seconds(0.45));
        close.setByX(-290);
        open.setNode(sectionContentView);
        open.setDuration(Duration.seconds(0.45));
        open.setByX(290);

        HBox box = new HBox();
        definitely_delete.setScene(new Scene(box,100,50));
        Button yes = new Button("Yes");
        Button no = new Button("No");
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(yes,no);
        definitely_delete.setResizable(false);
        definitely_delete.setAlwaysOnTop(true);
        no.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)) {
                definitely_delete.close();
            }
        });
        yes.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)) {
                if(sectionContentView.getChildren().contains(Main.noteView)){
                    ManageNote.deleteNote();
                    definitely_delete.close();
                } else {
                    if(sectionContentView.getChildren().contains(Main.calendarView)) {
                        ManageEvent.deleteEvent();
                        definitely_delete.close();
                    }
                }
            }
        });
    }
    @FXML public void viewCalendar() {
        if(!sectionContentView.getChildren().contains(Main.calendarView)) {
            if(sectionContentView.getChildren().contains(Main.noteView)) {
                sectionContentView.getChildren().remove(Main.noteView);
            }
            sectionContentView.getChildren().add(Main.calendarView);
        }
        ListObjectController.setListEvent();
    }
    @FXML
    public void viewNote() {
        if(!sectionContentView.getChildren().contains(Main.noteView)) {
            if(sectionContentView.getChildren().contains(Main.calendarView)) {
                sectionContentView.getChildren().remove(Main.calendarView);
            }
            sectionContentView.getChildren().add(Main.noteView);
        }
        ListObjectController.setListNote();
    }
    @FXML
    private void setOpenList() {
        ListObjectController.setOpenList();
        open.play();
        openContentView.play();
        openList.setOpacity(0);
    }

    public static void openBoxDelete() {
        definitely_delete.show();
    }
}
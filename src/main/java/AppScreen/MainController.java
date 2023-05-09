package AppScreen;

import AppObject.ContentEvent;
import AppObject.ContentNote;
import AppObject.Event;
import AppObject.Note;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

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

    public static List<ContentNote> contentNoteList = new ArrayList<>();
    public static List<ContentEvent> contentEventList = new ArrayList<>();

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



        AnchorPane.setLeftAnchor(sectionContentView,370.0);

        mainPage.getChildren().add(1,Main.listView);
        mainPage.getChildren().set(0,sectionContentView);
        _openList = openList;
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
        openList.setOpacity(0);
    }
}
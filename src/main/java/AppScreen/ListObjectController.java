package AppScreen;

import AppObject.Event;
import AppObject.Note;
import ManageObject.ManageEvent;
import ManageObject.ManageNote;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ListObjectController implements Initializable {
    @FXML
    public AnchorPane listObject;
    @FXML
    private AnchorPane listView;
    @FXML
    private ComboBox<String> sort;
    public static AnchorPane list;
    public static TilePane listNote = new TilePane();
    public static TilePane listEvent = new TilePane();

    public static Button openList;

    public static boolean isOpening = true;
//    Di chuyen
    TranslateTransition close = new TranslateTransition();
    public static TranslateTransition open = new TranslateTransition();
    FadeTransition fade = new FadeTransition();
    public static FadeTransition emerge = new FadeTransition();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(final Note var : ManageNote.noteList) {
            listNote.getChildren().add(var.getButton());
        }
        for(final Event var : ManageEvent.eventList) {
            listEvent.getChildren().add(var.getButton());
        }
        AnchorPane.setTopAnchor(listEvent,0.0);
        AnchorPane.setLeftAnchor(listEvent,0.0);
        AnchorPane.setRightAnchor(listEvent,0.0);
        AnchorPane.setBottomAnchor(listEvent,0.0);
        AnchorPane.setTopAnchor(listNote,0.0);
        AnchorPane.setBottomAnchor(listNote,0.0);
        AnchorPane.setLeftAnchor(listNote,0.0);
        AnchorPane.setRightAnchor(listNote,0.0);
        listNote.setAlignment(Pos.TOP_CENTER);
        listNote.setOrientation(Orientation.VERTICAL);
        listNote.setVgap(10);
        listEvent.setAlignment(Pos.TOP_CENTER);
        listEvent.setOrientation(Orientation.VERTICAL);
        listEvent.setVgap(10);

        list = listObject;

        fade.setNode(listView);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setDuration(Duration.seconds(0.75));
        emerge.setNode(listView);
        emerge.setFromValue(0);
        emerge.setToValue(1);
        emerge.setDuration(Duration.seconds(0.75));
        close.setNode(listView);
        close.setToX(-290);
        close.setDuration(Duration.seconds(0.75));
        open.setNode(listView);
        open.setToX(0);
        open.setDuration(Duration.seconds(0.75));


        //sort comboBox
        ObservableList<String> listSort = FXCollections.observableArrayList("name","last update","created","default");

        sort.setItems(listSort);
        sort.setOnAction(Event -> {
                for(int i = listNote.getChildren().size() - 1; i >= 0;i-- ) {
                    listNote.getChildren().remove(i);
                }
                for(final Note var : ManageNote.sortList(sort.getSelectionModel().getSelectedIndex())) {
                    listNote.getChildren().add(var.getButton());
                }
        });

    }
    @FXML
    private void setCloseList() {
        if(isOpening){
            fade.play();
            close.play();
            AnchorPane.setLeftAnchor(Main.mainPage.getChildren().get(0), 100.0);
            MainController._openList.setOpacity(1);
            isOpening = false;
        }
    }

    public static void setOpenList() {
        if(!isOpening) {
            emerge.play();
            open.play();
            AnchorPane.setLeftAnchor(Main.mainPage.getChildren().get(0), 370.0);
            isOpening = true;
        }
    }

    public static void setListNote() {
        list.getChildren().setAll(listNote);
    }
    public static void setListEvent() {
        list.getChildren().setAll(listEvent);
    }

    @FXML
    public void CreateNote() {
        ManageNote.createNote();
    }
}

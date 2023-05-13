package AppScreen;

import AppObject.Event;
import AppObject.Note;
import Manage.ManageEvent;
import Manage.ManageNote;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class ListObjectController implements Initializable {
    @FXML
    public AnchorPane listObject;
    @FXML
    private AnchorPane listView;
    @FXML
    private ComboBox<String> sort;
    @FXML
    private TextField boxSearch;


    public static AnchorPane list;
    public static VBox listNote = new VBox();
    public static VBox listEvent = new VBox();
    private static int typeSortNote = 3;
    private static int typeSortEvent = 3;
    private static ComboBox<String> sortVar;
    public static boolean isOpening = true;
//    Di chuyen
    TranslateTransition closeList = new TranslateTransition();
    public static TranslateTransition openList = new TranslateTransition();
    FadeTransition fadeList = new FadeTransition();
    public static FadeTransition emergeList = new FadeTransition();
    static TranslateTransition closeContent = new TranslateTransition();
    TranslateTransition openContent = new TranslateTransition();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boxSearch.setText("");
        for(final Note var : ManageNote.getListRoot()) {
            listNote.getChildren().add(var.getButton());
        }
        for(final Event var : ManageEvent.getListRoot()) {
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
//        listNote.setOrientation(Orientation.VERTICAL);
        listNote.setSpacing(5);
        listEvent.setAlignment(Pos.TOP_CENTER);
//        listEvent.setOrientation(Orientation.VERTICAL);
        listEvent.setSpacing(5);

        list = listObject;
        sortVar = sort;
        fadeList.setNode(listView);
        fadeList.setFromValue(1);
        fadeList.setToValue(0);
        fadeList.setDuration(Duration.seconds(0.45));
        emergeList.setNode(listView);
        emergeList.setFromValue(0);
        emergeList.setToValue(1);
        emergeList.setDuration(Duration.seconds(0.45));
        closeList.setNode(listView);
        closeList.setByX(-290);
        closeList.setDuration(Duration.seconds(0.45));
        openList.setNode(listView);
        openList.setByX(290);
        openList.setDuration(Duration.seconds(0.45));
        //sort comboBox
        ObservableList<String> listSort = FXCollections.observableArrayList("name","updated","created","sort");

        sort.setItems(listSort);
//        sort.setEditable(false);
//        sort.cancelEdit();
        sort.setOnAction(Event -> {
            if(sort.getSelectionModel().getSelectedIndex() == 3) {
                boxSearch.setText("");
            }
            if(list.getChildren().contains(listNote)){
                uploadListNote(ManageNote.sortList(sort.getSelectionModel().getSelectedIndex(), boxSearch.getText()));
                typeSortNote = sort.getSelectionModel().getSelectedIndex();
            }
            else if(list.getChildren().contains(listEvent)){
                uploadListEvent(ManageEvent.sortList(sort.getSelectionModel().getSelectedIndex(), boxSearch.getText()));
                typeSortEvent = sort.getSelectionModel().getSelectedIndex();
            }
        });
        sort.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem (String item, boolean empty) {
                        super.updateItem(item,empty);
                        if(item == null || empty) {
                            setText(null);
                        }
                        else if(item.contains("sort")) {
                            setText("default");
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });


        //box search
        boxSearch.setOnKeyReleased(e -> {
            if(listObject.getChildren().contains(listNote)){
                uploadListNote(ManageNote.sortList(sort.getSelectionModel().getSelectedIndex(), boxSearch.getText()));
            } else if(listObject.getChildren().contains(listEvent)) {
                uploadListEvent(ManageEvent.sortList(sort.getSelectionModel().getSelectedIndex(), boxSearch.getText()));
            }
        });
    }
    @FXML
    private void setCloseList() {
        if(isOpening){
            fadeList.play();
            closeList.play();
            AnchorPane.clearConstraints(MainController.contentView);
            MainController.closeContentView.play();
            MainController._openList.setOpacity(1);
            isOpening = false;
            MainController.close.play();
        }
    }

    public static void setOpenList() {
        if(!isOpening) {
            emergeList.play();
            openList.play();
            isOpening = true;

        }
    }

    public static void setListNote() {
        list.getChildren().setAll(listNote);
        sortVar.getSelectionModel().select(typeSortNote);
    }
    public static void setListEvent() {
        list.getChildren().setAll(listEvent);
        sortVar.getSelectionModel().select(typeSortEvent);
    }

    @FXML
    public void Create() {
        if(listObject.getChildren().contains(listNote)){
            ManageNote.createNew();
        } else if(listObject.getChildren().contains(listEvent)) {
            ManageEvent.createNew();
        }
    }

    public static void uploadListEvent(ArrayList<Event> list) {
        listEvent.getChildren().clear();
        for(final Event e : list) {
            if(!listEvent.getChildren().contains(e.getButton())){
                listEvent.getChildren().add(e.getButton());
            }
        }
    }
    public static void uploadListNote(ArrayList<Note> list) {
        listNote.getChildren().clear();
        for(final Note e : list) {
            if(!listNote.getChildren().contains(e.getButton())) {
                listNote.getChildren().add(e.getButton());
            }
        }
    }
}

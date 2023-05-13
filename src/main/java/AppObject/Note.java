package AppObject;

import AppScreen.Main;
import AppScreen.MainController;
import AppScreen.TabNoteController;
import Manage.ManageEvent;
import Manage.ManageNote;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;

import java.time.ZonedDateTime;

public class Note {
    private static ToggleGroup buttonNote = new ToggleGroup();
    //Content Note
    private ContentNote contentNote;
    //Button Note
    private ToggleButton button = new ToggleButton();
    //Tab Note
    private Tab tab = new Tab();
    private AnchorPane tabContent = new AnchorPane();
    private TextField title = new TextField();
    private HTMLEditor body = new HTMLEditor();
    private boolean canSave = false;
    private Label deleteFromButton = new Label("delete");

    public Note(ContentNote content) {
        button.setToggleGroup(buttonNote);
        button.setPrefSize(270,30);
        button.setMinSize(270,30);
        button.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
        contentNote = content;
        button.setText(contentNote.getTitle());
        tab.setText(contentNote.getTitle());

        title.setPromptText("Title");
        title.setAlignment(Pos.BOTTOM_LEFT);
        body.setHtmlText(contentNote.getBody());
        title.setText(contentNote.getTitle());

        deleteFromButton.setPrefSize(50,20);
        deleteFromButton.setBackground(Background.fill(Color.WHITE));
        deleteFromButton.setOnMouseEntered(e -> {
            deleteFromButton.setBorder(Border.stroke(Color.BLACK));
        });
        deleteFromButton.setOnMouseExited(e -> {
            Main.listView.getChildren().remove(deleteFromButton);
        });

        makeTab();
    }
    private void makeTab() {
        //design tab layout
        tab.setClosable(true);
        tab.setContent(tabContent);

        AnchorPane.setTopAnchor(body,70.0);
        AnchorPane.setBottomAnchor(body,50.0);
        AnchorPane.setLeftAnchor(body,10.0);
        AnchorPane.setRightAnchor(body,20.0);

        AnchorPane.setTopAnchor(title,0.0);
        AnchorPane.setLeftAnchor(title,10.0);
        AnchorPane.setRightAnchor(title,20.0);

        //text update and create
        Text created = new Text("Created: " + contentNote.getCreated_at().getMonth().toString() + ", " +
                contentNote.getCreated_at().getDayOfMonth() + ", " +
                contentNote.getCreated_at().getYear());
        Text lastUpdate = new Text("Last Update: " + contentNote.getUpdated_at().getMonth().toString() + ", " +
                contentNote.getUpdated_at().getDayOfMonth() + ", " +
                contentNote.getUpdated_at().getYear());

        VBox taskTime = new VBox(created,lastUpdate);
        taskTime.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(taskTime,10.0);
        AnchorPane.setLeftAnchor(taskTime,0.0);
        AnchorPane.setRightAnchor(taskTime,20.0);



        //save
        ImageView save = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/save.png")));
        ImageView saved = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/saved.png")));
        Button saveBut = new Button();
        saveBut.setGraphic(save);
        saveBut.setStyle("-fx-background-color:  transparent");
        save.setPreserveRatio(true);
        save.setFitHeight(20);
        saved.setPreserveRatio(true);
        saved.setFitHeight(20);
        saveBut.setOnMouseClicked(Event -> {
            if(canSave){
                saveBut.setGraphic(save);
                canSave = false;
                contentNote.setBody(body.getHtmlText());
                contentNote.setTitle(title.getText());
                contentNote.setUpdated_at(ZonedDateTime.now());
                lastUpdate.setText("Last Update: " + contentNote.getUpdated_at().getMonth().toString() + ", " +
                        contentNote.getUpdated_at().getDayOfMonth() + ", " +
                        contentNote.getUpdated_at().getYear());
                ManageNote.writeDataofNotetoFile(contentNote);
            }
        });


        //delete
        ImageView delete = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/delete.png")));
        Button deleteBut = new Button();
        deleteBut.setGraphic(delete);
        deleteBut.setStyle("-fx-background-color:  transparent");
        delete.setPreserveRatio(true);
        delete.setFitHeight(20);
        deleteBut.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)) {
                MainController.openBoxDelete();
            }
        });

        //important note
        ImageView importanton = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/importanton.png")));
        ImageView importantoff = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/importantoff.png")));
        Button importantButton = new Button();
        if(contentNote.isImportant()) {
            importantButton.setGraphic(importantoff);
            button.setGraphic( new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/importantoff.png"),20,20,true,false)));
        } else {
            importantButton.setGraphic(importanton);
        }
        importantButton.setStyle("-fx-background-color:  transparent");
        importanton.setPreserveRatio(true);
        importanton.setFitHeight(30);
        importantoff.setPreserveRatio(true);
        importantoff.setFitHeight(30);
        importantButton.setOnMouseClicked(Event -> {
            contentNote.setImportant(!contentNote.isImportant());
            if(contentNote.isImportant()) {
                button.setGraphic( new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/importantoff.png"),20,20,true,false)));
                importantButton.setGraphic(importantoff);
            } else {
                button.setGraphic(null);
                importantButton.setGraphic(importanton);
            }
            canSave = true;
            saveBut.setGraphic(saved);
        });


        HBox taskBox = new HBox(saveBut, deleteBut, importantButton);
        taskBox.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(taskBox,10.0);
        AnchorPane.setLeftAnchor(taskBox,20.0);
        AnchorPane.setRightAnchor(taskBox,20.0);
        taskBox.setSpacing(10);

        tabContent.getChildren().addAll(body, title, taskTime, taskBox);
        //set up mouse click
        title.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                tab.setText(title.getText());
                button.setText(title.getText());
                canSave = true;
                saveBut.setGraphic(saved);
            }
        });
        body.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                canSave = true;
                saveBut.setGraphic(saved);
            }
        });
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if (!Main.noteView.getTabs().contains(tab)) {
                        Main.noteView.getTabs().add(tab);
                    }
                    Main.noteView.getSelectionModel().select(tab);
                    ManageNote.idNoteSelected = getId();
                } else {
                    if(mouseEvent.getButton().equals(MouseButton.SECONDARY) && ManageNote.idNoteSelected == contentNote.getId()) {
                        Main.mainPage.getChildren().add(deleteFromButton);
                        deleteFromButton.setLayoutX(mouseEvent.getScreenX());
                        deleteFromButton.setLayoutY(mouseEvent.getSceneY());
                    }
                }
            }
        });


    }


    //Getter
    public int getId() {
        return contentNote.getId();
    }

    public ToggleButton getButton() {
        return button;
    }

    public Tab getTab() {
        return tab;
    }

    public ContentNote getContentNote() {
        return contentNote;
    }
}

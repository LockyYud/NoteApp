package AppObject;

import AppScreen.Main;
import ManageObject.ManageNote;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.ZonedDateTime;

public class Note {
    //Content Note
    private ContentNote contentNote;
    //Button Note
    private Button buttonNote = new Button();
    //Tab Note
    private Tab tabNote = new Tab();
    private AnchorPane tabContent = new AnchorPane();
    private TextField tab_NoteTitle = new TextField();
    private TextArea tab_NoteBody = new TextArea();
    private boolean canSave = false;

    public Note(ContentNote content) {
        buttonNote.setPrefSize(200,40);
        buttonNote.getStylesheets().add(this.getClass().getResource("buttonstyle.css").toExternalForm());
        contentNote = content;
        buttonNote.setText(contentNote.getTitle());
        tabNote.setText(contentNote.getTitle());
        tab_NoteBody.setPromptText("Body");
        tab_NoteTitle.setPromptText("Title");
        tab_NoteTitle.setAlignment(Pos.BOTTOM_LEFT);
        tab_NoteTitle.getStylesheets().add(this.getClass().getResource("textfieldstyle.css").toString());
        tab_NoteBody.getStylesheets().add(this.getClass().getResource("textareastyle.css").toString());
        tab_NoteBody.setText(contentNote.getBody());
        tab_NoteTitle.setText(contentNote.getTitle());
        makeTab();
    }
    private void makeTab() {
        //design tab layout
        tabNote.setClosable(true);
        tabNote.setContent(tabContent);

        AnchorPane.setTopAnchor(tab_NoteBody,70.0);
        AnchorPane.setBottomAnchor(tab_NoteBody,50.0);
        AnchorPane.setLeftAnchor(tab_NoteBody,10.0);
        AnchorPane.setRightAnchor(tab_NoteBody,20.0);

        AnchorPane.setTopAnchor(tab_NoteTitle,0.0);
        AnchorPane.setLeftAnchor(tab_NoteTitle,10.0);
        AnchorPane.setRightAnchor(tab_NoteTitle,20.0);





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
                contentNote.setBody(tab_NoteBody.getText());
                contentNote.setTitle(tab_NoteTitle.getText());
                contentNote.setUpdated_at(ZonedDateTime.now());
                lastUpdate.setText("Last Update: " + contentNote.getUpdated_at().getMonth().toString() + ", " +
                        contentNote.getUpdated_at().getDayOfMonth() + ", " +
                        contentNote.getUpdated_at().getYear());
                ManageNote.writeDataofNotetoFile(contentNote);
            }
        });


        //italic
        ImageView italic = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/italic.png")));
        Button italicBut = new Button();
        italicBut.setGraphic(italic);
        italicBut.setStyle("-fx-background-color:  transparent");
        italic.setPreserveRatio(true);
        italic.setFitHeight(20);



        //underline
        ImageView underline = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/underline.png")));
        Button underlineBut = new Button();
        underlineBut.setGraphic(underline);
        underline.setPreserveRatio(true);
        underlineBut.setStyle("-fx-background-color:  transparent");
        underline.setFitHeight(20);


        //bold
        ImageView bold = new ImageView(new Image(Note.class.getResourceAsStream("/AppObject/Icon/bold.png")));
        Button boldBut = new Button();
        boldBut.setGraphic(bold);
        bold.setPreserveRatio(true);
        boldBut.setStyle("-fx-background-color:  transparent");
        bold.setFitHeight(20);



        HBox taskBox = new HBox(saveBut,boldBut,italicBut,underlineBut);
        taskBox.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(taskBox,10.0);
        AnchorPane.setLeftAnchor(taskBox,20.0);
        AnchorPane.setRightAnchor(taskBox,20.0);
        taskBox.setSpacing(10);

        tabContent.getChildren().addAll(tab_NoteBody, tab_NoteTitle, taskTime, taskBox);
        //set up mouse click
        tab_NoteTitle.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                tabNote.setText(tab_NoteTitle.getText());
                buttonNote.setText(tab_NoteTitle.getText());
                canSave = true;
                saveBut.setGraphic(saved);
            }
        });
        tab_NoteBody.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                canSave = true;
                saveBut.setGraphic(saved);
            }
        });
        buttonNote.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!Main.noteView.getTabs().contains(tabNote)){
                    Main.noteView.getTabs().add(tabNote);
                }
                Main.noteView.getSelectionModel().select(tabNote);
            }
        });
    }



    //save content note
    private void saveNote() {
        contentNote.setTitle(tab_NoteTitle.getText());
        contentNote.setBody(tab_NoteBody.getText());
        ManageNote.writeDataofNotetoFile(contentNote);
    }

    //Getter
    public int getId() {
        return contentNote.getId();
    }

    public Button getButton() {
        return buttonNote;
    }

    public Tab getTabNote() {
        return tabNote;
    }

    public ContentNote getContentNote() {
        return contentNote;
    }
}

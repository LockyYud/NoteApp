package AppScreen;

import AppObject.ContentNote;
import AppObject.Event;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main extends Application {
    public static AnchorPane calendarView;
    public static AnchorPane mainPage;
    public static TabPane noteView;
    public static AnchorPane detailEvent;
    public static AnchorPane listView;
    public static int numsNote = 0;
    public static int numsNoteNew = 0;
    public static void main(String[] args) {
        launch();
    }


    public void start(Stage stage) throws IOException {
        calendarView = new FXMLLoader(Main.class.getResource("Calendar.fxml")).load();
        noteView = new FXMLLoader(Main.class.getResource("TabNote.fxml")).load();
        detailEvent = new FXMLLoader(Main.class.getResource("DetailEvent.fxml")).load();
        listView = new FXMLLoader(Main.class.getResource("List.fxml")).load();
        mainPage = new FXMLLoader(Main.class.getResource("HomePage.fxml")).load();
        Scene scene = new Scene(mainPage,1280 , 600);
        stage.setTitle("Note App");
        stage.setScene(scene);
        stage.show();
    }
}




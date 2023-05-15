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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane mainPage;
    @FXML
    private AnchorPane sectionContentView;
    @FXML
    private Button openList;
    @FXML
    private Text NoteTips;
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

        setUpStageDelete();
        listNoteTips.add("Be organized: Use headings, subheadings, bullet points, or numbered lists to structure your notes. This helps you find information quickly and understand the relationships between different ideas.");
        listNoteTips.add("Use abbreviations and symbols: Develop a set of abbreviations or symbols that you can use to write faster. This can be particularly useful during lectures or when taking notes in real-time.");
        listNoteTips.add("Listen actively: Focus on the main ideas and key points being discussed. Don't try to write down everything word-for-word, as it can be overwhelming and ineffective. Instead, paraphrase and summarize information in your own words.");
        listNoteTips.add("Be selective: Prioritize important information and concepts over trivial details. Capture the main ideas, supporting examples, and any additional insights provided by the speaker or text.");
        listNoteTips.add("Visualize information: Use diagrams, charts, mind maps, or other visual aids to represent relationships between ideas or complex concepts. Visual representations can enhance your understanding and make it easier to remember information.");
        listNoteTips.add("Review and revise: Regularly review and revise your notes to reinforce your understanding. Add clarifications, additional details, or examples if needed. This process helps solidify the information in your memory.");
        listNoteTips.add("Use different colors: Incorporate different colors in your notes to visually distinguish between different topics or highlight important information. This can make your notes more engaging and memorable.");
        listNoteTips.add("Include examples: When possible, include relevant examples to illustrate concepts or clarify your understanding. Examples can make the information more concrete and relatable.");
        listNoteTips.add("Date and number your pages: Assigning dates and page numbers to your notes helps you keep them in chronological order and easily refer back to specific information later on.");
    }

    private void setUpStageDelete() {
        HBox box = new HBox();
        definitely_delete.setTitle("Do you want to delete?");
        definitely_delete.setScene(new Scene(box,300,50));
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
        CalendarController.listUp();
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

    ArrayList<String> listNoteTips = new ArrayList<>();
    @FXML
    private void setNoteTips() {
        NoteTips.setText(listNoteTips.get((int) (Math.random() * listNoteTips.size())));
    }
}

package ManageObject;

import AppObject.ContentNote;
import AppObject.Note;
import AppScreen.ListObjectController;
import AppScreen.Main;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ManageNote {
    public static List<Note> noteList = new ArrayList<>();
    static {
        File folderNote = new File("src\\main\\resources\\NoteData");
        for(final File fileNote : folderNote.listFiles()) {
            Main.numsNote++;
            try{
                FileInputStream fileIn = new FileInputStream(fileNote);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                noteList.add(new Note((ContentNote) in.readObject()));
                in.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
        }
    }
    public static void getAllNote(String url) {
    }
    public static void writeDataofNotetoFile(ContentNote contentNote) {
        try {
            String path = "src\\main\\resources\\NoteData\\" + "note"+ contentNote.getId() + ".ser";
            File file = new File(path);
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(contentNote);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Note> sortList (int type) {
        Collections.sort(noteList,new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                /*name*/
                if(type == 0) {
                    return o1.getButton().getText().compareTo(o2.getButton().getText());
                } else if(type == 1) {
                    return o1.getContentNote().getUpdated_at().compareTo(o2.getContentNote().getUpdated_at());
                } else if(type == 2) {
                    return o1.getContentNote().getCreated_at().compareTo(o2.getContentNote().getCreated_at());
                }
                return 0;
            }
        });
        return (ArrayList<Note>) noteList;
    }
    public static void createNote() {
        Note note = new Note(new ContentNote());
        note.getButton().setText("New");
        ListObjectController.listNote.getChildren().add(note.getButton());
        Main.noteView.getTabs().add(note.getTabNote());
        Main.noteView.getSelectionModel().select(note.getTabNote());
    }
}

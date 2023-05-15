package Manage;

import AppObject.ContentNote;
import AppObject.Event;
import AppObject.Note;
import AppScreen.ListObjectController;
import AppScreen.Main;
import com.dlsc.formsfx.model.structure.NodeElement;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ManageNote{
//    public static int numsNote = 0;
    public static int idNoteSelected;
    private static ArrayList<Note> listRoot = new ArrayList<>();
    private static ArrayList<Note> list = new ArrayList<>();
    static {
        File folderNote = new File("src\\main\\resources\\NoteData");
        for(final File fileNote : folderNote.listFiles()) {
//            numsNote++;
            try{
                FileInputStream fileIn = new FileInputStream(fileNote);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                list.add(new Note((ContentNote) in.readObject()));
                in.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
        }
        listRoot.addAll(list);
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
    public static void deleteNote() {
        for(Note note : list) {
            if(note.getId() == idNoteSelected) {
                Main.noteView.getTabs().remove(note.getTab());
                ListObjectController.listNote.getChildren().remove(note.getButton());
                try {
                    String path = "src\\main\\resources\\NoteData\\" + "note"+ note.getId() + ".ser";
                    File file = new File(path);
                    if(file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                note = null;
                list.remove(note);
                listRoot.remove(note);
                return;
            }
        }
    }
    public static ArrayList<Note> sortList (int type, String item) {
        Collections.sort(list,new Comparator<Note>() {
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
        if(type == 3) list.addAll(listRoot);
        ArrayList<Note> result = new ArrayList<>();
        for(Note var : list) {
            if(var.getContentNote().getTitle().contains(item)) {
                result.add(var);
            }
        }
        return result;
    }
    public static ArrayList<Note> getListRoot() {
        return listRoot;
    }
    public static void createNew() {
        Note note = new Note(new ContentNote());
        note.getButton().setText("New");
        ListObjectController.listNote.getChildren().add(note.getButton());
        Main.noteView.getTabs().add(note.getTab());
        Main.noteView.getSelectionModel().select(note.getTab());
        listRoot.add(note);
        list.add(note);
        note.getButton().setSelected(true);
    }

}

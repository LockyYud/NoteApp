package ManageObject;

import AppObject.ContentEvent;
import AppObject.ContentNote;
import AppObject.Event;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class ManageEvent {
    public static List<Event> eventList = new ArrayList<>();
    public static void writeDataofEventtoFile(ContentEvent contentEvent) {
        try {
            String path = "src\\main\\resources\\EventData\\" + "note"+ contentEvent.getEvent_id() + ".ser";
            File file = new File(path);
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(contentEvent);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

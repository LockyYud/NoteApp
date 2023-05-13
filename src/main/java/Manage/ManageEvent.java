package Manage;

import AppObject.ContentEvent;
import AppObject.Event;
import AppObject.Note;
import AppScreen.EventContentController;
import AppScreen.ListObjectController;
import AppScreen.Main;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ManageEvent{
    private static ArrayList<Event> listRoot = new ArrayList<>();
    private static ArrayList<Event> list = new ArrayList<>();
    public static int idEventSelected;
    static {
        File folderNote = new File("src\\main\\resources\\EventData");
        for(final File fileNote : folderNote.listFiles()) {
            try{
                FileInputStream fileIn = new FileInputStream(fileNote);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                listRoot.add(new Event((ContentEvent) in.readObject()));
                in.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
            list.addAll(listRoot);
        }
    }
    public static void deleteEvent() {
        for(Event event : listRoot) {
            if(event.getId() == idEventSelected) {
                Main.calendarView.getChildren().remove(Main.detailEvent);
                ListObjectController.listEvent.getChildren().remove(event.getButton());
                try {
                    String path = "src\\main\\resources\\EventData\\" + "event"+ event.getId() + ".ser";
                    File file = new File(path);
                    if(file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                event = null;
                listRoot.remove(event);
                if(list.contains(event)) {
                    list.remove(event);
                }
                return;
            }
        }
    }
    public static void writeDataofEventtoFile(ContentEvent contentEvent) {
        try {
            String path = "src\\main\\resources\\EventData\\" + "event"+ contentEvent.getId() + ".ser";
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
    public static void createNew() {
        Event event = new Event(new ContentEvent());
        event.getButton().setText("New");
        ListObjectController.listEvent.getChildren().add(event.getButton());
        if(!Main.calendarView.getChildren().contains(Main.detailEvent)){
            Main.calendarView.getChildren().add(Main.detailEvent);
        }
        EventContentController.assignEvent(event);
        listRoot.add(event);
        list.add(event);
    }

    public static ArrayList<Event> sortList (int type, String item) {
        ArrayList<Event> result = new ArrayList<>();
        Collections.sort(list,new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                /*name*/
                if(type == 0) {
                    return o1.getButton().getText().compareTo(o2.getButton().getText());
                } else if(type == 1) {
                    return o1.getContentEvent().getUpdated_at().compareTo(o2.getContentEvent().getUpdated_at());
                } else if(type == 2) {
                    return o1.getContentEvent().getCreated_at().compareTo(o2.getContentEvent().getCreated_at());
                }
                return 0;
            }
        });
        if(type == 3) list.addAll(listRoot);
        for(Event var : list) {
            if(var.getContentEvent().getTitle().contains(item)) {
                result.add(var);
            }
        }
        return (ArrayList<Event>) result;
    }

    public static List<Event> getListRoot() {
        return listRoot;
    }

    public static ArrayList<Event> filterbyDay(LocalDate dateTime) {
        list.clear();
        for(final Event e : listRoot) {
            if(e.getStartTime().getDayOfYear() == dateTime.getDayOfYear() & e.getStartTime().getYear() == dateTime.getYear()) {
                list.add(e);
            }
        }
        return (ArrayList<Event>) list;
    }
}

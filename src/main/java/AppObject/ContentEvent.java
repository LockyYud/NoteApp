package AppObject;


import java.util.Date;

public class ContentEvent {
    private int event_id;
    private String event_title;
    private String event_description;
    private Date start_time;
    private Date end_time;
    private Date reminder_time;
    private String event_location;

    public int getEvent_id() {
        return event_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(Date reminder_time) {
        this.reminder_time = reminder_time;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }
}
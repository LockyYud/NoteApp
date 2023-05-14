package AppObject;


import java.io.Serializable;
import java.time.ZonedDateTime;

public class ContentEvent extends ContentObject implements Serializable {

    private ZonedDateTime start_time = ZonedDateTime.now();
    private ZonedDateTime end_time = ZonedDateTime.now();
    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public ZonedDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(ZonedDateTime start_time) {
        this.start_time = start_time;
    }

    public ZonedDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(ZonedDateTime end_time) {
        this.end_time = end_time;
    }

    public ContentEvent() {
        created_at = ZonedDateTime.now();
        updated_at = ZonedDateTime.now();
        location = "";
        this.id = this.hashCode();
    }
}
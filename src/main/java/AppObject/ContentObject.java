package AppObject;

import java.io.Serializable;
import java.time.ZonedDateTime;

public abstract class ContentObject implements Serializable {
    protected int id;
    protected String title = "";
    protected String body = "";
    protected ZonedDateTime created_at;
    protected ZonedDateTime updated_at;
    protected boolean important = false;

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

}

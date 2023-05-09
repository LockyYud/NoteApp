package AppObject;

import AppScreen.Main;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class ContentNote implements Serializable {
    private int id = -1;
    private String title = "";
    private String body = "";
    private ZonedDateTime created_at;
    private ZonedDateTime updated_at;
    private boolean liked = false;
    public ContentNote() {
        this.id = Main.numsNote;
        Main.numsNote++;
        created_at = ZonedDateTime.now();
        updated_at = ZonedDateTime.now();
    }

    public int getId() {
        return id;
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

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}


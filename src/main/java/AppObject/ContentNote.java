package AppObject;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class ContentNote extends ContentObject implements Serializable  {
    public ContentNote() {
        this.id = this.hashCode();
//        ManageNote.numsNote++;
        created_at = ZonedDateTime.now();
        updated_at = ZonedDateTime.now();
    }
}


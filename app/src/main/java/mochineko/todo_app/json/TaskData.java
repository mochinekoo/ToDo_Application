package mochineko.todo_app.json;

import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import mochineko.todo_app.library.DeserializedJson;
import mochineko.todo_app.status.TaskStatus;

public class TaskData extends DeserializedJson {

    private int id;
    private Color color;
    private String name;
    private String description;
    private String status;
    private String created_at;

    public TaskData(int id, String name, String description, String status, String created_at, Color color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.created_at = created_at;
        this.color = color;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRawStatus() {
        return status;
    }

    public TaskStatus getStatus() {
        return TaskStatus.valueOf(status);
    }

    public String getRawCreatedAt() {
        return created_at;
    }

    public Color getColor() {
        return color;
    }

    public LocalDateTime getCreatedAt() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date formatDate = sdf.parse(created_at);
            return LocalDateTime.ofInstant(formatDate.toInstant(), ZoneId.systemDefault());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

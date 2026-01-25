package mochineko.todo_app.status;

import mochineko.todo_app.R;
import mochineko.todo_app.json.TaskData;
import mochineko.todo_app.library.DeserializedJson;

public enum JsonFileType {
    TASK_LIST("task_list.json", R.raw.task_list, TaskData.class);

    private final String fileName;
    private final int id;
    private final Class<? extends DeserializedJson> deserialized_class;

    JsonFileType(String fileName, int id, Class<? extends DeserializedJson> deserialized_class) {
        this.fileName = fileName;
        this.id = id;
        this.deserialized_class = deserialized_class;
    }

    public String getFileName() {
        return fileName;
    }

    public Class<? extends DeserializedJson> getDeserializedClass() {
        return deserialized_class;
    }

    public int getId() {
        return id;
    }
}

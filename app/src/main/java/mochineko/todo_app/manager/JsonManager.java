package mochineko.todo_app.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import mochineko.todo_app.library.DeserializedJson;
import mochineko.todo_app.status.JsonFileType;

public class JsonManager {

    private static final Map<JsonFileType, JsonManager> memory_json_map = new HashMap<>();

    private JsonElement raw_element;
    private final JsonFileType type;
    private final Gson gson;
    private final Context context;

    private JsonManager(JsonFileType type, Context context) {
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.context = context;
    }

    public static JsonManager getInstance(JsonFileType fileType, Context context) {
        if (!memory_json_map.containsKey(fileType)) {
            memory_json_map.put(fileType, new JsonManager(fileType, context));
        }
        return memory_json_map.get(fileType);
    }

    /**
     * メモリー上からJSONを取得する
     * @return {@link JsonElement}として返す
     */
    public JsonElement getRawElement() {
        createJson();
        if (raw_element == null) updateJson();
        return raw_element;
    }

    /**
     *　デシリアライズしたJSONとして返す
     */
    public DeserializedJson getDeserializedJson() {
        return gson.fromJson(getRawElement(), type.getDeserializedClass());
    }

    /**
     * ファイルからJsonを直接取得する
     * @return {@link JsonElement}として返す
     */
    @Deprecated
    public JsonElement getFileRawElement() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, JsonElement.class);
        }
    }

    /**
     * @return 現在のJSONがどこにあるかを、ファイルとして返す
     */
    public File getFile() {
        return new File(context.getFilesDir(), type.getFileName());
    }

    /**
     * メモリー上のJSONを更新する。
     */
    public void updateJson() {
        try {
            if (!getFile().exists()) createJson(); //無い場合は作成
            if (raw_element == null) {
                raw_element = getFileRawElement();
            }
            memory_json_map.put(type, this);

            try (BufferedWriter write = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
                write.write(gson.toJson(raw_element));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSONを作成する関数。
     * @return ファイルの作成に成功した場合 もしくは 既にファイルが存在する場合 は trueを返し、失敗した場合は false を返す。
     */
    public boolean createJson() {
        boolean status = false;
        if (!getFile().exists()) {
            try (InputStream in = context.getResources().openRawResource(type.getId())) {
                Files.copy(in, getFile().toPath());
                updateJson();
                status = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            status = true;
        }
        return status;
    }
}

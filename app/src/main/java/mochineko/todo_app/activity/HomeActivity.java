package mochineko.todo_app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import mochineko.todo_app.R;
import mochineko.todo_app.json.TaskData;
import mochineko.todo_app.listener.SearchViewQueryTextListener;
import mochineko.todo_app.recyclerview.TaskListRecyclerAdapter;

public class HomeActivity extends AppCompatActivity {

    public static final List<TaskData> TASK_DATA_LIST =
            Arrays.asList(
                    new TaskData(0, "ああ", "aa", "RUNNING", "2025/10/10 12:10", Color.valueOf(255, 0, 255)),
                    new TaskData(1, "いい", "aa", "RUNNING", "2025/10/10 12:10", Color.valueOf(0, 0, 255))
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialize();
    }

    public void initialize() {
        RecyclerView task_recycler = findViewById(R.id.taskList_recycler);
        TaskListRecyclerAdapter adapter = new TaskListRecyclerAdapter(this, TASK_DATA_LIST);
        task_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        task_recycler.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.taskSearch_view);
        searchView.setOnQueryTextListener(new SearchViewQueryTextListener(this));
    }
}
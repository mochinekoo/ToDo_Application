package mochineko.todo_app.listener;

import android.app.Activity;
import android.graphics.Color;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import mochineko.todo_app.R;
import mochineko.todo_app.activity.HomeActivity;
import mochineko.todo_app.json.TaskData;
import mochineko.todo_app.recyclerview.TaskListRecyclerAdapter;

public class SearchViewQueryTextListener implements SearchView.OnQueryTextListener {

    private Activity activity;

    public SearchViewQueryTextListener(Activity activity) {
        this.activity = activity;
    }

    /**
     * テキストが変更された時のリスナー
     */
    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    /**
     * エンターを押したときのリスナー
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s == null) return false;
        if (s.isEmpty()) return false;
        RecyclerView recyclerView = activity.findViewById(R.id.taskList_recycler);
        List<TaskData> list = HomeActivity.TASK_DATA_LIST.stream().filter(taskData -> s.contains(taskData.getName())).collect(Collectors.toList());
        TaskListRecyclerAdapter adapter = new TaskListRecyclerAdapter(activity, list);
        recyclerView.setAdapter(adapter);
        return false;
    }
}

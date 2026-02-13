package mochineko.todo_app.recyclerview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mochineko.todo_app.R;
import mochineko.todo_app.json.TaskData;

public class TaskListRecyclerAdapter extends RecyclerView.Adapter<TaskListRecyclerAdapter.ViewHolder> {

    private Activity activity;
    private List<TaskData> taskDataList;

    public TaskListRecyclerAdapter(Activity activity, List<TaskData> taskDataList) {
        this.activity = activity;
        this.taskDataList = taskDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tasklist_layout, parent, false);
        return new ViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskData taskData = taskDataList.get(position);
        holder.taskName.setText(taskData.getName());
        holder.taskDescription.setText(taskData.getDescription());
        holder.itemView.setBackgroundColor(taskData.getColor().toArgb());
    }

    @Override
    public int getItemCount() {
        return taskDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName, taskDescription;

        public ViewHolder(@NonNull View itemView, Activity activity) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName_text);
            taskDescription = itemView.findViewById(R.id.task_description);
        }
    }

}
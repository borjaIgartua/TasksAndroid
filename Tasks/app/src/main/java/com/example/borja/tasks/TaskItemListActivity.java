package com.example.borja.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.borja.tasks.IO.operations.OperationErrorListener;
import com.example.borja.tasks.IO.operations.OperationSuccessListener;
import com.example.borja.tasks.IO.operations.TaskOperation;
import com.example.borja.tasks.model.Task;

import java.util.List;

/**
 * An activity representing a list of TasksItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TaskItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TaskItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskitem_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.taskitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        View recyclerView = findViewById(R.id.taskitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el action bar
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Podemos manejar la opcion elegida por el usuario
        switch (item.getItemId()) {

            case R.id.action_add_task:
                showAddTask();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {

        TaskOperation.retrieveTasks(getApplicationContext(), new OperationSuccessListener() {
            @Override
            public void success(Object obj) {
                if (obj instanceof List) {
                    tasks = (List<Task>)obj;
                    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(tasks));
                }
            }
        }, new OperationErrorListener() {
            @Override
            public void error(Exception ex) {
                //TODO: show error
            }
        });
    }

    private void updateTask(Task task) {

        TaskOperation.updateTask(getApplicationContext(), task, null, new OperationErrorListener() {
            @Override
            public void error(Exception ex) {
                //TODO: show error and undone the task
            }
        });
    }

    private void addTask(Task task) {

        TaskOperation.insertTask(getApplicationContext(), task, new OperationSuccessListener() {
            @Override
            public void success(Object obj) {

                if (obj instanceof Task) {
                    tasks.add((Task)obj);
                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.taskitem_list);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        }, new OperationErrorListener() {
            @Override
            public void error(Exception ex) {

            }
        });
    }

    private void showAddTask() {

        AddTaskDialogFragment dialogFragment = AddTaskDialogFragment.newInstance(new AddTaskDialogFragment.OnOKInsertTaskListener() {
            @Override
            public void operationAccept(String title, String message) {

                Task task = new Task(title,message);
                addTask(task);
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "TaskItemListActivity");
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Task> mValues;

        public SimpleItemRecyclerViewAdapter(List<Task> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.taskitem_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTitleView.setText(mValues.get(position).getTitle());
            holder.mMessageView.setText(mValues.get(position).getMessage());
            holder.mCheckbox.setChecked(mValues.get(position).isDone());

            holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    holder.mItem.setDone(checked);
                    updateTask(holder.mItem);
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putParcelable(TaskItemDetailFragment.ARG_ITEM_ID, holder.mItem);
                        TaskItemDetailFragment fragment = new TaskItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.taskitem_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TaskItemDetailActivity.class);
                        intent.putExtra(TaskItemDetailFragment.ARG_ITEM_ID, holder.mItem);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public final TextView mMessageView;
            public final CheckBox mCheckbox;
            public Task mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(R.id.id);
                mMessageView = (TextView) view.findViewById(R.id.content);
                mCheckbox = (CheckBox)view.findViewById(R.id.task_checkBox);
            }
        }
    }
}

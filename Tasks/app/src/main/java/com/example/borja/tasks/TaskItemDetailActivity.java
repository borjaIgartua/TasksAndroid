package com.example.borja.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.borja.tasks.IO.operations.OperationErrorListener;
import com.example.borja.tasks.IO.operations.OperationSuccessListener;
import com.example.borja.tasks.IO.operations.TaskOperation;
import com.example.borja.tasks.model.Task;

/**
 * An activity representing a single TaskItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TaskItemListActivity}.
 */
public class TaskItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete_task);
        delete.setImageResource(R.drawable.ic_delete_white_24dp);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.delete_task_confirm_message), Snackbar.LENGTH_LONG);
                snackbar.setAction(getResources().getString(android.R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteTask();
                    }
                });
                snackbar.show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(TaskItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(TaskItemDetailFragment.ARG_ITEM_ID));
            TaskItemDetailFragment fragment = new TaskItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.taskitem_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, TaskItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void deleteTask() {

        Task task = getIntent().getParcelableExtra(TaskItemDetailFragment.ARG_ITEM_ID);
        TaskOperation.deleteTask(getApplicationContext(), task, new OperationSuccessListener() {
            @Override
            public void success(Object obj) {
                finish();
            }
        }, new OperationErrorListener() {
            @Override
            public void error(Exception ex) {

            }
        });
    }
}

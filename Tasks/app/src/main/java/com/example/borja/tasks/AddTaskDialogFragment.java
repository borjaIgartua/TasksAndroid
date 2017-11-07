package com.example.borja.tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.borja.tasks.model.Task;

import java.io.Serializable;

/**
 * Created by Borja on 22/10/17.
 */

public class AddTaskDialogFragment extends DialogFragment {

    private static final String ARG_OKLISTENER = "okListener";

    private OnOKInsertTaskListener okListener;

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    public static AddTaskDialogFragment newInstance(OnOKInsertTaskListener okListener) {

        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OKLISTENER, okListener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            okListener = (OnOKInsertTaskListener) getArguments().getSerializable(ARG_OKLISTENER);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View contentView = inflater.inflate(R.layout.fragment_add_place_dialog,null);
        builder.setView(contentView);

        final EditText nameEditText = (EditText)contentView.findViewById(R.id.add_task_title);
        final EditText descriptionEditText = (EditText)contentView.findViewById(R.id.add_task_message);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (okListener != null) {
                    okListener.operationAccept(nameEditText.getText().toString(),descriptionEditText.getText().toString());
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        okListener = null;
    }

    public interface OnOKInsertTaskListener extends Serializable {
        void operationAccept(String title, String message);
    }
}

package com.developer.diegoalves.peladapay.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

/**
 * Created by Diego Alves on 01/09/2015.
 */
public class GenericDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String EXTRA_ID = "id";
    private static final String EXTRA_MENSSAGE = "mensagem";
    private static final String EXTRA_TITLE = "titulo";
    private static final String EXTRA_BUTTON = "botoes";
    private static final String DIALOG_TAG = "simplesDialog";

    private int mDialog;

    public static GenericDialogFragment newDialog(int id, int title, int message, int[] buttonsText) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ID, id);
        bundle.putInt(EXTRA_TITLE, title);
        bundle.putInt(EXTRA_MENSSAGE, message);
        bundle.putIntArray(EXTRA_BUTTON, buttonsText);

        GenericDialogFragment dialog = new GenericDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = getArguments().getInt(EXTRA_ID);

        int title = getArguments().getInt(EXTRA_TITLE);
        int message = getArguments().getInt(EXTRA_MENSSAGE);
        int[] buttons = getArguments().getIntArray(EXTRA_BUTTON);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);

        switch (buttons.length) {
            case 3:
                alertDialogBuilder.setNeutralButton(buttons[2], this);
            case 2:
                alertDialogBuilder.setNegativeButton(buttons[1], this);
            case 1:
                alertDialogBuilder.setPositiveButton(buttons[0], this);
        }
        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int buttonPressed) {
        Activity activity = getActivity();

        if(activity instanceof onClickDialog) {
            onClickDialog listener = (onClickDialog) activity;
            listener.onClick(mDialog, buttonPressed);
        }
    }

    public void open(FragmentManager fragmentManager) {
        Fragment dialogFragment = fragmentManager.findFragmentByTag(DIALOG_TAG);

        if(dialogFragment == null) {
            show(fragmentManager, DIALOG_TAG);
        }
    }

    public interface onClickDialog {
        void onClick(int id, int button);
    }
}

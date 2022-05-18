package com.example.astroybat.classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class PrintAlert extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Предупреждение")
                .setMessage("Не найдено приложения для работы с Excel-таблицами, установите его и попробуйте снова!")
                .setPositiveButton("Ок", (dialog, id) -> dialog.cancel());
        return builder.create();
    }
}

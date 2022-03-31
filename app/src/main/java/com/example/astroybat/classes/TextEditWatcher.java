package com.example.astroybat.classes;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.astroybat.activities.SmetaEdit;

import java.util.Date;

public class TextEditWatcher implements TextWatcher {

    String filled_str;
    EditText t_edit;
    SmetaEdit smeta_edit;

    public TextEditWatcher(String filled_str, EditText t_edit, SmetaEdit smeta_edit){
        this.filled_str = filled_str;
        this.t_edit =  t_edit;
        this.smeta_edit = smeta_edit;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        switch (filled_str){
            case "title":
                smeta_edit.setSmetaTitle(smeta_edit.uuid, t_edit.getText().toString());
                break;
            case "zakazchik":
                smeta_edit.setSmetaZakazchik(smeta_edit.uuid, t_edit.getText().toString());
                break;
            case "podryadchik":
                smeta_edit.setSmetaPodryadchik(smeta_edit.uuid, t_edit.getText().toString());
                break;
            case "raboti":
                smeta_edit.setSmetaRaboti(smeta_edit.uuid, t_edit.getText().toString());
                break;
            case "object":
                smeta_edit.setSmetaObject(smeta_edit.uuid, t_edit.getText().toString());
                break;
            case "osnovanie":
                smeta_edit.setSmetaOsnovanie(smeta_edit.uuid, t_edit.getText().toString());
                break;
            case "date":
                Date date = (Date) t_edit.getText();
                smeta_edit.setSmetaDate(smeta_edit.uuid, date.getTime());
        }
    }
}

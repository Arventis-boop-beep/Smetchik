package com.example.astroybat.classes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.astroybat.activities.SmetaEdit;

public class TextEditWatcher implements TextWatcher {

    String key;
    EditText t_edit;
    SmetaEdit smeta_edit;

    public TextEditWatcher(String key, EditText t_edit, SmetaEdit smeta_edit){
        this.key = key;
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
        smeta_edit.smetaSetValueForKey(smeta_edit.database, smeta_edit.uuid, t_edit.getText().toString(), key);
    }
}

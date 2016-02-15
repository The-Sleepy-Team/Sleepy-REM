package com.example.tyler.helloworld;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by tyler on 2/12/2016.
 */
public class SelectingItem implements AdapterView.OnItemSelectedListener{

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        Toast.makeText(parent.getContext(), "Selected Item: " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {

    }
}

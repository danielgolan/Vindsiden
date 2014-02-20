package com.vindsiden.windwidget.CustomAutoComplete;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import com.vindsiden.windwidget.R;
import com.vindsiden.windwidget.Vindsiden;

/**
 * Created by Daniel on 23.01.14.
 */
public class CustomAutocompleteTextChangedListener implements TextWatcher {
    public static final String TAG = "CustomAutocompleteTextChangedListener.java";
    Context context;


    public CustomAutocompleteTextChangedListener(Context context) {
        this.context = context;

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        //Log user input
        Log.d(TAG, "User input : " + userInput);
        // Vindsiden vindsiden = new Vindsiden();


        //Query the database based on the user input
        Vindsiden.getInstance().item = Vindsiden.getInstance().getItemsFromDb(userInput.toString());


        //Update the adapter
        Vindsiden.getInstance().myAdapter.notifyDataSetChanged();

        //Making a new thread seems to improve perfomance
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Vindsiden.getInstance().myAdapter = new StedAdapter(Vindsiden.getInstance(), R.layout.autocomplete_layout, Vindsiden.getInstance().item);

            }
        });

        thread.run();

        Vindsiden.getInstance().AcTv_sok.setAdapter(Vindsiden.getInstance().myAdapter);


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

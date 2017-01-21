package com.biniam.android.gpsbasedpongandroidgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by USER on 1/19/2017.
 */

public class SettingDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        Button saveButton = (Button) view.findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("com.biniam.android.gpsbasedpongandroidgame_preference_file_key", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                EditText address = (EditText) view.findViewById(R.id.address);
                EditText portText = (EditText) view.findViewById(R.id.port);


                int portNumber = 0;
                if(portText.getText().toString() != null & !portText.getText().toString().trim().equals("")){
                    portNumber = Integer.parseInt(portText.getText().toString());
                }

                if(address.getText().toString() != ""){
                    editor.putString("address", address.getText().toString());
                }

                editor.putInt("port", portNumber);
                editor.commit();
                //SettingDialogFragment.this.getDialog().cancel();
                SettingDialogFragment.this.getDialog().dismiss();
            }
        });
    }


}

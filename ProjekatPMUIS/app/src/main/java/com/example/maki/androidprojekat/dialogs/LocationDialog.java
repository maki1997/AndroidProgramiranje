package com.example.maki.androidprojekat.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * Created by Maki on 5/29/2018.
 */

public class LocationDialog extends AlertDialog.Builder {

    public LocationDialog(Context context) {

        super(context);

        setUpDialog();
    }

    private void setUpDialog(){
        setTitle("Warning");
        setMessage("You have disabled location services! Do you want to enable them?");
        setCancelable(false);

        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getContext().startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialog.dismiss();
            }
        });

        setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

    }

    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}

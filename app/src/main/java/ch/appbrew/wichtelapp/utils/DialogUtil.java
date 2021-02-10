package ch.appbrew.wichtelapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import ch.appbrew.wichtelapp.R;

/**
 * This class provides useful methods to show user dialog messages
 */
public class DialogUtil {

    public static void popupMessage(String message, String title, FragmentActivity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setIcon(R.drawable.common_google_signin_btn_icon_dark);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("internet","Ok btn pressed");
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void popupMessage(String message, String title, Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setIcon(R.drawable.common_google_signin_btn_icon_dark);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("internet","Ok btn pressed");
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

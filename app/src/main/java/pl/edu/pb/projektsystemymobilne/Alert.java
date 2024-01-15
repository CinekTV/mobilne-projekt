package pl.edu.pb.projektsystemymobilne;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Alert {

    private String title;
    private String message;

    public Alert(String title, String allert_message){
        this.message = allert_message;
        this.title = title;
    }

    public void Show(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(this.title);
        builder.setMessage(this.message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

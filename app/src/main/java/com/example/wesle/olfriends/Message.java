package com.example.wesle.olfriends;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/* News Feature */

public class Message {
    protected static void toastMessage(Context context, String msg, String delay){
        if(delay.equals("")){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    protected static void logMessage(String tag, String msg){
        Log.d(tag,msg);
    }
}

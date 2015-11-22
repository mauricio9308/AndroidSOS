package mx.sos.sos.broadcast;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import mx.sos.sos.activity.RequestActivity;
import mx.sos.sos.activity.RequestResponseActivity;
import mx.sos.sos.util.ParseHandler;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class ParsePushCustomReceiver extends ParsePushBroadcastReceiver {


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPushOpen(Context context, Intent intent) {
//        super.onPushOpen(context, intent);

        Log.d("HANDLING", "TRIGGER");

        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String jsonData = extras.getString("com.parse.Data");
                JSONObject json;
                json = new JSONObject(jsonData);
                Log.d("DATA", json.toString() );


                int not_id = json.getInt("not_id");
                String message = json.getString( "alert" );


                /* we process the notification */

                /* appending the notification intent */
                Intent responseIntent;

                if( not_id == Integer.parseInt(ParseHandler.ID_REQUEST_RESPONSE)){
                    responseIntent = new Intent( context, RequestResponseActivity.class);
                    responseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    responseIntent.putExtra(RequestResponseActivity.EXTRA_RESPONSE_MESSAGE, message);
                    responseIntent.putExtra(RequestResponseActivity.EXTRA_RESPOSNE, json.getBoolean("is_affirmative"));
                    Log.d("HANDLING", "REQUEST");
                    context.startActivity( responseIntent );
                }else{
                    /* we are handling a simple request response */
                    Log.d("HANDLING", "REQUEST_REPSOSNE");
                    responseIntent = new Intent( context, RequestActivity.class);
                    responseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    responseIntent.putExtra( RequestActivity.EXTRA_MESSAGE, json.getString("me"));
                    context.startActivity( responseIntent );
                }


            }
        } catch (JSONException e) {
            Log.d("HANDLING", "ERROR");

            e.printStackTrace();
        }


    }



}

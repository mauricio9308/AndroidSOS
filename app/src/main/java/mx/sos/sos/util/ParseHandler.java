package mx.sos.sos.util;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class ParseHandler {

    public static final String USER_CHANNEL = "user_channel";
    public static final String ADMIN_CHANNEL = "admin_channel";

    public static final String ID_REQUEST = "1";
    public static final String ID_REQUEST_RESPONSE = "2";

    public static void subscribeUser(){
        ParsePush.subscribeInBackground( USER_CHANNEL );
    }

    public static void subscribeAdmin(){
        ParsePush.subscribeInBackground( ADMIN_CHANNEL );
    }

    public static void unsubscribeUser(){
        ParsePush.unsubscribeInBackground(USER_CHANNEL);
    }

    public static void unsubscribeAdmin(){
        ParsePush.unsubscribeInBackground( ADMIN_CHANNEL );
    }

    public static void sendMessageRequestAssistance( String user, String message ){
        try {
            ParsePush push = new ParsePush();
            ParseQuery pushQuery = ParseInstallation.getQuery();
            pushQuery.whereEqualTo("channels", ADMIN_CHANNEL);
            push.setQuery(pushQuery);
            JSONObject data = new JSONObject("{\"alert\": \"¡El usuario "+ user +" necesita asistencia!\",\"not_id\": \""+ ID_REQUEST +"\", \"me\": \""+ message +"\"}");
            push.setData( data );
            push.sendInBackground(new SendCallback() {
                @Override
                public void done(ParseException e) {
                    Log.d("SEND", "SEND");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageRespondRequest( String response, boolean wasOk ){
        try {
            ParsePush push = new ParsePush();
            ParseQuery pushQuery = ParseInstallation.getQuery();
            pushQuery.whereEqualTo("channels", USER_CHANNEL);
            push.setQuery(pushQuery);
            JSONObject data = new JSONObject("{\"alert\": \""+ response +"\",\"not_id\": \""+ ID_REQUEST_RESPONSE +"\", \"is_affirmative\": "+ wasOk +"}");
            push.setData(data);
            push.sendInBackground(new SendCallback() {
                @Override
                public void done(ParseException e) {
                    Log.d("SEND", "SEND");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

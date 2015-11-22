package mx.sos.sos.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class SOSApplication extends Application{

    @Override
    public void onCreate(){
        super.onCreate();

        /* setting up the parse keys  */
        Parse.initialize(this, "7ygsPwtlRXKS0O6SCHZTX6RYoGuRYqxCUBOM19J9", "qNIamL4g5Lb455kjnR1zrgYhtHTPUpfZNshohy8z");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

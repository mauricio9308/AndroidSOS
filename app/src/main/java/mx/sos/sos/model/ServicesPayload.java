package mx.sos.sos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class ServicesPayload {

    @SerializedName("success")
    private boolean mSuccess;

    @SerializedName("model")
    private ServiceUser[] mUsers;

    public ServiceUser[] getUsers(){
        return mUsers;
    }

    public boolean wasSuccessful(){
        return mSuccess;
    }


}

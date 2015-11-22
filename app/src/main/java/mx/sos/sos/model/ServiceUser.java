package mx.sos.sos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauriciolara on 11/21/15.
 */
public class ServiceUser {

    @SerializedName("id")
    private long mId;

    @SerializedName("email")
    private String email;

    //TODO FIX THIS NOW
    @SerializedName("password")
    private String mPassword;

    @SerializedName("name")
    private String mName;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("adress")
    private String mAddress;

    private int mStars;
    private String mPriceRange;

    public long getId(){
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public int getStars() {
        return mStars;
    }

    public String getPriceRange(){
        return mPriceRange;
    }

    public String getPhone(){
        return mPhone;
    }
}

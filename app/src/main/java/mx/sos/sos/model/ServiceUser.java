package mx.sos.sos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauriciolara on 11/21/15.
 */
public class ServiceUser {

    @SerializedName("id")
    private long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("adress")
    private String mAddress;

    @SerializedName("phone")
    private String mPhone;

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

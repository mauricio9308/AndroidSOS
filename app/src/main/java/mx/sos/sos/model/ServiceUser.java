package mx.sos.sos.model;

/**
 * Created by mauriciolara on 11/21/15.
 */
public class ServiceUser {
    
    private String mName;
    private String mAddress;
    private int mStars;
    private String mPriceRange;

    public ServiceUser(String name, String address, int stars, String priceRange ) {
        mName = name;
        mAddress = address;
        mStars = stars;
        mPriceRange = priceRange;
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
}

package mx.sos.sos.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import mx.sos.sos.model.ServiceUser;
import mx.sos.sos.model.ServicesPayload;
import mx.sos.sos.net.SOSApiInterface;
import mx.sos.sos.net.SOSApiSingleton;
import retrofit.RetrofitError;

/**
 * Created by mauriciolara on 11/16/15.
 */
public class ServicesUsersLoader extends AsyncTaskLoader<ServicesUsersLoader.ServicesLoaderResult> {

    private ServicesLoaderResult mResult;
    private int mServiceId;


    public static final int OUTSIDE_SERVICE_PLUMBER = 1;
    public static final int OUTSIDE_SERVICE_POWER = 2;
    public static final int OUTSIDE_SERVICE_HEALTH = 3;
    public static final int OUTSIDE_SERVICE_CAR = 4;

    public ServicesUsersLoader(Context context, int service_id ) {
        super(context);
        mServiceId = service_id;
    }

    @Override
    public void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public ServicesLoaderResult loadInBackground() {
        try{
            SOSApiInterface apiInterface = SOSApiSingleton.getApiInterfaceInstance();

            ServicesPayload servicesPayload;
            switch( mServiceId ){
                case OUTSIDE_SERVICE_PLUMBER:
                    servicesPayload = apiInterface.getPlumbers();
                    break;
                case OUTSIDE_SERVICE_HEALTH:
                    servicesPayload = apiInterface.getMedics();
                    break;
                case OUTSIDE_SERVICE_POWER:
                    servicesPayload =apiInterface.getElectricists();
                    break;
                case OUTSIDE_SERVICE_CAR:
                    servicesPayload = apiInterface.getMechanics();
                    break;
                default:
                    throw new IllegalStateException("Invalid service id");
            }

            /* error in the server */
            if( servicesPayload.getUsers() == null ){
                return new ServicesLoaderResult( new Exception( ) );
            }

            return new ServicesLoaderResult( servicesPayload.getUsers() );
        }catch( RetrofitError e ){
            return new ServicesLoaderResult( new Exception() );
        }
    }

    @Override
    public void deliverResult(ServicesLoaderResult data) {
        if (isReset()) {
                /*
				 * Since this is a simple list, we don't need to release any
				 * resources
				 */
            return;
        }

			/*
			 * We don't hold a reference to old data because we don't need to
			 * release any resources
			 */

        mResult = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

			/*
			 * Since this is a simple list, we don't need to release any
			 * resources
			 */
    }

    @Override
    protected void onReset() {
        onStopLoading(); // Ensure the loader has been stopped.
        if (mResult != null) {

				/*
				 * Since this is a simple list, we don't need to release any
				 * resources
				 */
            mResult = null;
        }

			/* We don't use an observer, so we skip unregistering the observer */
    }

    @Override
    public void onCanceled(ServicesLoaderResult data) {
        super.onCanceled(data);

			/*
			 * Since this is a simple list, we don't need to release any
			 * resources
			 */
    }


    public class ServicesLoaderResult{
        private ServiceUser[] mServiceUsers;
        private Exception mException;

        public ServicesLoaderResult( ServiceUser[] serviceUsers ){
            mServiceUsers = serviceUsers;
        }

        public ServicesLoaderResult( Exception e ){
            mException = e;
        }

        public ServiceUser[] getServiceUsers() {
            return mServiceUsers;
        }

        public Exception getException() {
            return mException;
        }

        public boolean isSuccessful(){
            return ( mException == null );
        }
    }
}

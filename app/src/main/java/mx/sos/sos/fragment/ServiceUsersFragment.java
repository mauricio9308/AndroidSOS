package mx.sos.sos.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mx.sos.sos.adapter.ServiceUserAdapter;
import mx.sos.sos.app.WorkingRecyclerViewFragment;
import mx.sos.sos.loader.ServicesUsersLoader;
import mx.sos.sos.model.ServiceUser;

public class ServiceUsersFragment extends WorkingRecyclerViewFragment implements ServiceUserAdapter.AdapterCallback,
        LoaderManager.LoaderCallbacks<ServicesUsersLoader.ServicesLoaderResult>{

    /* frag arguments */
    private static final String FRAG_ARG_SERVICE_ID = "FRAG_ARG_SERVICE_ID";

    /* loader identifier */
    private static final int LOADER_ID_HISTORY_LENDS = 1;

    private Callback mCallback;
    private ServiceUserAdapter mAdapter;
    private RecyclerView mRecyclerView;

    /* year identifier */
    private int mServiceId;

    public ServiceUsersFragment(){
    }

    public static ServiceUsersFragment newInstance( int service_id ){
        Bundle args = new Bundle( 1 );
        args.putInt(FRAG_ARG_SERVICE_ID, service_id);

        ServiceUsersFragment fragment = new ServiceUsersFragment();
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onAttach( Activity activity ){
        super.onAttach(activity);
        try{
            mCallback = ( Callback ) activity;
        }catch( ClassCastException e ){
            throw new ClassCastException("Activity " + activity.getClass().getSimpleName() + " must implement " +
                    Callback.class.getSimpleName() );
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ){
        super.onViewCreated(view, savedInstanceState);

        /* setting the empty and retry text */
        setEmptyText("No hay prestadores disponibles");
        setRetryText("Error al obtener prestadores");
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ){
        super.onActivityCreated(savedInstanceState);

        /* getting the reference of the recycler view */
        mRecyclerView = getRecylcerView();

        /* passing the reference of the service id */
        mServiceId = getFragArgServiceId();

        /* loading the recycler view */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager(linearLayoutManager);

        /* setting the adapter */
        mAdapter = new ServiceUserAdapter( ServiceUsersFragment.this /* Adapter Callback */ );
        mRecyclerView.setAdapter(mAdapter);

        /* fetching the lends */
        getLoaderManager().initLoader(LOADER_ID_HISTORY_LENDS, null /* args */,
                ServiceUsersFragment.this /* LoaderCallbacks */);
    }

    private int getFragArgServiceId(){
        return getArguments().getInt( FRAG_ARG_SERVICE_ID );
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallback = null;
    }

    @Override
    public Loader<ServicesUsersLoader.ServicesLoaderResult> onCreateLoader(int id, Bundle args) {
        /* set the recycler view in a loading state */
        setRecyclerViewState(WorkingRecyclerViewFragment.STATE_LOADING);

        return new ServicesUsersLoader( getContext(), mServiceId );
    }

    @Override
    public void onLoadFinished(Loader<ServicesUsersLoader.ServicesLoaderResult> loader,
                               ServicesUsersLoader.ServicesLoaderResult result ) {
        if( result.isSuccessful() ){
            ServiceUser[] users = result.getServiceUsers();
            if( users.length == 0 ){
                /* setting the empty lends message */
                setRecyclerViewState( WorkingRecyclerViewFragment.STATE_EMPTY );
            }else{
                /* setting the loaded lends inside the adapters */
                mAdapter.setServiceUsers( users );

                setRecyclerViewState(WorkingRecyclerViewFragment.STATE_LOADED);
            }
        }else{
            /* setting the recycler view to the retry state */
            setRecyclerViewState(WorkingRecyclerViewFragment.STATE_RETRY);
        }
    }

    @Override
    public void onLoaderReset(Loader<ServicesUsersLoader.ServicesLoaderResult> loader) {
        // DO NOTHING
    }

    /**
     * Restarts the loading of the lends in the application
     * */
    @Override
    public void onRetryClicked(){
        getLoaderManager().restartLoader(LOADER_ID_HISTORY_LENDS, null /* args */,
                ServiceUsersFragment.this /* Loader Callbacks */);
    }


    @Override
    public void onUserClicked(ServiceUser user ) {
        if( mCallback != null ){
            mCallback.onUserClicked( user );
        }
    }

    /**
     * Interface for the communications between the lends fragment and the Main Activity
     * */
    public interface Callback{
        void onUserClicked( ServiceUser serviceUser );
    }
}
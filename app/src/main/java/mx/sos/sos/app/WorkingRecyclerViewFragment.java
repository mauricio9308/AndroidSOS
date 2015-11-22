package mx.sos.sos.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import mx.sos.sos.R;

/**
 * Class that imitates the workflow of a ListFragment but with a RecyclerView as main list component.
 *
 * Created by Mauricio Lara Soberanis on 8/6/15.
 */
public class WorkingRecyclerViewFragment extends Fragment implements View.OnClickListener{

    /* Representation of the states of the Recycler View */
    public static final int STATE_LOADING = 1;
    public static final int STATE_LOADED = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_RETRY = 4;

    private static final int NOT_SET = -1;

    final private Handler mHandler = new Handler();

    /* Recycler view and its adapter */
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mRecyclerView.focusableViewAvailable( mRecyclerView );
        }
    };

    private TextView mStandardEmptyTextView;
    private View mProgressContainer;
    private View mListContainer;
    private View mRetryButton;
    private boolean mListShown;

    private View mEmptyView;

    private CharSequence mEmptyText;
    private CharSequence mRetryText;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ){

        View root = inflater.inflate( R.layout.layout_working_recycler, container /* parent */,
                false /* attachToRoot */);


        /* getting the reference for the empty and retry views */
        mEmptyView = root.findViewById( R.id.working_recycler_empty );
        mStandardEmptyTextView = ( TextView ) root.findViewById( R.id.working_recycler_empty_textview );

        mRetryButton = root.findViewById( R.id.working_recycler_retry_button );

        /* reference for the progress container and the list container */
        mProgressContainer = root.findViewById( R.id.working_recycler_progress_container );
        mListContainer = root.findViewById( R.id.working_recycler_internal_recycler_container );

        /* setting the reference for the reload with the retry button */
        mRetryButton.setOnClickListener( WorkingRecyclerViewFragment.this /* OnClickListener */);

        return root;
    }

    /**
     * Attach to list view once the view hierarchy has been created.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureRecyclerView();
    }

    /**
     * Detach from list view.
     */
    @Override
    public void onDestroyView() {
        /* clearing all the references to the given fragment */
        mHandler.removeCallbacks(mRequestFocus);
        mRecyclerView = null;
        mListShown = false;
        mProgressContainer = null;
        mStandardEmptyTextView = null;
        mEmptyView = null;
        mRetryButton = null;
        super.onDestroyView();
    }

    /**
     * Get the activity's list view widget.
     */
    public RecyclerView getRecylcerView() {
        ensureRecyclerView();
        return mRecyclerView;
    }

    public void onClick( View view ){
        if( view.getId() == R.id.working_recycler_retry_button ){
            onRetryClicked();
        }

        //Process the rest of the clicks
    }

    /**
     * Override this method for accessing of the retry button
     *
     * */
    public void onRetryClicked(){

    }

    /**
     * The default content for a ListFragment has a TextView that can be shown when the list is
     * empty. If you would like to have it shown, call this method to supply the text it should
     * use.
     */
    public void setEmptyText(CharSequence text) {
        ensureRecyclerView();
        if (mStandardEmptyTextView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        mStandardEmptyTextView.setText(text);
        mEmptyText = text;
    }

    public void setRetryText( CharSequence text ){
        mRetryText = text;
    }

    /**
     * Control whether the RecyclerView is being displayed. You can make it not displayed if you are waiting
     * for the initial data to show in it. During this time an indeterminant progress indicator will
     * be shown instead.
     * <p/>
     * <p/>
     * Applications do not normally need to use this themselves. The default behavior of
     * ListFragment is to start with the list not being shown, only showing it once an adapter is
     * given with {@link #setRecyclerViewAdapter(RecyclerView.Adapter)}. If the list at that point had
     * not been shown, when it does get shown it will be do without the user ever seeing the hidden
     * state.
     *
     * @param shown
     *         If true, the list view is shown; if false, the progress indicator. The initial value
     *         is true.
     */
    private void setRecyclerViewShown(boolean shown) {
        setRecyclerViewShown(shown, true /* animate */);
    }

    /**
     * Like {@link #setRecyclerViewShown(boolean)}, but no animation is used when transitioning from the
     * previous state.
     */
    private void setRecyclerViewShownNoAnimation(boolean shown) {
        setRecyclerViewShown(shown, false /* animate */);
    }

    /**
     * Control whether the list is being displayed. You can make it not displayed if you are waiting
     * for the initial data to show in it. During this time an indeterminant progress indicator will
     * be shown instead.
     *
     * @param shown
     *         If true, the list view is shown; if false, the progress indicator. The initial value
     *         is true.
     * @param animate
     *         If true, an animation will be used to transition to the new state.
     */
    private void setRecyclerViewShown(boolean shown, boolean animate) {
        ensureRecyclerView();
        if (mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.GONE);
        }
    }

    /**
     * Since RecyclerView doesn't support setEmptyTextView we add the support via view hiding
     * */
    private void setRecyclerViewEmptyState( boolean isEmpty ){
        mRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        mStandardEmptyTextView.setVisibility( isEmpty ? View.VISIBLE : View.GONE );
        mStandardEmptyTextView.setText(isEmpty ? mEmptyText : mRetryText);
        mEmptyView.setVisibility( isEmpty ? View.VISIBLE : View.GONE );
    }

    private void setRecyclerViewRetryState( boolean isRetryPossible ){
        mRetryButton.setVisibility( isRetryPossible ? View.VISIBLE : View.GONE );
        mStandardEmptyTextView.setVisibility( isRetryPossible ? View.VISIBLE : View.GONE );
        mStandardEmptyTextView.setText(isRetryPossible ? mRetryText : mEmptyText);
        mEmptyView.setVisibility( isRetryPossible? View.VISIBLE : View.GONE);
    }

    /**
     * Clear the previous set states
     * */
    private void clearStates(){
        setRecyclerViewEmptyState(false);
        setRecyclerViewRetryState(false);
    }

    private void showList( boolean show ){
        if( isAdded() ){
            setRecyclerViewShown( show );
        }else{
            setRecyclerViewShownNoAnimation( show );
        }
    }

    public void setRecyclerViewState( int state ){
        clearStates();

        switch ( state ){
            case STATE_LOADING:
                //Only shows the loading progress bar
                showList( false  );
                return;
            case STATE_LOADED:
                //Shows the list only
                showList( true );
                return;
            case STATE_EMPTY:
                //Shows the list empty view
                setRecyclerViewEmptyState( true );
                showList( true );
                return;
            case STATE_RETRY:
                setRecyclerViewRetryState( true );
                showList( true );
                return;
        }
    }


    /**
     * Get the #{RecyclerView.Adapter} associated with this activity's ListView.
     */
    public RecyclerView.Adapter getRecyclerViewAdapter() {
        return mAdapter;
    }

    /**
     * Provide the Adapter for the recycler view.
     */
    public void setRecyclerViewAdapter( RecyclerView.Adapter adapter) {
        boolean hadAdapter = mAdapter != null;
        mAdapter = adapter;
        if ( mRecyclerView != null) {
            mRecyclerView.setAdapter(adapter);
            if (!mListShown && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter. It is now time to show it.
                final View rootView = getView();
                if (rootView != null) {
                    setRecyclerViewShown(true, rootView.getWindowToken() != null);
                }
            }
        }
    }


    /**
     * Ensures that the layout views are set in place and configured
     * */
    private void ensureRecyclerView(){
        if( mRecyclerView != null ){
            return;
        }

        View root = getView();
        if( root == null ){
            throw new IllegalStateException("Content view not yet created");
        }

        /* getting the reference of the Recyler view */
        mRecyclerView = (RecyclerView) root.findViewById( R.id.working_recycler_recyclerview );

        /* Setting up the empty view */
        if( !TextUtils.isEmpty(mEmptyText) ) {
            mStandardEmptyTextView.setText(mEmptyText);
        }

        mListShown = true;
        if( mAdapter != null ){
            RecyclerView.Adapter adapter = mAdapter;
            mAdapter = null;
            setRecyclerViewAdapter(adapter);
        }else{
            // We are starting without an adapter, so assume we won't
            // have our data right away and start with the progress indicator.
            if (mProgressContainer != null) {
                setRecyclerViewShown(false /* shown */, false /* animate */);
            }
        }

        mHandler.post( mRequestFocus );
    }


}

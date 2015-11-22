package mx.sos.sos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mx.sos.sos.R;
import mx.sos.sos.model.ServiceUser;

/**
 * Created by mauriciolara on 11/21/15.
 */
public class ServiceUserAdapter extends RecyclerView.Adapter<ServiceUserAdapter.ServiceViewHolder> implements View.OnClickListener{

    private AdapterCallback mAdapterCallback;
    private ServiceUser[] mServiceUsers;

    public ServiceUserAdapter( ServiceUser[] serviceUsers, AdapterCallback adapterCallback ){
        mServiceUsers = serviceUsers;
        mAdapterCallback = adapterCallback;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        View userView = inflater.inflate(R.layout.item_service_user, parent, false /* attachToRoot */);

        ServiceViewHolder viewHolder = new ServiceViewHolder( userView );
        viewHolder.baseView.setOnClickListener( ServiceUserAdapter.this /* OnClickListener */);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        /* binding the view holder */
        ServiceUser user = mServiceUsers[ position ];
        holder.txtServiceName.setText( user.getName() );
        holder.txtPriceRange.setText( user.getPriceRange() );

        /* setting the tag reference for the list element */
        holder.baseView.setTag(R.id.service_user, mServiceUsers[position]);
    }

    @Override
    public int getItemCount() {
        return ( mServiceUsers == null ? 0 : mServiceUsers.length );
    }

    @Override
    public void onClick( View view ){
        if( mAdapterCallback != null ){
            mAdapterCallback.onUserClicked( (ServiceUser)view.getTag( R.id.service_user ) );
        }
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{

        private TextView txtServiceName;
        private TextView txtPriceRange;
        private View baseView;

        public ServiceViewHolder( View view ){
            super( view );

            baseView = view;
            txtServiceName = ( TextView ) view.findViewById( R.id.txt_service_user_name );
            txtPriceRange = ( TextView ) view.findViewById( R.id.txt_price_range );
        }
    }

    public interface AdapterCallback{
        void onUserClicked( ServiceUser clicked );
    }
}

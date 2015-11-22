package mx.sos.sos.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mx.sos.sos.R;
import mx.sos.sos.adapter.ServiceUserAdapter;
import mx.sos.sos.model.ServiceUser;

public class MoreServicesActivity extends AppCompatActivity implements ServiceUserAdapter.AdapterCallback{

    public static final String EXTRA_SERVICE_ID = "EXTRA_SERVICE_ID";

    private static final int NOT_SET = -1;

    public static final int SERVICE_PLUMBER = 1;
    public static final int SERVICE_POWER = 2;
    public static final int SERVICE_HEALTH = 3;
    public static final int SERVICE_CAR = 4;

    private RecyclerView mRecyclerView;
    private int mServiceId;
    private ServiceUserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting the reference for the service recycler view
        mServiceId = getExtraServiceId();

        /* setting the action bar color */
        int color  = getActionBarColor(mServiceId);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable( new ColorDrawable( color  ));
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        /* setting up the recycler view */
        mRecyclerView = ( RecyclerView) findViewById( R.id.recycler_view_more );

        /* setting the layout manager */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( MoreServicesActivity.this /* context */);
        mRecyclerView.setLayoutManager( linearLayoutManager );

        /* setting the adapter */
        mAdapter = new ServiceUserAdapter( new ServiceUser[]{
                new ServiceUser("User 1", "Merida Calle 2 por 1 y 4 #12", 1, "100$-200$" ),
                new ServiceUser("User 1", "Merida Calle 2 por 1 y 4 #12", 1, "100$-200$" ),
                new ServiceUser("User 1", "Merida Calle 2 por 1 y 4 #12", 1, "100$-200$" ),
                new ServiceUser("User 1", "Merida Calle 2 por 1 y 4 #12", 1, "100$-200$" ),
                new ServiceUser("User 1", "Merida Calle 2 por 1 y 4 #12", 1, "100$-200$" ),
                new ServiceUser("User 1", "Merida Calle 2 por 1 y 4 #12", 1, "100$-200$" ),
                new ServiceUser("User 1", "Merida Calle 2 por 1 y 4 #12", 1, "100$-200$" )
        }, MoreServicesActivity.this /* adapter callback */);
        mRecyclerView.setAdapter(  mAdapter  );
    }

    private int getExtraServiceId(){
        int service_id =  getIntent().getIntExtra( EXTRA_SERVICE_ID, NOT_SET );
        if( service_id == NOT_SET ){
            throw new IllegalStateException("You need to provide a service id");
        }

        return service_id;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        if( item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }else{
            return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onUserClicked(ServiceUser clicked) {
        Intent goToServiceActivity = new Intent( MoreServicesActivity.this /* context */, GetServiceActivity.class );
        goToServiceActivity.putExtra( GetServiceActivity.EXTRA_SERVICE_ID, mServiceId );
        startActivity( goToServiceActivity );
    }

    private int getActionBarColor( int service_id ){
        Resources res = getResources();
        int color = Color.BLACK;
        switch ( service_id ){
            case SERVICE_CAR:
                color = res.getColor(R.color.car);
                break;
            case SERVICE_HEALTH:
                color = res.getColor(R.color.health);
                break;
            case SERVICE_PLUMBER:
                color = res.getColor(R.color.plumber);
                break;
            case SERVICE_POWER:
                color = res.getColor(R.color.power);
                break;
        }

        return color;
    }


}

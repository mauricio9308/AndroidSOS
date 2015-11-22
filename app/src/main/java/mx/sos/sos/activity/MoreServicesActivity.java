package mx.sos.sos.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mx.sos.sos.R;
import mx.sos.sos.fragment.ServiceUsersFragment;
import mx.sos.sos.model.ServiceUser;

public class MoreServicesActivity extends AppCompatActivity implements ServiceUsersFragment.Callback{

    /* frag tag for the services list */
    private static final String FRAG_TAG_SERVICE_LIST = "FRAG_TAG_SERVICE_LIST";

    /* extra for the service id reference */
    public static final String EXTRA_SERVICE_ID = "EXTRA_SERVICE_ID";

    private static final int NOT_SET = -1;

    public static final int SERVICE_PLUMBER = 1;
    public static final int SERVICE_POWER = 2;
    public static final int SERVICE_HEALTH = 3;
    public static final int SERVICE_CAR = 4;

    private int mServiceId;

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

        if( savedInstanceState == null ){
            /* we set the fragment */
            getSupportFragmentManager()
                    .beginTransaction()
                    .add( R.id.container_services_list,
                            ServiceUsersFragment.newInstance(mServiceId), FRAG_TAG_SERVICE_LIST)
                    .commit();
        }
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

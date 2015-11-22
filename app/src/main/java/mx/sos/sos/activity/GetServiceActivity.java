package mx.sos.sos.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mx.sos.sos.R;
import mx.sos.sos.util.CommonIntents;

public class GetServiceActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_SERVICE_ID = "EXTRA_SERVICE_ID";

    public static final int NOT_SET = -1;

    public static final int SERVICE_PLUMBER = 1;
    public static final int SERVICE_POWER = 2;
    public static final int SERVICE_HEALTH = 3;
    public static final int SERVICE_CAR = 4;

    private int mServiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* we get the reference of the service identifier */
        mServiceId = getServiceIdentifierFromExtra();
        int color  = getActionBarColor(mServiceId);
        setActionBarTitle(mServiceId);

        /* setting the action bar color */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        /* setting the colors to the collapsing toolbar layout */
        CollapsingToolbarLayout collapsingToolbarLayout = ( CollapsingToolbarLayout )
                findViewById( R.id.collapsing_service_layout );
        collapsingToolbarLayout.setContentScrimColor( color );
        collapsingToolbarLayout.setExpandedTitleColor( Color.WHITE );
        collapsingToolbarLayout.setCollapsedTitleTextColor( Color.WHITE );
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        getMenuInflater().inflate( R.menu.service_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem menuItem ){
        if( menuItem.getItemId() == R.id.nav_select_another_service_provider ){
            goToMoreServicesList( mServiceId );
            return true;
        }

        if( menuItem.getItemId() == android.R.id.home ) {
            finish();
            return true;
        }

        return true;
    }

    @Override
    public void onClick( View view ){

        Intent actionIntent;
        switch ( view.getId() ){
            case R.id.container_details_phone:
                //We turn on the dialer
                actionIntent = CommonIntents.makeDialIntent( GetServiceActivity.this, "9982935500");
                break;
            case R.id.container_details_location:
                actionIntent = CommonIntents.searchLocation( GetServiceActivity.this, "Calle 33 x 54 y 56, #438, Merida Yucatan");
                break;
            case R.id.container_details_email:
                actionIntent = CommonIntents.makeSendMailIntent(new String[]{ "mauricio9308@gmail.com"},
                        "Solicitu de información", "Eviando a través de SOS....");
                break;
            default:
                throw new IllegalArgumentException("Invalid view clicked....");
        }

        startActivity( actionIntent );
    }

    private int getServiceIdentifierFromExtra(){
        int service_id =  getIntent().getIntExtra(EXTRA_SERVICE_ID, NOT_SET);
        if( service_id == NOT_SET ){
            throw new IllegalStateException("Invalid service id given....");
        }

        return service_id;
    }

    private void setActionBarTitle( int service_id ){
        String title = "";
        switch ( service_id ){
            case SERVICE_CAR:
                title = getString( R.string.car_service );
                break;
            case SERVICE_HEALTH:
                title = getString(R.string.health_service );
                break;
            case SERVICE_PLUMBER:
                title = getString(R.string.plumber_service );
                break;
            case SERVICE_POWER:
                title = getString(R.string.power_service );
                break;
        }

        getSupportActionBar().setTitle( title );
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

    private void goToMoreServicesList( int service_id ){
        Intent goToMoreServicesList = new Intent( GetServiceActivity.this, MoreServicesActivity.class );
        goToMoreServicesList.putExtra( MoreServicesActivity.EXTRA_SERVICE_ID, service_id );
        startActivity( goToMoreServicesList );
    }
}

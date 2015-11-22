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
import android.widget.ImageView;
import android.widget.Toast;

import mx.sos.sos.R;
import mx.sos.sos.util.CommonIntents;
import mx.sos.sos.util.ParseHandler;

public class GetServiceActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_SERVICE_ID = "EXTRA_SERVICE_ID";

    public static final int NOT_SET = -1;

    public static final int SERVICE_PLUMBER = 1;
    public static final int SERVICE_POWER = 2;
    public static final int SERVICE_HEALTH = 3;
    public static final int SERVICE_CAR = 4;

    private ImageView mImageView;

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

        /* setting the image */
        mImageView = ( ImageView ) findViewById( R.id.mgv_details_image );
        setImage( mServiceId );

        /* setting the action bar color */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        /* setting the colors to the collapsing toolbar layout */
        CollapsingToolbarLayout collapsingToolbarLayout = ( CollapsingToolbarLayout )
                findViewById( R.id.collapsing_service_layout );
        collapsingToolbarLayout.setContentScrimColor(color);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        /* setting the listener for the layouts */
        findViewById( R.id.container_details_location ).setOnClickListener( GetServiceActivity.this /* OnClickListener */);
        findViewById( R.id.details_fab_done ).setOnClickListener( GetServiceActivity.this /* OnClickListener */);
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
        if( view.getId()  == R.id.details_fab_done ){

            ParseHandler.sendMessageRequestAssistance("Mauricio");

            Toast.makeText( GetServiceActivity.this,
                    "Se ha mandado la solicitud al prestador", Toast.LENGTH_SHORT ).show();

            /* we publish the review notification */
//            Notification notification = NotificationSender.buildNotification(GetServiceActivity.this, getString(R.string.app_name), "Es momento de calificar al prestador");
//            NotificationSender.scheduleNotification( GetServiceActivity.this, notification, 30000);

//            /* goes to main activity */
//            Intent goToMainActivity = new Intent( GetServiceActivity.this, MainActivity.class );
//            goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(goToMainActivity);
//            finish();

            return;
        }


        Intent actionIntent;
        switch ( view.getId() ){
            case R.id.container_details_location:
                actionIntent = CommonIntents.searchLocation( GetServiceActivity.this, "Calle 33 x 54 y 56, #438, Merida Yucatan");
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

    private void setImage( int service_id ){
        int resource = R.drawable.plumbers;
        switch ( service_id ){
            case SERVICE_CAR:
                resource = R.drawable.mecanico;
                break;
            case SERVICE_HEALTH:
                resource = R.drawable.medica_1;
                break;
            case SERVICE_PLUMBER:
                resource = R.drawable.plumbers;
                break;
           case SERVICE_POWER:
                resource = R.drawable.electricista;
                break;
        }

        mImageView.setImageResource( resource );
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

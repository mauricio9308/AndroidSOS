package mx.sos.sos;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class GetServiceActivity extends AppCompatActivity {

    public static final String EXTRA_SERVICE_ID = "EXTRA_SERVICE_ID";

    public static final int NOT_SET = -1;

    public static final int SERVICE_PLUMBER = 1;
    public static final int SERVICE_POWER = 2;
    public static final int SERVICE_HEALTH = 3;
    public static final int SERVICE_CAR = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* we get the reference of the service identifier */
        int service_id = getServiceIdentifierFromExtra();
        int color  = getActionBarColor(service_id);
        setActionBarTitle(service_id);

        /* setting the action bar color */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable( color ));
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        /* setting the colors to the collapsing toolbar layout */
        CollapsingToolbarLayout collapsingToolbarLayout = ( CollapsingToolbarLayout )
                findViewById( R.id.collapsing_service_layout );
        collapsingToolbarLayout.setContentScrimColor( color );
        collapsingToolbarLayout.setExpandedTitleColor( Color.WHITE );
        collapsingToolbarLayout.setCollapsedTitleTextColor( Color.WHITE );
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
                color = res.getColor( R.color.car );
                break;
            case SERVICE_HEALTH:
                color = res.getColor( R.color.health);
                break;
            case SERVICE_PLUMBER:
                color = res.getColor( R.color.plumber );
                break;
            case SERVICE_POWER:
                color = res.getColor( R.color.power );
                break;
        }

        return color;
    }
}

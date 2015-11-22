package mx.sos.sos.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mx.sos.sos.R;
import mx.sos.sos.util.ParseHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EMAIL = "EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyLoggedIn();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* setting the subtitle in base of the preferences */
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        toolbar.setSubtitle(pref.getString(EMAIL, ""));

        /* setting up the listeners for the buttons in the main */
        findViewById(R.id.btn_car_emergency).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_health_emergency).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_plumber_emergency).setOnClickListener(MainActivity.this);
        findViewById(R.id.btn_power_emergency).setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        goToServiceActivity(view.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_action_close_session) {
            //Un subscribe parse
            ParseHandler.unsubscribeAdmin();
            ParseHandler.unsubscribeUser();

            /* removing the email preference*/
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            pref.edit().putString(EMAIL, null).apply();

            //We redirect to the login activity
            Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
            goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goToLogin);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Redirects to the service activity to be used
     */
    private void goToServiceActivity(int service_id) {
        Intent goToServicesList = new Intent(MainActivity.this, GetServiceActivity.class);

        switch (service_id) {
            case R.id.btn_car_emergency:
                goToServicesList.putExtra(GetServiceActivity.EXTRA_SERVICE_ID, GetServiceActivity.SERVICE_CAR);
                break;
            case R.id.btn_health_emergency:
                goToServicesList.putExtra(GetServiceActivity.EXTRA_SERVICE_ID, GetServiceActivity.SERVICE_HEALTH);
                break;
            case R.id.btn_plumber_emergency:
                goToServicesList.putExtra(GetServiceActivity.EXTRA_SERVICE_ID, GetServiceActivity.SERVICE_PLUMBER);
                break;
            case R.id.btn_power_emergency:
                goToServicesList.putExtra(GetServiceActivity.EXTRA_SERVICE_ID, GetServiceActivity.SERVICE_POWER);
                break;
        }

        startActivity(goToServicesList);
    }

    public void verifyLoggedIn() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (pref.getString(EMAIL, null) == null) {
            Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
            goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goToLogin);
            finish();
        }
    }

}

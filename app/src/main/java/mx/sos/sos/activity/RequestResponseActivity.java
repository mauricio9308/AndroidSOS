package mx.sos.sos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mx.sos.sos.fragment.RequestResponseDialogFragment;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class RequestResponseActivity extends AppCompatActivity{

    public static final String EXTRA_RESPOSNE =  "EXTRA_RESPOSNE";
    public static final String EXTRA_RESPONSE_MESSAGE = "EXTRA_RESPONSE_MESSAGE";


    private static final String FRAG_TAG_REQUEST_RESPONSE_FRAGMENT
            = "FRAG_TAG_REQUEST_RESPONSE_FRAGMENT";

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        if (savedInstanceState == null) {
            /* let's show the notification dialog */
            RequestResponseDialogFragment.newInstance( getExtraResponseMessage(), getExtraResposne() )
                    .show( getSupportFragmentManager(), FRAG_TAG_REQUEST_RESPONSE_FRAGMENT );
        }
    }

    public String getExtraResponseMessage(){
        return getIntent().getStringExtra( EXTRA_RESPONSE_MESSAGE );
    }

    public boolean getExtraResposne(){
        return getIntent().getBooleanExtra( EXTRA_RESPOSNE, false );
    }

    private void goToMainActivity(){
        Intent intent = new Intent( RequestResponseActivity.this /* context */, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // call this to finish the current activity
    }
}

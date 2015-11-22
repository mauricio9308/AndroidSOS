package mx.sos.sos.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mx.sos.sos.fragment.RequestDialogFragment;
import mx.sos.sos.util.ParseHandler;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class RequestActivity extends AppCompatActivity implements RequestDialogFragment.RequestDialogCallback{

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    private static final String FRAG_TAG_RESPONSE_FRAGMENT = "FRAG_TAG_RESPONSE_FRAGMENT";

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        if (savedInstanceState == null) {
            /* let's show the notification dialog */
            RequestDialogFragment.newInstance( getExtraMessage() )
                    .show( getSupportFragmentManager(), FRAG_TAG_RESPONSE_FRAGMENT );
        }
    }

    public String getExtraMessage() {
        return getIntent().getStringExtra( EXTRA_MESSAGE );
    }

    @Override
    public void onRequestResolved(String message, boolean accepted) {
        /* we send the message */
        ParseHandler.sendMessageRespondRequest( message, accepted );

        finish();
    }
}

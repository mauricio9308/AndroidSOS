package mx.sos.sos.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import mx.sos.sos.R;
import mx.sos.sos.util.CommonIntents;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class RequestResponseDialogFragment extends AppCompatDialogFragment implements View.OnClickListener{

    private static final String FRAG_ARG_MESSAGE = "FRAG_ARG_MESSAGE";
    private static final String FRAG_ARG_WAS_SUCCESS = "FRAG_ARG_WAS_SUCCESS";

    public static RequestResponseDialogFragment newInstance( String message, boolean wasSuccess ){
        Bundle args = new Bundle(2);
        args.putString(FRAG_ARG_MESSAGE, message);
        args.putBoolean(FRAG_ARG_WAS_SUCCESS, wasSuccess);

        RequestResponseDialogFragment requestResponseDialogFragment = new RequestResponseDialogFragment();
        requestResponseDialogFragment.setArguments(args);
        return requestResponseDialogFragment;
    }


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from( getContext() ).inflate( R.layout.dialog_request_response, null, false );

         /* setting the reference for the text view */
        TextView comments = (TextView) view.findViewById( R.id.txt_request_response );
        comments.setText(getFragArgMessage());

        TextView wasSuccessful = (TextView) view.findViewById( R.id.txt_request_response_was_accepted );
        if( getFragArgWasSuccessful() ){
            wasSuccessful.setTextColor( getResources().getColor( R.color.plumber ));
            wasSuccessful.setText( "La petición fue aceptada");
        }else{
            wasSuccessful.setTextColor( getResources().getColor( R.color.health ));
            wasSuccessful.setText( "La petición fue denegada");
        }

        view.findViewById( R.id.btn_petition_consumed ).setOnClickListener(this);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        /* the dialog is not cancelable */
        dialog.setCancelable(false);
        return dialog;
    }


    @Override
    public void onClick( View view ){
        switch( view.getId() ){
            case R.id.btn_petition_consumed:
                dismiss();
                getActivity().finish();
                break;
            case R.id.container_details_phone:
                //We turn on the dialer
                Intent phoneIntent = CommonIntents.makeDialIntent( getActivity(), "9999047127");
                getActivity().startActivity( phoneIntent );
                break;
            case R.id.container_details_email:
                Intent emailIntent = CommonIntents.makeSendMailIntent(new String[]{ "torrespjgt@gmail.com"},
                        "Solicitud de información", "Eviando a través de SOS....");
                getActivity().startActivity( emailIntent );
                break;
        }
    }


    public String getFragArgMessage(){
        return getArguments().getString( FRAG_ARG_MESSAGE );
    }

    public boolean getFragArgWasSuccessful(){
        return getArguments().getBoolean( FRAG_ARG_WAS_SUCCESS );
    }
}

package mx.sos.sos.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import mx.sos.sos.R;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class RequestDialogFragment extends AppCompatDialogFragment implements View.OnClickListener{

    private static final String FRAG_ARG_MESSAGE = "FRAG_ARG_MESSAGE";

    private RequestDialogCallback mCallback;
    private EditText mEditText;

    public static RequestDialogFragment newInstance( String message ){
        Bundle args = new Bundle(1);
        args.putString(FRAG_ARG_MESSAGE, message);

        RequestDialogFragment requestResponseDialogFragment = new RequestDialogFragment();
        requestResponseDialogFragment.setArguments(args);
        return requestResponseDialogFragment;
    }

    @Override
    public void onAttach( Activity activity ){
        super.onAttach(activity);

        try{
            mCallback = ( RequestDialogCallback ) activity;
        }catch( ClassCastException e ){
            throw new IllegalStateException("Activtity must implement " + RequestDialogCallback.class.getSimpleName() );
        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from( getContext() ).inflate( R.layout.dialog_request, null, false );

        view.findViewById( R.id.btn_deny_petition).setOnClickListener(this);
        view.findViewById( R.id.btn_accept_petition).setOnClickListener(this);

        /* setting the reference for the text view */
        mEditText = ( EditText ) view.findViewById( R.id.comments );

        TextView textRequest = ( TextView ) view.findViewById( R.id.txt_request_response );
        textRequest.setText("Nombre: Mauricio Lara\nLocalización: UTM Mérida\nMensaje: " + getFragArgMessage() );
        textRequest.setTextColor(Color.BLACK);

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
        if( mCallback != null ){
            boolean wasAccepted = ( view.getId() == R.id.btn_accept_petition );
            mCallback.onRequestResolved( mEditText.getText().toString(), wasAccepted );
            dismiss();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallback = null;
    }


    public String getFragArgMessage(){
        return getArguments().getString( FRAG_ARG_MESSAGE );
    }

    public interface RequestDialogCallback{
        void onRequestResolved( String message, boolean accepted );
    }

}

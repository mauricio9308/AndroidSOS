package mx.sos.sos.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import mx.sos.sos.R;

/**
 * Created by mauriciolara on 11/22/15.
 */
public class SendRequestFragment extends AppCompatDialogFragment implements View.OnClickListener{

    private SendRequestDialogCallback mCallback;
    private EditText mEditText;

    public static SendRequestFragment newInstance(){
        SendRequestFragment requestResponseDialogFragment = new SendRequestFragment();
        return requestResponseDialogFragment;
    }

    @Override
    public void onAttach( Activity activity ){
        super.onAttach(activity);

        try{
            mCallback = ( SendRequestDialogCallback ) activity;
        }catch( ClassCastException e ){
            throw new IllegalStateException("Activtity must implement " + SendRequestDialogCallback.class.getSimpleName() );
        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate( R.layout.dialog_send_request, null, false );

        view.findViewById( R.id.send_petition).setOnClickListener(this);

        /* setting the reference for the text view */
        mEditText = (EditText) view.findViewById( R.id.comments );

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        /* the dialog is not cancelable */
        dialog.setCancelable( true );
        return dialog;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick( View view ){
        if( mCallback != null ){
            mCallback.sendMessage( mEditText.getText().toString() );
            dismiss();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallback = null;
    }

    public interface SendRequestDialogCallback{
        void sendMessage( String message );
    }

}

package mx.sos.sos.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by mauriciolara on 11/21/15.
 */
public class CommonIntents {

    public static Intent makeDialIntent( Activity launchingActivity, String phone ){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(phone.trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return callIntent;
    }

    public static Intent searchLocation( Activity launchingActivity, String address ){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }

    public static Intent makeSendMailIntent(String[] mails, String subject, String message) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(Intent.EXTRA_EMAIL, mails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        return intent;
    }




}

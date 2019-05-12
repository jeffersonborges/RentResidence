package br.com.jborges.rentresidence.Firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import static android.content.ContentValues.TAG;

/**
 * Jefferson Borges - 2019
 */

public class CDCInstanceIDService extends FirebaseInstanceIdService {

    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token da App", token);
    }

    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }

}

package br.com.jborges.rentresidence.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class CDCInstanceIDService extends FirebaseInstanceIdService {

    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token da App", token);
    }
}

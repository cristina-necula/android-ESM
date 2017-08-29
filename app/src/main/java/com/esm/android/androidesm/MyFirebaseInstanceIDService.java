package com.esm.android.androidesm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import tracker.EsmTracker;

/**
 * Created by Cristina on 8/27/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        EsmTracker.getInstance().onTokenRefresh(refreshedToken);


    }
}

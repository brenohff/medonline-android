package com.brenohff.medonline.FirebaseConfig;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConfig {

    private static FirebaseAuth mAuth;

    public static FirebaseAuth getmAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }
}

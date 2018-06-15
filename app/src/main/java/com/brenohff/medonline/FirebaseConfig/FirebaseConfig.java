package com.brenohff.medonline.FirebaseConfig;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseConfig {

    private static FirebaseAuth mAuth;
    private static FirebaseStorage storage;

    public static FirebaseAuth getmAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static FirebaseStorage getStorage() {
        if (storage == null)
            storage = FirebaseStorage.getInstance();

        return storage;
    }
}

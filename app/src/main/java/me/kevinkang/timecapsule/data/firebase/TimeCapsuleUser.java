package me.kevinkang.timecapsule.data.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Work on 10/15/2016.
 */

public class TimeCapsuleUser {
    private static final String DEFAULT_ID = "0d5508b7-9131-4115-a9be-3878febb38fe";
    private static final String TAG = "TimeCapsuleUser";
    private String id = null;
    private String name = null;
    private List<FirebaseCapsule> capsules;
    private DatabaseReference mDatabase;

    public TimeCapsuleUser() {
        capsules = new ArrayList<FirebaseCapsule>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        id = DEFAULT_ID;
        /*
        if (user != null) {
            id = user.getUid();
        } else {
            // No user is signed in, use default
            id = DEFAULT_ID;
        } */
        Log.d(TAG, "message");
        // get the user's name, replace with default if nobody is signed in
        name = user.getDisplayName();
        if (name == null)
            name = "dubs";

        // add user to firebase if they are new
        if (mDatabase.child("users").child(id) == null) {
            mDatabase.child("users").child(id).setValue(id);
            mDatabase.child("users").child(id).child("name").setValue(name);
        }

        // get existing capsules if the user exists
        else {
            DatabaseReference test = mDatabase.child("users").child(id).child("capsules");
            test.addChildEventListener(childEventListener);
           // capsules = FirebaseCapsule.getCapsules();

        }
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, dataSnapshot.getKey());
            capsules.add(new FirebaseCapsule((dataSnapshot.getKey())));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}

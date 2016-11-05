package me.kevinkang.timecapsule.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.kevinkang.timecapsule.data.models.Capsule;
import me.kevinkang.timecapsule.data.models.User;

/**
 * Created by Work on 10/15/2016.
 */

public class TimeCapsuleUser extends User{
    private static final String LOG_TAG = TimeCapsuleUser.class.getSimpleName();
    private String id, name;
    private List<Capsule> capsules;
    private DatabaseReference database;

    private static TimeCapsuleUser currentUser;

    public static TimeCapsuleUser getCurrentUser(){
        if(currentUser == null)
            currentUser = new TimeCapsuleUser();
        return currentUser;
    }

    public TimeCapsuleUser() {
        capsules = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();
        name = user.getDisplayName();

        currentUser = this;

        // Retrieve the user's data
        database.child("users").child(id).addListenerForSingleValueEvent(firstLoadListener);
    }

    ValueEventListener firstLoadListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            loadData(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void loadData(DataSnapshot snapshot){
        if(!snapshot.exists()){
            // if the user entry does not exist then write it to firebase
            writeUserToFirebase();
        } else {
            // the user does exist. Load up all of the data.
            this.name = (String) snapshot.child("name").getValue();

            // If the user has any time capsules, load them
            if(snapshot.child("capsules").exists()) {
                for (DataSnapshot capsule : snapshot.child("capsules").getChildren()){
                    capsules.add(new FirebaseCapsule(capsule.getKey()));
                }
            }

        }
    }

    /**
     * Output the current FirebaseAuth user to TimeCapsule's user database
     */
    private void writeUserToFirebase(){
        database.child("users").child(id).child("name").setValue(name);
    }

    /**
     * Add a capsule to both this local TimeCapsuleUser instance and the TimeCapsule Firebase database
     * @param capsule capsule that will be inserted
     */
    @Override
    public void addCapsule(Capsule capsule) {
        this.capsules.add(capsule);

        database.child("users").child(id).child("capsules").child(capsule.getUUID().toString()).setValue(true);
    }

    @Override
    public List<Capsule> getCapsules() {
        return capsules;
    }
}

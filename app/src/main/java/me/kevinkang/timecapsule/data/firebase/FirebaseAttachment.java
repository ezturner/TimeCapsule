package me.kevinkang.timecapsule.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.UUID;

import me.kevinkang.timecapsule.data.models.Capsule;

/**
 * Created by Work on 10/16/2016.
 */

public class FirebaseAttachment {


    private String type;
    private String extension;
    private String fileId;
    private UUID uuid;
    private File file;
    private Capsule capsule;

    public FirebaseAttachment(String type, File file){
        this.type = type;
        this.file = file;
        uuid = UUID.randomUUID();
    }

    public void addCapsule(Capsule capsule){
        this.capsule = capsule;
    }

    public void updateFirebase(){


    }

    private void uploadFile(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://time-capsule-f5de2.appspot.com");
        try {
            String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String folder = "users/" + userUID + "/" + capsule.getUUID().toString() + "/";
                    // Create a reference to "mountains.jpg"
            StorageReference file = storageRef.child(folder + uuid.toString());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public UUID getUUID(){
        return uuid;
    }
}

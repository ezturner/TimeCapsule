package me.kevinkang.timecapsule.data.firebase;

import java.io.File;

/**
 * Created by Work on 10/16/2016.
 */

public class FirebaseAttachment {


    private String type;
    private String extension;
    private File file;

    public FirebaseAttachment(String type, File file){
        this.type = type;
        this.file = file;
    }
}

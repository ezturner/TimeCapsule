package me.kevinkang.timecapsule.data.models;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Work on 10/15/2016.
 */

public abstract class Attachment {

    public abstract String getType();
    public abstract String getExtension();

    public abstract String getId();

    public abstract void update(DataSnapshot recipient);

    public abstract void save();

    public abstract void delete();
}

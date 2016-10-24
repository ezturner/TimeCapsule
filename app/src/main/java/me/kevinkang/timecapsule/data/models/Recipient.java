package me.kevinkang.timecapsule.data.models;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Work on 10/15/2016.
 */

public abstract class Recipient {

    public abstract String getName();

    public abstract String getDestination();

    // indicates how to send the message
    public abstract String getType();


    public abstract void update(DataSnapshot recipient);

    public abstract String getId();

    public abstract void save();

    public abstract void delete();
}
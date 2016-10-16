package me.kevinkang.timecapsule.data.models;

/**
 * Created by Work on 10/15/2016.
 */

public abstract class Recipient {

    public abstract String getName();

    public abstract String getDestination();

    // indicates how to send the message
    public abstract String getType();
}
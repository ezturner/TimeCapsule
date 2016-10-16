package me.kevinkang.timecapsule.data.models;

/**
 * Created by Work on 10/15/2016.
 */

public interface Recipient {

    String getName();

    String getDestination();

    // indicates how to send the message
    String getType();
}
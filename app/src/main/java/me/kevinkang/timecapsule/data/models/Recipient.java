package me.kevinkang.timecapsule.data.models;

/**
 * Created by Work on 10/15/2016.
 */

public interface Recipient {

    int TEXT = 24;
    int EMAIL = 48;

    String getPhoneNumber();

    String getEmail();

    String getName();

    int getType();
}
package me.kevinkang.timecapsule.data.models;

import java.util.UUID;

/**
 * Created by Work on 10/15/2016.
 */

public abstract class Attachment {

    public abstract String getType();
    public abstract String getExtension();
    public abstract UUID getUUID();
}

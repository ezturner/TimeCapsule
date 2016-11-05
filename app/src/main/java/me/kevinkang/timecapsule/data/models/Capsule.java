package me.kevinkang.timecapsule.data.models;

import java.security.Identity;
import java.util.List;
import java.util.UUID;

/**
 * Created by Work on 10/15/2016.
 */

public abstract class Capsule {

    /**
     * Returns the name of the capsule
     * @return capsule
     */
    public abstract String getName();

    /**
     * Returns the message of the capsule
     * @return message
     */
    public abstract String getMessage();

    /**
     * Returns the recipients of this Time Capsule
     * @return recipients
     */
    public abstract List<Recipient> getRecipients();

    /**
     * Returns the list of attached Attachments of the Time Capsule
     * @return attachments
     */
    public abstract List<Attachment> getAttachments();

    /**
     * Returns the date that the Capsule was created
     * @return creationDate
     */
    public abstract long getCreationDate();

    /**
     * Returns the date that the capsule will be delivered at
     * @return openDate
     */
    public abstract long getOpenDate();

    public abstract UUID getUUID();
}

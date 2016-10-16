package me.kevinkang.timecapsule.data.models;

import java.util.List;

/**
 * Created by Work on 10/15/2016.
 */

public interface Capsule {

    /**
     * Returns the name of the capsule
     * @return capsule
     */
    String getName ();

    /**
     * Returns the message of the capsule
     * @return message
     */
    String getMessage();

    /**
     * Returns the recipients of this Time Capsule
     * @return recipients
     */
    List<Recipient> getRecipients();

    /**
     * Returns the list of attached Attachments of the Time Capsule
     * @return attachments
     */
    List<Attachment> getAttachments();

    /**
     * Returns the date that the Capsule was created
     * @return creationDate
     */
    long getCreationDate();

    /**
     * Returns the date that the capsule will be delivered at
     * @return openDate
     */
    long getOpenDate();

}

package me.kevinkang.timecapsule.data.firebase;

import java.util.ArrayList;
import java.util.List;

import me.kevinkang.timecapsule.data.models.Attachment;
import me.kevinkang.timecapsule.data.models.Capsule;
import me.kevinkang.timecapsule.data.models.Recipient;

/**
 * Created by Steven on 10/15/2016.
 */

public class FirebaseCapsule extends Capsule implements Comparable<FirebaseCapsule>  {
    private String name;
    private String message;
    private long creationDate;
    private long openDate;
    private List<Recipient> recipients;
    private List<Attachment> attachments;


    /**
     * Constructs a new FirebaseCapsule with an empty message and attachment list
     * @param name The capsule's name
     * @param openDate The date the capsule will open on
     * @param recipients The list of people who will receive the capsule
     */
    public FirebaseCapsule(String name, long openDate, List<Recipient> recipients) {
        if (name == null ||openDate <= System.currentTimeMillis() || recipients == null) {
            throw new IllegalArgumentException("name and recipients cannot be null," +
                    " openDate must be a valid time in the future");
        }
        this.name = name;
        this.message = "";
        this.openDate = openDate;
        this.creationDate = System.currentTimeMillis();
        this.recipients = recipients;
        attachments = new ArrayList<Attachment>();
    }

    /**
     * Constructs a new FirebaseCapsule with a message and an empty attachment list
     * @param name The capsule's name
     * @param openDate The date the capsule will open on
     * @param recipients The list of people who will receive the capsule
     * @param message The capsule's message
     */
    public FirebaseCapsule(String name, long openDate, List<Recipient> recipients, String message) {
        this(name, openDate, recipients);
        if (message == null)
            throw new IllegalArgumentException("message cannot be null");
        this.message = message;
    }

    /**
     * Constructs a new FirebaseCapsule with attachments and an empty message
     * @param name The capsule's name
     * @param openDate The date the capsule will open on
     * @param recipients The list of people who will receive the capsule
     * @param attachments The files to be attached to this capsule
     */
    public FirebaseCapsule(String name, long openDate, List<Recipient> recipients,
                           List<Attachment> attachments) {
        this(name, openDate, recipients);
        if (attachments == null)
            throw new IllegalArgumentException("attachments cannot be null");
        this.attachments.addAll(attachments);
    }

    /**
     * Constructs a new FirebaseCapsule with attachments and a message
     * @param name The capsule's name
     * @param openDate The date the capsule will open on
     * @param recipients The list of people who will receive the capsule
     * @param attachments The files to be attached to this capsule
     * @param message The capsule's message
     */
    public FirebaseCapsule(String name, long openDate, List<Recipient> recipients, String message,
                           List<Attachment> attachments) {
        this(name, openDate, recipients);
        if (attachments == null || message == null) {
            throw new IllegalArgumentException("attachments and message cannot be null");
        }
        this.message = message;
        this.attachments.addAll(attachments);
    }

    /**
     * @return The capsule's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The capsule's message text
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return The capsule's recipients
     */
    public List<Recipient> getRecipients() {
        return this.recipients;
    }

    /**
     * @return The capsule's attachments
     */
    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    /**
     * @return The capsule's creation date
     */
    public long getCreationDate() {
        return this.creationDate;
    }

    /**
     * @return The capsule's opening date
     */
    public long getOpenDate() {
        return this.openDate;
    }

    /**
     * Standard compareTo method
     * @param other The FirebaseCapsule to compare to
     * @return a positive integer if this is opening sooner than other, negative otherwise
     */
    public int compareTo(FirebaseCapsule other) {
        return (int) (other.getOpenDate() - this.openDate);
    }
}

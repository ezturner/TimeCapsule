package me.kevinkang.timecapsule.data.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.kevinkang.timecapsule.data.models.Attachment;
import me.kevinkang.timecapsule.data.models.Capsule;
import me.kevinkang.timecapsule.data.models.Recipient;

/**
 * Created by Steven on 10/15/2016.
 */

public class FirebaseCapsule extends Capsule implements Comparable<FirebaseCapsule>  {
    private static final String LOG_TAG = FirebaseCapsule.class.getSimpleName();
    private UUID id = null;
    private String name;
    private String message;
    private long creationDate;
    private long openDate;
    private List<Recipient> recipients;
    private List<Attachment> attachments;
    private boolean isHidden;
    private DatabaseReference mDatabase;

    /**
     * Constructs a new FirebaseCapsule with an empty message and attachment list
     * @param name The capsule's name
     * @param openDate The date the capsule will open on
     * @param recipients The list of people who will receive the capsule
     */
    private FirebaseCapsule(String name, long openDate, List<Recipient> recipients) {
        if (name == null ||openDate <= System.currentTimeMillis() || recipients == null) {
            throw new IllegalArgumentException("name and recipients cannot be null," +
                    " openDate must be a valid time in the future");
        }
        this.id = UUID.randomUUID();
        this.name = name;
        this.message = "";
        this.openDate = openDate;
        this.creationDate = System.currentTimeMillis();
        this.recipients = recipients;
        attachments = new ArrayList<>();
        isHidden = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    public FirebaseCapsule(String key) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("capsules");

        // get capsule with matching key
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                update(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void update(DataSnapshot data) {
        try {
            if(data.child("name").exists())
                this.name = (String) data.child("name").getValue();

            if(data.child("message").exists())
                this.message = (String) data.child("message").getValue();

            if(data.child("openDate").exists())
                this.openDate = (long) data.child("openDate").getValue();

            updateRecipients(data);
            updateAttachments(data);
        } catch (ClassCastException e){
            e.printStackTrace();
            Log.d(LOG_TAG, "The firebase backend's data model has a different type than this version!");
        }
    }

    /**
     * Updates the local copy of the recipients from the firebase update
     * @param data
     */
    private void updateRecipients(DataSnapshot data){
        if(!data.child("recipients").exists())
            return;

        for(DataSnapshot recipient : data.child("recipients").getChildren()){
            String recipientId = recipient.getKey();
            for(Recipient localRecipient: this.recipients){
                if (localRecipient.getId().equals(recipientId)) {
                    localRecipient.update(recipient);
                }
            }
        }
    }

    /**
     * Updates the local copy of the attachmetns from the firebase update
     * @param data
     */
    private void updateAttachments(DataSnapshot data){
        if(!data.child("attachments").exists())
            return;

        for(DataSnapshot recipient : data.child("attachments").getChildren()){
            String attachmentId = recipient.getKey();
            for(Attachment localAttachment: this.attachments){
                if (localAttachment.getId().equals(attachmentId)) {
                    localAttachment.update(recipient);
                }
            }
        }
    }

    /**
     * Saves this FirebaseCapsule to the backend.
     */
    public void save(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Retrieve the DatabaseReference for this capsule
        DatabaseReference ref = database.getReference("capsules").child(id.toString());

        ref.child("name").setValue(this.getName());
        ref.child("message").setValue(this.getMessage());
        ref.child("openDate").setValue(this.getOpenDate());
        for(Recipient recipient: this.getRecipients()){
            recipient.save();
        }

        for(Attachment attachment : this.getAttachments()){
            attachment.save();
        }

    }

    public boolean isHidden(){
        return isHidden;
    }

    /**
     * Flags the capsule as isHidden
     */
    public void setHidden() {
        isHidden = true;
    }

    /**
     * Flags the capsule as visible (this is the default setting)
     */
    public void setVisible() {
        isHidden = false;
    }

    public void setMessage(String message) {
        if (message == null)
            throw new IllegalArgumentException("message cannot be null");
        this.message = message;
    }

    public void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null");
        this.name = name;
    }

    public void setRecipients(List<Recipient> recipients) {
        if (recipients == null || recipients.size() == 0)
            throw new IllegalArgumentException("recipients muist be a valid list");
        this.recipients = recipients;
    }

    public void addRecipients(Recipient r) {
        if (r == null)
            throw new IllegalArgumentException("cannot add null recipient");
        recipients.add(r);
    }

    public void removeRecipient(Recipient r){
        if(r == null)
            throw new IllegalArgumentException("Cannot remove a null recipient");
        recipients.remove(r);
        r.delete();
    }

    public void setAttachments(List<Attachment> attachments) {
        if (attachments == null || attachments.size() == 0)
            throw new IllegalArgumentException("attachments must be a valid list");
        this.attachments = attachments;
    }

    public void addAttachments(Attachment a) {
        if (a == null)
            throw new IllegalArgumentException("cannot add null attachment");
        this.attachments.add(a);
    }

    public void removeAttachment(Attachment a){
        if(a == null)
            throw new IllegalArgumentException("Cannot remove a null recipient");
        attachments.remove(a);
        a.delete();
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

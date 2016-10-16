package me.kevinkang.timecapsule.data.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
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
    private UUID id = null;
    private String name;
    private String message;
    private long creationDate;
    private long openDate;
    private List<Recipient> recipients;
    private List<Attachment> attachments;
    private boolean hidden;
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
        attachments = new ArrayList<Attachment>();
        hidden = false;
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

    public FirebaseCapsule(final String key) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("capsules");

        // get capsule with matching key
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                String searchKey = key.replaceAll("\"", "").trim();

                for(DataSnapshot d: data.getChildren()) {

                    if (searchKey.equals(d.getKey().toString())) {
                        Log.d("Success!", d.child("message").toString());

                        id = UUID.fromString(searchKey);
                        name = d.child("name").getValue().toString();
                        message = d.child("message").getValue().toString();
                        openDate = Long.parseLong(d.child("date_to_open").getValue().toString());
                        creationDate = Long.parseLong(d.child("date_created").getValue().toString());
                        recipients = new ArrayList<Recipient>();
                        for (DataSnapshot r: d.child("recipients").getChildren()) {
                            //recipients.add()
                            recipients.add(new FirebaseCapsuleRecipient(r.getValue().toString()));
                        }
                    } else {
                        Log.d("FB Capsule", d.getKey().toString() + " " + searchKey);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    ChildEventListener childListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("FB Capsule", dataSnapshot.getKey());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ChildEventListener recipientListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("FB capsule", dataSnapshot.getValue().toString());
            recipients.add(new FirebaseCapsuleRecipient(dataSnapshot.getKey()));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    /**
     * Flags the capsule as hidden
     */
    public void setHidden() {
        hidden = true;
    }

    /**
     * Flags the capsule as visible (this is the default setting)
     */
    public void setVisible() {
        hidden = false;
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

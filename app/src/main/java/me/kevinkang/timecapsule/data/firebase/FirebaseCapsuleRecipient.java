package me.kevinkang.timecapsule.data.firebase;

import android.telephony.PhoneNumberUtils;

import me.kevinkang.timecapsule.data.models.Recipient;

/**
 * Created by Steven Austin on 10/15/2016.
 */

public class FirebaseCapsuleRecipient extends Recipient {
    public static final String EMAIL = "email", PHONE = "phone";

    private String email = null;
    private String name = null;
    private int phone = 0;
    private String type = null; // the notification method

    /**
     * Construct a new Recipient with an email address
     * @param name The Recipient's name
     * @param email The Recipient's email address
     */
    public FirebaseCapsuleRecipient(String name, String email) {
        if (name == null || email == null) {
            throw new IllegalArgumentException("name and email must not be null");
        }
        this.email = email;
        this.name = name;
        type = EMAIL;
    }

    /**
     * Construct a new Recipient with a phoen number
     * @param name The Recipient's name
     * @param phone The Recipient's phone number
     */
    public FirebaseCapsuleRecipient(String name, int phone) {
        if (name == null || phone < 0) {
            throw new IllegalArgumentException("name must not be null" +
                    " and phone number must be valid");
        }
        this.name = name;
        this.phone = phone;
        type = PHONE;
    }

    /**
     * Returns the recipient's name
     * @return the recipient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the "Destination" of the recipient
     * @return the destination as a string
     */
    public String getDestination() {
        if (type.equals(EMAIL))
            return email;

        if (type.equals(PHONE))
            return Integer.toString(phone);

        throw new IllegalStateException("recipient type has not been set");
    }

    /**
     * Returns the type of the destination
     * @return type: The method used to send the capsule (email, phone, etc.)
     */
    public String getType() {
        return type;
    }

    /**
     * Indicates whether email is a valid email address
     * @param email The email address to validate
     * @return true if email is a valid email address, false otherwise
     */
    public boolean isValidEmail(String email) {
        // address must contain exactly 1 @
        if (email.indexOf("@") == -1 || email.lastIndexOf("@") != email.indexOf("@"))
            return false;

        //@ must not be the first or last character of the address
        if (email.indexOf("@") == 0 || email.indexOf("@") == email.length() - 1)
            return false;

        return true;
    }

    /**
     * Indicates whether the phone number is valid
     * @param phoneNumber The phone number to be validated
     * @return true if the phone number is valid, false otherwise
     */
    public boolean isValidPhoneNumber(int phoneNumber) {
        return PhoneNumberUtils.isGlobalPhoneNumber(Integer.toString(phoneNumber));
    }
}

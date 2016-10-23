package me.kevinkang.timecapsule.data.models;

import java.util.List;
import java.util.Set;

import me.kevinkang.timecapsule.data.firebase.FirebaseCapsule;

/**
 * Created by Work on 10/15/2016.
 */

public abstract class User {
    /**
     * Adds capsule to an ordered Set
     * @param capsule capsule that will be inserted
     */
    public abstract void addCapsules(FirebaseCapsule capsule);

    /**
     * Returns a user's capsules
     * @return returns an ordered List of Capsules.
     */
    public abstract List<Capsule> getCapsules();

}

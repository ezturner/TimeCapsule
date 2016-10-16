package me.kevinkang.timecapsule.data.models;

import java.util.Set;

import me.kevinkang.timecapsule.data.firebase.FirebaseCapsule;

/**
 * Created by Work on 10/15/2016.
 */

public interface User {
    /**
     * Adds capsule to an ordered Set
     * @param capsule capsule that will be inserted
     */
    void addCapsules(FirebaseCapsule capsule);

    /**
     * Gets capsule to an ordered Set
     * @return returns an ordered Set of Capsules.
     */
    Set<FirebaseCapsule> getCapsules();

}

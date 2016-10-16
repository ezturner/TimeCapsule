package me.kevinkang.timecapsule.data.firebase;

import java.util.Set;
import java.util.TreeSet;

import me.kevinkang.timecapsule.data.models.User;

/**
 * Created by Work on 10/15/2016.
 */

public class FbUser implements User {

    private String name;
    private TreeSet<FirebaseCapsule> capsules;

    public FbUser() {
        this.capsules = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addCapsules(FirebaseCapsule capsule) {
        capsules.add(capsule);
    }

    @Override
    public Set<FirebaseCapsule> getCapsules() {
        return capsules;
    }
}

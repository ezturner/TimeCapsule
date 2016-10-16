package me.kevinkang.timecapsule.data.mock;

import java.util.List;

import me.kevinkang.timecapsule.data.models.Attachment;
import me.kevinkang.timecapsule.data.models.Capsule;
import me.kevinkang.timecapsule.data.models.Recipient;

/**
 * Created by Work on 10/15/2016.
 */

public class MockCapsule implements Capsule {

    @Override
    public String getName() {
        return "Sarah";
    }

    @Override
    public String getMessage() {
        return "Code 4 lyfe";
    }

    @Override
    public List<Recipient> getRecipients() {
        return null;
    }

    @Override
    public List<Attachment> getAttachments() {
        return null;
    }

    @Override
    public long getCreationDate() {
        return 0;
    }

    @Override
    public long getOpenDate() {
        return 0;
    }
}

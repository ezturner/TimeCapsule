package me.kevinkang.timecapsule.data;

import me.kevinkang.timecapsule.data.firebase.TimeCapsuleUser;
import me.kevinkang.timecapsule.data.models.User;

/**
 * Created by Ethan on 10/23/2016.
 */

public class CurrentUser {

    public static User getInstance(){
        return TimeCapsuleUser.getCurrentUser();
    }
}

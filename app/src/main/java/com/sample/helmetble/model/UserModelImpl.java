package com.sample.helmetble.model;

import android.content.Context;

import com.sample.helmetble.model.vo.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserModelImpl implements UserModel {

    UserStore userStore;

    public UserModelImpl(Context context) {
        userStore = new UserStore(context);
    }

    @Override
    public void addUser() {
        int nextId = userStore.getNextId();
        User user = userStore.createUser(nextId);
        userStore.addUser(user);
    }

    @Override
    public ArrayList<User> getUsers() {
        return sortUser(userStore.getUsers());
    }

    private ArrayList<User> sortUser(ArrayList<User> users) {
        if(users.size() >= 2) {
            Collections.sort(users, new UserCompare());
        }
        return users;
    }

    private class UserCompare implements Comparator<User> {

        @Override
        public int compare(User o1, User o2) {
            return o1.getId() < o2.getId() ? -1 : o1.getId() > o2.getId() ? 1:0;
        }
    }

}

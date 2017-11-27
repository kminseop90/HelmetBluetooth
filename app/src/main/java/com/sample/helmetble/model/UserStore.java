package com.sample.helmetble.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sample.helmetble.model.vo.User;

import java.util.ArrayList;
import java.util.Map;

public class UserStore {

    private static final String PREF_NAME = "user_pref";
    private SharedPreferences pref;

    public UserStore(Context context) {
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private void increaseId() {
        int id =  pref.getInt("id", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id", ++id);
        editor.apply();
    }

    private int getId() {
        return pref.getInt("id", 0);
    }

    public int getNextId() {
        int id = this.getId();
        increaseId();
        return id;
    }

    public User createUser(int id) {
        User user = new User();
        user.setId(id);
        user.setName(String.format("Name : %1s", id));
        user.setPhone(String.format("Phone : %1s", id));
        return user;
    }

    public void addUser(User user) {
        SharedPreferences.Editor editor = pref.edit();
        String userJson = new Gson().toJson(user);
        editor.putString(String.valueOf(user.getId()), userJson);
        editor.apply();
    }

    public ArrayList<User> getUsers() {

        Map<String, ?> allEntries = pref.getAll();
        ArrayList<User> users = new ArrayList<>();

        for(Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(!entry.getKey().equals("id")) {
                String userJson = pref.getString(entry.getKey(), null);
                if (!TextUtils.isEmpty(userJson)) {
                    User user = new Gson().fromJson(userJson, User.class);
                    users.add(user);
                }
            }
        }

        return users;
    }

    //    public void unUser(String userId) {
//        SharedPreferences.Editor editor = pref.edit();
//        editor.remove(String.valueOf(userId));
//        editor.apply();
//    }

}

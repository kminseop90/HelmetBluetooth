package com.sample.helmetble.model;


import com.sample.helmetble.model.vo.User;

import java.util.ArrayList;


public interface UserModel {

    void addUser();
    ArrayList<User> getUsers();
}

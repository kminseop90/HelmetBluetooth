package com.sample.helmetble.view;


import com.sample.helmetble.base.BaseView;
import com.sample.helmetble.model.vo.User;

import java.util.ArrayList;


public interface MainView extends BaseView {

    void showMessage(String message, int image);
    void showUsers(ArrayList<User> users);
}

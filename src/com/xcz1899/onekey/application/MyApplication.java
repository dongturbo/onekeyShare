package com.xcz1899.onekey.application;

import android.app.Application;
import cn.bmob.v3.BmobUser;

import com.xcz1899.onekey.data.User;

public class MyApplication extends Application {
    private User mUser;

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
    
    
}

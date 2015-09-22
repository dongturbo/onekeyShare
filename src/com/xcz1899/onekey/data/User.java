package com.xcz1899.onekey.data;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser{

    private BmobFile img;
    private Integer gold;
    public BmobFile getImg() {
        return img;
    }
    public void setImg(BmobFile img) {
        this.img = img;
    }
    public Integer getGold() {
        return gold;
    }
    public void setGold(Integer gold) {
        this.gold = gold;
    }
   
}

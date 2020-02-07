package com.riter.atcrowdfunding.vo;

import com.riter.atcrowdfunding.bean.User;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<User> datas = new ArrayList<User>();
    private List<Integer> ids = new ArrayList<Integer>();

    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}

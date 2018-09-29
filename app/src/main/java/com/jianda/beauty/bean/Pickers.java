package com.jianda.beauty.bean;

import java.io.Serializable;

/**
 * Created by yzt on 2018/2/24.
 */

public class Pickers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String showConetnt;

    public String getShowConetnt() {
        return showConetnt;
    }


    public Pickers(String showConetnt) {
        super();
        this.showConetnt = showConetnt;
    }

    public Pickers() {
        super();
    }

}
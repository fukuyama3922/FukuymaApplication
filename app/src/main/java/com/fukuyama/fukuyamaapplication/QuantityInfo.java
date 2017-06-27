package com.fukuyama.fukuyamaapplication;

import java.io.Serializable;

/**
 * Created by fukuyama on 2017/05/27.
 */

public class QuantityInfo implements Serializable {
       public int mQuantity;
    private String mTime;
    private String mComment;
    private boolean mIsSelected;

    public boolean isSelected() {
        return mIsSelected;
    }


    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }


public int getmQuantity() { return mQuantity; }
public void setmQuantity(int mQuantity) { this.mQuantity = mQuantity; }
public String getmTime() { return mTime; }
public void setmTime(String mTime) { this.mTime = mTime; }
public String getmComment() { return mComment; }
public void setmComment(String mComment) { this.mComment = mComment; }



}





package com.fukuyama.fukuyamaapplication;

import java.io.Serializable;

/**
 * Created by fukuyama on 2017/05/27.
 */

public class QuantityInfo implements Serializable {
    private int quantity;
    private String time;
    private String comment;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


public int getQuantity() { return quantity; }
public void setQuantity(int quantity) { this.quantity = quantity; }
public String getTime() { return time; }
public void setTime(String time) { this.time = time; }
public String getComment() { return comment; }
public void setComment(String comment) { this.comment = comment; }



}





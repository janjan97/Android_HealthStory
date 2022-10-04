package com.example.HealthStory.API;

import android.graphics.drawable.Drawable;

public class ExeList {
    private Drawable drawable;
    private String text;
    private String fire;

    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

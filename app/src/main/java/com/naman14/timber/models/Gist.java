package com.naman14.timber.models;

import java.io.Serializable;

public class Gist implements Serializable {
    public String title;
    public String link;
    public String imagepath;
    public String desc;
    public String date;

    @Override
    public String toString() {
        return title;
    }
}

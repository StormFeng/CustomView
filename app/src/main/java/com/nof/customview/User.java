package com.nof.customview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/23.
 */

public class User implements Parcelable{
    private String name;
    private String sex;

    protected User(Parcel in) {
        name = in.readString();
        sex = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
    }
}

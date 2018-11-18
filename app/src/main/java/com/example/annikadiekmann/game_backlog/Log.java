package com.example.annikadiekmann.game_backlog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "log")
public class Log implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "platform")
    public String mPlatform;

    @ColumnInfo(name = "notes")
    public String mNotes;

    @ColumnInfo(name = "status")
    public String mStatus;

    public Log(String mTitle, String mPlatform, String mNotes, String mStatus) {
        this.mTitle = mTitle;
        this.mPlatform = mPlatform;
        this.mNotes = mNotes;
        this.mStatus = mStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.mTitle);
        dest.writeString(this.mPlatform);
        dest.writeString(this.mNotes);
        dest.writeString(this.mStatus);
    }

    protected Log(Parcel in) {
        this.id = in.readLong();
        this.mTitle = in.readString();
        this.mPlatform = in.readString();
        this.mNotes = in.readString();
        this.mStatus = in.readString();
    }

    public static final Creator<Log> CREATOR = new Creator<Log>() {
        @Override
        public Log createFromParcel(Parcel source) {
            return new Log(source);
        }

        @Override
        public Log[] newArray(int size) {
            return new Log[size];
        }
    };
}



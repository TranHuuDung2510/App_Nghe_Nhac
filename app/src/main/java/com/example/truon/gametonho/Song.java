package com.example.truon.gametonho;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by truon on 3/7/2017.
 */

public class Song implements Parcelable {
    public long id = 0;
    public String title = "";
    public String artist = "";
    public String artistID = "";
    public boolean love = false;
    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public Song() {
    }

    public Song(long id, String title, String artist, String artistID, boolean love) {
        this.title = title;
        this.artist = artist;
        this.artistID = artistID;
        this.id = id;
        this.love = love;
    }

    public boolean getLove() {
        return love;
    }

    public void setLove(boolean love) {
        this.love = love;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getID() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(artistID);
        dest.writeLong(id);

    }

    protected Song(Parcel in) {
        this.title = in.readString();
        this.artist = in.readString();
        this.artistID = in.readString();
        this.id = in.readLong();

    }
}

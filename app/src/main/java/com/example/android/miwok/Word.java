package com.example.android.miwok;

import android.widget.ImageView;

public class Word {

    private String defaultWord = "";
    private String foreignWord = "";
    private int imageSrc = NO_IMAGE;
    private static final int NO_IMAGE = 0;
    private int audioResId;

    public  Word(String defaultWord, String foreignWord, int audioResId){
        this.defaultWord = defaultWord;
        this.foreignWord = foreignWord;
        this.audioResId = audioResId;
    }

    public  Word(String defaultWord, String foreignWord, int imageSrc, int audioResId){
        this(defaultWord, foreignWord, audioResId);
        this.imageSrc = imageSrc;
    }

    public String getDefaultWord () {
        return this.defaultWord;
    }

    public String getForeignWord () {
        return this.foreignWord;
    }

    public int getImageSrc() { return this.imageSrc; }

    public int getAudioResId() { return this.audioResId; }

    public boolean hasImage() {
        if (imageSrc == NO_IMAGE)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Word{" +
                "defaultWord='" + defaultWord + '\'' +
                ", foreignWord='" + foreignWord + '\'' +
                ", imageSrc=" + imageSrc +
                ", audioResId=" + audioResId +
                '}';
    }
}

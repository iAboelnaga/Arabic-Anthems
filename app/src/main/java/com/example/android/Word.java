package com.example.android;

public class Word {
    //name of the country
    private String cCountryName;

    /** Image resource ID for the word */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    //Music file Id for words
    private int mAudioResourceId;

    // constructor for the new word object with image Resource id
    public Word(String countryName, int imageResourceId, int audioResourceId){
        cCountryName = countryName;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    //get the country name
    public String getDefaultTranslation(){
        return cCountryName;
    }

    //get the image resource Id
    public int getImageResourceId() {
        return mImageResourceId;
    }

    // check whether image resource id is provided or not
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    //get the audio file resource id
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}

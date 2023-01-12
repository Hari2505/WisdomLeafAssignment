package com.wisdomleafassignment.screens.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListModel {
    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<Datum> getmData() {
        return mData;
    }

    public void setmData(List<Datum> mData) {
        this.mData = mData;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}

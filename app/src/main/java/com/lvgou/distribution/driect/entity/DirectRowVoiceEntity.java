package com.lvgou.distribution.driect.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Snow on 2016/8/10
 */
public class DirectRowVoiceEntity extends GroupMessage{


    @Expose(serialize = true, deserialize = false)
    @SerializedName("MT")
    private final MessageType messageType = MessageType.Voice;
    @Expose(serialize = true, deserialize = true)
    @SerializedName("U")
    private String url;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("L")
    private int voicetime;


    @Override
    public MessageType getMessageType() {
        return this.messageType;
    }

    @Override
    public String toString() {
        return this.url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVoicetime() {
        return voicetime;
    }

    public void setVoicetime(int voicetime) {
        this.voicetime = voicetime;
    }
}

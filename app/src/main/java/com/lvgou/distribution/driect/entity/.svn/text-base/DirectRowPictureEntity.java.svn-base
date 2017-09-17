package com.lvgou.distribution.driect.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Snow on 2016/8/11 0011.
 */
public class DirectRowPictureEntity extends GroupMessage{
    @Expose(serialize = true, deserialize = false)
    @SerializedName("MT")
    private final MessageType messageType = MessageType.Image;
    @Expose(serialize = true, deserialize = true)
    @SerializedName("O")
    private String url;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("T")
    private String thumbnailUrl;

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

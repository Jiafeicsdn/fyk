package com.lvgou.distribution.response.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/24.
 */
public class UploadVideoData implements Serializable{
    private String url;
     private String thumbnailUrl;
     private String coverImage;
     private int length;

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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

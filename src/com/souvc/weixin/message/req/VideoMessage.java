package com.souvc.weixin.message.req;

public class VideoMessage  extends BaseMessage{

    // √ΩÃÂID
    private String MediaId;
    // ”Ô“Ù∏Ò Ω
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    public String getThumbMediaId() {
        return ThumbMediaId;
    }
    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
    
    
    
}

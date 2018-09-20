package com.souvc.weixin.message.resp;

public class VideoMessage extends BaseMessage {
    // สำฦต
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }
}

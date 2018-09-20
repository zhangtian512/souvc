package com.souvc.weixin.message.resp;

public class VoiceMessage extends BaseMessage {
    // ”Ô“Ù
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}

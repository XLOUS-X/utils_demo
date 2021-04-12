package com.example.utilsdemo.video;

import org.junit.jupiter.api.Test;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class VideoTest {

//    @Test
//    public void Test01() {
//        File source = new File("C:\\Users\\TLKJ\\Downloads\\16359647368732380834\\22.mov");
//        File target = new File("C:\\Users\\TLKJ\\Downloads\\16359647368732380834\\44.mp4");
//        try {
//
//            AudioAttributes audio = new AudioAttributes();
//            audio.setCodec("libmp3lame");
//            audio.setBitRate(new Integer(56000));
//            audio.setChannels(new Integer(1));
//            audio.setSamplingRate(new Integer(22050));
//            VideoAttributes video = new VideoAttributes();
//            video.setCodec("libx264");
//            video.setBitRate(new Integer(800000));
//            video.setFrameRate(new Integer(15));
//            EncodingAttributes attrs = new EncodingAttributes();
//            attrs.setFormat("mp4");
//            attrs.setAudioAttributes(audio);
//            attrs.setVideoAttributes(video);
//            Encoder encoder = new Encoder();
//            encoder.encode(source, target, attrs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void Test02() {
        File source = new File("C:\\Users\\TLKJ\\Downloads\\16359647368732380834\\22.mov");
        File target = new File("C:\\Users\\TLKJ\\Downloads\\16359647368732380834\\22.mp4");
        try {
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(new Integer(800000));//设置比特率
            audio.setChannels(new Integer(1));//设置音频通道数
            audio.setSamplingRate(new Integer(44100));//设置采样率
            VideoAttributes video = new VideoAttributes();
            //video.setCodec("mpeg4");
            video.setCodec("libx264");
            video.setBitRate(new Integer(3200000));
            video.setFrameRate(new Integer(15));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.utilsdemo.RTP;



import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacv.*;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avutil.AV_PIX_FMT_YUV420P;

public class VideoConvert {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd__hhmmSSS");

    private static final int RECORD_LENGTH = 5000;

    private static final boolean AUDIO_ENABLED = false;

    private RtspPacketEncode rtspPacketEncode;

    public void frameRecord(String inputFile) throws FrameGrabber.Exception, FrameRecorder.Exception{

        int audioChannel = AUDIO_ENABLED ? 1 : 0;

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtp://127.0.0.1:10000", 1910, 874, audioChannel);
        recorder.setInterleaved(true);
        // video options
        recorder.setVideoOption("tune", "zerolatency");
        recorder.setVideoOption("preset", "ultrafast");
        recorder.setVideoOption("crf", "30");
        recorder.setVideoBitrate(150000);
        recorder.setFrameRate(25);
        recorder.setVideoCodec(AV_CODEC_ID_MPEG2VIDEO);
//        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        // recorder.setFormat("mpegts");
        recorder.setFormat("rtp");

        grabber.start();
        recorder.start();

        Frame frame;
        while ((frame = grabber.grabFrame(AUDIO_ENABLED, true, true, false)) != null) {
            FrameConverter frameConverter =new Java2DFrameConverter();
            BufferedImage bufferedImage = (BufferedImage) frameConverter.convert(frame);
            byte[] r = bufImg2Bytes(bufferedImage);
            try {
                r = rtspPacketEncode.h264ToRtp(r,2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bufferedImage = bytes2bufImg(r);
            frame = frameConverter.convert(bufferedImage);
            recorder.record(frame);


//            recorder.record(frame);
        }
        recorder.stop();
        grabber.stop();
    }

    public static BufferedImage bytes2bufImg(byte[] imgBytes){
        BufferedImage tagImg = null;
        try {
            tagImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
            return tagImg;
        } catch (IOException e) {
            throw new RuntimeException("bugImg写入失败:"+e.getMessage(),e);
        }
    }

    public static byte[] bufImg2Bytes(BufferedImage original){
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(original, "png", bStream);
        } catch (IOException e) {
            throw new RuntimeException("bugImg读取失败:"+e.getMessage(),e);
        }
        return bStream.toByteArray();
    }


    @Test
    public void Test1(){
        String inputFile = "C:\\Users\\TLKJ\\Desktop\\video.mp4";
        String outputFile = "C:\\Users\\TLKJ\\Desktop\\convert.h264";
//        try {
//            frameRecord(inputFile,outputFile);
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        } catch (FrameRecorder.Exception e) {
//            e.printStackTrace();
//        }
        //String videoPath ="../video.mp4";

        try {
            frameRecord(inputFile);
            convertRtsp();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void main(String[] args) {
//        String inputFile = "C:\\Users\\TLKJ\\Desktop\\video.mp4";
//        String outputFile = "C:\\Users\\TLKJ\\Desktop\\convert.h264";
////        try {
////            frameRecord(inputFile,outputFile);
////        } catch (FrameGrabber.Exception e) {
////            e.printStackTrace();
////        } catch (FrameRecorder.Exception e) {
////            e.printStackTrace();
////        }
//        //String videoPath ="../video.mp4";
//
//        try {
//            frameRecord(inputFile, outputFile);
//            convertRtsp();
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        } catch (FrameRecorder.Exception e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 截取视频获得指定帧的图片
     *
     */
    public static void getVideoPic(String inputFile, String outputFile) throws FrameGrabber.Exception, FrameRecorder.Exception {
        int audioChannel = AUDIO_ENABLED ? 1 : 0;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(inputFile);
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);
        recorder.setVideoCodec(AV_CODEC_ID_H264);
        ff.start();
        recorder.start();
        try {
            ff.start();
            int i = 0;
            int length = ff.getLengthInFrames();
            Frame frame;
            long t1 = System.currentTimeMillis();
            while (i < length) {
                frame = ff.grabFrame(AUDIO_ENABLED, true, true, false);
                recorder.record(frame);
                if ((System.currentTimeMillis() - t1) > RECORD_LENGTH) {
                    break;
                }
                i++;
            }
            recorder.stop();
            ff.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertRtsp() throws IOException {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("rtsp://admin:admin12345@15.17.32.205");
        grabber.setVideoCodec(AV_CODEC_ID_H264);
        grabber.setOption("rtsp_transport", "tcp");
        grabber.setFrameRate(15);
        grabber.setImageWidth(1920);
        grabber.setImageHeight(1080);
        grabber.start();
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtp://10.118.25.242:42984", 1920, 1080,0);
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtp://127.0.0.1:10000", 1920, 1080,0);
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtp://127.0.0.1:10000/test.sdp", 1920, 1080);
        recorder.setInterleaved(true);
        // video options
        recorder.setVideoOption("tune", "zerolatency");
        recorder.setVideoOption("preset", "ultrafast");
        recorder.setVideoOption("crf", "30");
        recorder.setVideoBitrate(150000);
        recorder.setFrameRate(25);
        recorder.setVideoCodec(AV_CODEC_ID_MPEG2VIDEO);
//        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        // recorder.setFormat("mpegts");
        recorder.setFormat("rtp");
//         recorder.setFrameRate(FRAME_RATE);
//         recorder.setGopSize(GOP_LENGTH_IN_FRAMES);
        // Audio Option
//        recorder.setAudioBitrate(8 * 1000);
//        recorder.setAudioChannels(1);
//        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//        recorder.setSampleRate(44100);

        recorder.start();
        while (true){
            Frame frame = grabber.grabFrame(AUDIO_ENABLED, true, true, false);
            recorder.record(frame);
        }



//        ByteBuffer bbuf = ByteBuffer.allocate(1000);
//        File file = new File("test.sdp");
//        avformat.AVFormatContext av=new avformat.AVFormatContext();
//        avformat.av_sdp_create(recorder.getOc(),2, bbuf, 1000);
//        FileChannel wChannel = new FileOutputStream(file, false).getChannel();
//        wChannel.write(bbuf);
//        wChannel.close();
    }

}

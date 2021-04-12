package com.example.utilsdemo.video;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoAttributes;
import it.sauronsoftware.jave.VideoSize;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 多媒体工具类
 * @author Administrator
 *
 */
public class MediumManager {
	
	private static Log log = LogFactory.getLog(MediumManager.class);

	public static void main(String[] args) throws EncoderException {
		String rePath = "D:\\video\\22.mov";
		String newPath = "D:\\video\\22.mp4";
		changeAVItoMP4(rePath,newPath);
	}

	/**
	 * AVI转FLV
	 * @throws IllegalArgumentException
	 * @throws InputFormatException
	 * @throws EncoderException
	 */
	public static void changeAVItoFLV(String rePath,String newPath) throws IllegalArgumentException, InputFormatException, EncoderException
	{
		Encoder encoder = new Encoder();
		File source = new File(rePath);
		File target = new File(newPath);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(64000));
		audio.setChannels(new Integer(1));
		audio.setSamplingRate(new Integer(22050));
		VideoAttributes video = new VideoAttributes();
		video.setCodec("flv");
		
		video.setBitRate(480000);
		video.setFrameRate(30);
//		video.setQscale(4); //高品质转换
		video.setSize(new VideoSize(400, 300));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("flv");
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);
	
		encoder.encode(source, target, attrs);
	}
	
	/**
	 * AVI转MP4
	 * @throws IllegalArgumentException
	 * @throws InputFormatException
	 * @throws EncoderException
	 */
	public static void changeAVItoMP4(String rePath,String newPath) throws IllegalArgumentException, InputFormatException, EncoderException
	{
		Encoder encoder = new Encoder();
		File source = new File(rePath);
		File target = new File(newPath);
		AudioAttributes audio = new AudioAttributes();
		audio.setBitRate(new Integer(64000));
		audio.setChannels(new Integer(1));
		audio.setSamplingRate(new Integer(22050));
		VideoAttributes video = new VideoAttributes();
		video.setBitRate(480000);
		video.setFrameRate(30);
//		video.setQscale(4); //高品质转换
		video.setSize(new VideoSize(400, 300));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp4");
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);
	
		encoder.encode(source, target, attrs);
	}
	
	 /** AVI转ogg
	 * @throws IllegalArgumentException
	 * @throws InputFormatException
	 * @throws EncoderException
	 */
	public static void changeAVItoOgg(String rePath,String newPath) throws IllegalArgumentException, InputFormatException, EncoderException
	{
		Encoder encoder = new Encoder();
		File source = new File(rePath);
		File target = new File(newPath);
		AudioAttributes audio = new AudioAttributes();
		audio.setBitRate(new Integer(64000));
		audio.setChannels(new Integer(1));
		audio.setSamplingRate(new Integer(22050));
		VideoAttributes video = new VideoAttributes();
		video.setBitRate(480000);
		video.setFrameRate(30);
//		video.setQscale(4); //高品质转换
		video.setSize(new VideoSize(400, 300));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("ogg");
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);
	
		encoder.encode(source, target, attrs);
	}
	
	
	/**
	 * 生成视频预览图
	 * @throws EncoderException 
	 * @throws FileNotFoundException 
	 */
	public static void createVideoPreviewImage(String reVideoPath,String desImagePath) throws FileNotFoundException, EncoderException
	{
//		Encoder encoder = new Encoder();
//		encoder.getImage(new File(reVideoPath), new File(desImagePath), 1);
	}
	
	/**
	 * 获得视频播放时长
	 * @param videoPath
	 * @return
	 * @throws InputFormatException
	 * @throws EncoderException
	 */
	public static Long getVideoTime(String videoPath) throws InputFormatException, EncoderException
	{
		Encoder encoder = new Encoder();
		MultimediaInfo multimediaInfo =encoder.getInfo(new File(videoPath));
		Long time =multimediaInfo.getDuration();
		return time;
	}
}

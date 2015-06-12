package com.rimi.ctibet.common.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class ConvertUtil
{
	   public static String amr2mp3(File file,File outFile)
	    {    	
	    	AudioAttributes audio = new AudioAttributes();
	        audio.setCodec("libmp3lame");
	        audio.setBitRate(Integer.valueOf(128000));
	        audio.setChannels(Integer.valueOf(2));
	        audio.setSamplingRate(Integer.valueOf(44100));
	        EncodingAttributes attrs = new EncodingAttributes();
	        attrs.setFormat("mp3");
	        attrs.setAudioAttributes(audio);
	        Encoder encoder = new Encoder();
	        try {
	            encoder.encode(file, outFile, attrs);
	            file.delete();
	        }catch(Exception e) {
	        	e.printStackTrace();
	        	return null;
	        }
	    	return outFile.getAbsolutePath();
	    }
}

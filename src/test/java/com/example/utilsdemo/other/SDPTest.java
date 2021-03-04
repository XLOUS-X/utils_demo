package com.example.utilsdemo.other;

import gov.nist.javax.sdp.MediaDescriptionImpl;

import javax.sdp.*;
import javax.sip.Transaction;
import javax.sip.message.Message;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SDPTest {

    public static void main(String[] args) throws Exception {
        String SDPContent = "v=0\n" +
                "o=31011500022000000000 0 0 IN IP4 10.118.25.242\n" +
                "s=Play\n" +
                "u=31011500021120000397\n" +
                "c=IN IP4 10.118.25.242\n" +
                "t=0 0\n" +
                "m=video 42824 RTP/AVP 96\n" +
                "a=rtpmap:96 PS/90000\n" +
                "a=recvonly\n" +
                "y=0115002066\n" +
                "f=v/2/5/25/1/8000a///\n";
        parseSDP(SDPContent);



    }

    public static void parseSDP(String SDPContent) throws Exception{
        String[] SDPContents = SDPContent.split("\n");
        String newSDPContent = "";
        for (String SDP : SDPContents) {
            String type = SDP.substring(0, 1);
            if (!("y".equals(type)||"f".equals(type))){
                newSDPContent += SDP + "\n";
            }
        }
        SdpFactory sdpFactory = SdpFactory.getInstance();
        SessionDescription sessionDescription = sdpFactory.createSessionDescription(newSDPContent);
//        System.out.println("sessionDescription = " + sessionDescription);


        Vector mediaDescriptions = sessionDescription.getMediaDescriptions(true);
        for(int i = 0; i < mediaDescriptions.size(); ++i) {
            MediaDescription m = (MediaDescription)mediaDescriptions.elementAt(i);
            ((MediaDescriptionImpl)m).setDuplexity("sendrecv");
//            System.out.println("m = " + m.toString());
            Media media = m.getMedia();
            Vector formats = media.getMediaFormats(false);
            System.out.println("formats = " + formats);
            System.out.println("MediaPort = " + media.getMediaPort());
//            System.out.println("formats = " + formats);
        }
    }


}

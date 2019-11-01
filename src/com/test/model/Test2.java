package com.test.model;


import java.io.UnsupportedEncodingException;
import java.util.*;

public class Test2 {

    public static String appId = "lilin";
    public static String userName = "18054819612";
    public static String password = "e10adc3949ba59abbe56e057f20f883e";
    public static long time = new Date().getTime();

    public static String sn = "lilin";
    public static String sig = "lilin";
    public static String familyId = "fb22f84715834cfab3cf2d2b29463578";

    public static void main(String[] args) throws Exception {

        sn = UUID.randomUUID().toString();
//        sn=sn.replace("-","");
//        sn  = "693009ce4562513244d513c61c71bc7d";
        time = time+36000;
        System.out.println("sn:"+sn);

        password = password.toUpperCase();
        System.out.println("password:" + password);
//        time = 1845294790231L;
        System.out.println("time:" + time);
        String method = "GET&";
//        String uri = "%2Fapi%2FgetDeviceList&";
        String uri = "/api/getUserSceneList";
        uri = getURLEncoderString(uri);

        Map<String, String > parm = new TreeMap<>();
        parm.put("userName",userName);
        parm.put("appId",appId);
        parm.put("sn",sn);
        parm.put("password",password);
        parm.put("time",time+"");
        parm.put("familyId",familyId);
        StringBuilder sb = new StringBuilder();
        for (String key : parm.keySet()) {
            System.out.println("key:" + key);
            sb.append(key).append("=").append(parm.get(key)).append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println("param:" + sb.toString());

        String source = method+uri+"&"+getURLEncoderString(sb.toString());

        System.out.println("source:" + source);

        String sigkey = "a4a801a1755146ea91815100511b4718&";

        byte[] signature = HMACSHA1.getSignature(source, sigkey);

        String sig =  Base64.getEncoder().encodeToString(signature);

        System.out.println("Base64 sig:" + sig);
        sb.insert(0,"https://open.orvibo.com/api/getUserSceneList?");
        sig = getURLEncoderString(sig);
        sb.append("&sig=").append(sig);
        String http =  sb.toString();
        System.out.println("http:" + http);
//appId=lilin&familyId=fb22f84715834cfab3cf2d2b29463578&password=E10ADC3949BA59ABBE56E057F20F883E&sig=PtUyXuB0%2FrA%2BDVkmwyCSESZbBx4%3D&sn=693009ce4562513244d513c61c71bc7d&time=1845294790231&userName=18054819612
    }

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}

package com.test;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;


public class TestJwt {
    public static String s = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjcwOGM5NDk0ZGQ5NzhhOGNkMDQwY2M4ZWFhN2ZkZjQwZjNmN2JkNDIyNzZjM2Q2YzQ5YzczYWJmMmEzNjgyN2QxZmI0MjA1OGQxZDI1MDQiLCJpYXQiOjE1NDU3OTU3NTEsIm5iZiI6MTU0NTc5NTc1MSwiZXhwIjoxNTc3MzMxNzUxLCJzdWIiOiI4MiIsInNjb3BlcyI6W119.Pnpd7uu29I26mAlTyT04BqXxYYWY_a9yRD3qHjalLNI";

    public static String s2 = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjcwOGM5NDk0ZGQ5NzhhOGNkMDQwY2M4ZWFhN2ZkZjQwZjNmN2JkNDIyNzZjM2Q2YzQ5YzczYWJmMmEzNjgyN2QxZmI0MjA1OGQxZDI1MDQiLCJpYXQiOjE1NDU3OTU3NTEsIm5iZiI6MTU0NTc5NTc1MSwiZXhwIjoxNTc3MzMxNzUxLCJzdWIiOiI4MiIsInNjb3BlcyI6W119.BMpPQFAOGJjnNdf7Y47tC3l8FaRY2q_QQ4gH8XMd8Zw";

    public static String publicKey2 = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAsQm2KJz9lgeUcYH+smsl3fWsH8MLl3XvBf7OxfUsdE128ZvfRChRVnn8ThlA2bOpVuFrAvTLT/jKp/E/IJQMFRIiRZc1fRRF6bDTyC4VuwwidnyUU2BBzpZE73ew6nSTa8CQGxQSMSC2kfwi967QWCSxjzq3EM3LnaDnQaycHtPqKCBFCfLwYyd1f4ycqygjyrSSqj7TdQ5+vLoZvEmQ/sHlKv0eRzzU0rOFHLDI8eZ7YzqTint3uK63a8IZwmMOllKbwwrAYpkm/pA4aPlJSeNEhqfun4X7qrrBiUCDyWgcL9gMujVBqXo3/F7ExiHgujztackjKQNdrgcTAJrwrBaQaq3V7LFY4WnKZeYqbulwbkwYMwGgHSGPfUAJRCP9qGQNo/g/4qWgFB5dwtZ+II8bYl86zj3aZMUuXnuI0LXohXvz734Y39yfqlMZtkUFulFqUFaQIeW1p2udTcLFwEdN/Tilea88PN29Y4GbGW4N6iadVyfcQ6ZZBH+Sh4YzWSJjFfDz1rateEPusyc3GytwVNW3pKoPTie5kuE/MPZVk7FMWTBpOW8fpswOHfdwnixEqHkes8sTXoDchepWQOlzVplFlSUvIfZn6DBX0RkSzqe5f+dX51oNzzft0BQRUV4eMtpRghVI8YiEtU83XIF9GYgcXHjJSfU3IYhSyfcCAwEAAQ==";
    public static String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAsQm2KJz9lgeUcYH+smsl" +
            "3fWsH8MLl3XvBf7OxfUsdE128ZvfRChRVnn8ThlA2bOpVuFrAvTLT/jKp/E/IJQM" +
            "FRIiRZc1fRRF6bDTyC4VuwwidnyUU2BBzpZE73ew6nSTa8CQGxQSMSC2kfwi967Q" +
            "WCSxjzq3EM3LnaDnQaycHtPqKCBFCfLwYyd1f4ycqygjyrSSqj7TdQ5+vLoZvEmQ" +
            "/sHlKv0eRzzU0rOFHLDI8eZ7YzqTint3uK63a8IZwmMOllKbwwrAYpkm/pA4aPlJ" +
            "SeNEhqfun4X7qrrBiUCDyWgcL9gMujVBqXo3/F7ExiHgujztackjKQNdrgcTAJrw" +
            "rBaQaq3V7LFY4WnKZeYqbulwbkwYMwGgHSGPfUAJRCP9qGQNo/g/4qWgFB5dwtZ+" +
            "II8bYl86zj3aZMUuXnuI0LXohXvz734Y39yfqlMZtkUFulFqUFaQIeW1p2udTcLF" +
            "wEdN/Tilea88PN29Y4GbGW4N6iadVyfcQ6ZZBH+Sh4YzWSJjFfDz1rateEPusyc3" +
            "GytwVNW3pKoPTie5kuE/MPZVk7FMWTBpOW8fpswOHfdwnixEqHkes8sTXoDchepW" +
            "QOlzVplFlSUvIfZn6DBX0RkSzqe5f+dX51oNzzft0BQRUV4eMtpRghVI8YiEtU83" +
            "XIF9GYgcXHjJSfU3IYhSyfcCAwEAAQ==";


    public static void main(String args[]) {
        Timer reconnectTimer = new Timer("Timer Reconnect" );
        reconnectTimer.schedule(new ReconnectTask(), 2000);
        reconnectTimer.schedule(new ReconnectTask(), 2000);
        reconnectTimer.schedule(new ReconnectTask(), 2000);
    }

    static class ReconnectTask extends TimerTask {
        private static final String methodName = "ReconnectTask.run";

        public void run() {
            System.out.println("ReconnectTask");
        }
    }

    public static void main2(String args[]) {
//        new TestJwt().parseJWT(s);
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
////        Jwts.parser().setSigningKey(key).parseClaimsJws(jws);
//
//        System.out.println("jws:" + jws);
//
        String[] split = s2.split("\\.");
        for (String s1 : split) {
            byte[] decode = Decoders.BASE64URL.decode(s1);
            System.out.println("decode:" + new String(decode));
        }
        String concatenated = split[0]+"."+split[1];

        byte[] keyBytes = Decoders.BASE64URL.decode(split[2]);


//        SecretKey key2 = Keys.hmacShaKeyFor(Decoders.BASE64.decode(publicKey));
        System.out.println("publicKey = 2 :" + publicKey.equals(publicKey2));

////        Keys.hmacShaKeyFor()
//        System.out.println("split[2]:" + split[2]);
//        byte[] keyBytes = Decoders.BASE64URL.decode(split[2]);
//        parseJWT(s,"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjcwOGM5NDk0ZGQ5NzhhOGNkMDQwY2M4ZWFhN2ZkZjQwZjNmN2JkNDIyNzZjM2Q2YzQ5YzczYWJmMmEzNjgyN2QxZmI0MjA1OGQxZDI1MDQiLCJpYXQiOjE1NDU3OTU3NTEsIm5iZiI6MTU0NTc5NTc1MSwiZXhwIjoxNTc3MzMxNzUxLCJzdWIiOiI4MiIsInNjb3BlcyI6W119.yPvuAoK_82hvSk2nh13d_bgRDbs6S_-X6K_dOKslx3A");
//        parseJWT(s,publicKey2);
        parseJWT(s2,publicKey2);
//
//        Base64UrlDecoder



    }


    public static SecretKey generalKey() {
        String encodedKey = "ilikeenglish.cn";
        byte [] encodeKeyChar = encodedKey.getBytes();
        SecretKey key = new SecretKeySpec(encodeKeyChar, 0, encodeKeyChar.length, "AES");
        return key;
    }

    private static void parseJWT(String jwt, byte[] key) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }
    private static void parseJWT(String jwt, String key) {
//        Jwt parse = Jwts.parser().parse(jwt);
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }

}
package com.test.model;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Test {

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     * @throws
     * @method encrypt
     * @since v1.0
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length,
                secondArray.length);
        return bytes;
    }


    public static byte[] encrypt2(String content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] byteContent = content.getBytes("utf-8");
            int dx = byteContent.length % 16;
            //不足16字节，补齐内容为差值
            int len = 16 - byteContent.length % 16;
            for (int i = 0; i < len; i++) {
                byte[] bytes = {(byte) len};
                System.out.println("bytes： " + parseByte2HexStr(bytes));
                byteContent = concat(byteContent, bytes);
            }
//            if (dx > 0) {
//
//                byte[] bytes = new byte[byteContent.length + (16-dx)];
//                System.arraycopy(byteContent, 0, bytes, 0, byteContent.length);
//                byteContent = bytes;
//            }
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static String Padding = "AES/ECB/NoPadding";

    /**
     * 数据加密
     *
     * @param data
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        byte[] original = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(Padding);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            original = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return original;
    }

    /**
     * 数据解密
     *
     * @param encData
     * @return
     */
    public static String decrypt(String encData, String key) {
//        byte[] decodeBase64 = Base64.decodeBase64(encData);
        byte[] original = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(Padding);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            original = cipher.doFinal(decodeBase64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(original).trim();
    }

    /**
     * 去除数组中的补齐
     *
     * @param paddingBytes 源数组
     * @param dataLength   去除补齐后的数据长度
     * @return 去除补齐后的数组
     */
    public static byte[] noPadding(byte[] paddingBytes, int dataLength) {
        if (paddingBytes == null) {
            return null;
        }

        byte[] noPaddingBytes = null;
        if (dataLength > 0) {
            if (paddingBytes.length > dataLength) {
                noPaddingBytes = new byte[dataLength];
                System.arraycopy(paddingBytes, 0, noPaddingBytes, 0, dataLength);
            } else {
                noPaddingBytes = paddingBytes;
            }
        } else {
            int index = paddingIndex(paddingBytes);
            if (index > 0) {
                noPaddingBytes = new byte[index];
                System.arraycopy(paddingBytes, 0, noPaddingBytes, 0, index);
            }
        }

        return noPaddingBytes;
    }

    public static int byteToInt(byte b) {
        return (b) & 0xff;
    }

    /**
     * 获取补齐的位置
     *
     * @param paddingBytes 源数组
     * @return 补齐的位置
     */
    private static int paddingIndex(byte[] paddingBytes) {
        for (int i = paddingBytes.length - 1; i >= 0; i--) {
            if (paddingBytes[i] != 0) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * AES解密
     *
     * @param data 将要解密的内容
     * @param key  密钥
     * @return 已经解密的内容
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            System.out.println("------------data： : " + parseByte2HexStr(data));
            byte[] decryptData = cipher.doFinal(data);
            System.out.println("------------decryptData： : " + parseByte2HexStr(decryptData));
            int len = 2 + byteToInt(decryptData[4]) + 3;
            return noPadding(decryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public static byte[] noPadding(byte[] paddingBytes) {
        if (paddingBytes == null) {
            return null;
        }
        byte end = paddingBytes[paddingBytes.length - 1];
        for (int i = paddingBytes.length - end; i < paddingBytes.length; i++) {
            if((end^paddingBytes[i])>0)
                return paddingBytes;
        }
        byte[] noPaddingBytes = new byte[paddingBytes.length - end];
        System.arraycopy(paddingBytes, 0, noPaddingBytes, 0, noPaddingBytes.length);
        return noPaddingBytes;
    }

    public static void main(String[] args) throws Exception {
//        String str = "20171017095514800000000000000000";
        String str = "{\"cmd\":86,\"serial\":0}";
        String key = "khggd54865SNJHGF";
        byte[] encrypt = encrypt2(str, key);
//        byte[] encrypt_data = encrypt(str.getBytes(), key.getBytes());
//        String s = decrypt(encrypt_data, key);
        System.out.println("加密前： : " + str);
        System.out.println("密文： : " + parseByte2HexStr(encrypt));

        byte[] decrypt = decrypt(encrypt, key.getBytes());
        System.out.println("解密后:" + new String(decrypt));
    }
}

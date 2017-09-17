package com.lvgou.distribution.utils;


import android.util.Base64;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Administrator on 2016/9/20.
 */
public class AESUtils {
    private static  String secretKey="1234567890ABCDEF";
    private String iv="1234567890123456";
    static Cipher cipher;
    static final String KEY_ALGORITHM = "AES";
    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

    /**
     * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
     */
    public static String method3(String str) throws Exception {
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        Key keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(getIV()));//使用加密模式初始化 密钥
        byte[] encrypt = cipher.doFinal(str.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。
//        Base64.encode(encrypt,  Base64.DEFAULT);
        return Base64.encodeToString(encrypt,Base64.DEFAULT); //此处使用BASE64做转码功能，同时能起到2次加密的作用。
//        System.out.println("method3-加密：" + Arrays.toString(encrypt));
//        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(getIV()));//使用解密模式初始化 密钥
//        byte[] decrypt = cipher.doFinal(encrypt);
//        System.out.println("method3-解密后：" + new String(decrypt));

    }
    static byte[] getIV() {
        String iv = "1234567890123456"; //IV length: must be 16 bytes long
        return iv.getBytes();
    }
    /**
     *     解密
     */
    public static String Decrypt(String sSrc) throws Exception {
        try {
//            // 判断Key是否正确
//            if (sKey == null) {
//                System.out.print("Key为空null");
//                return null;
//            }
//            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            IvParameterSpec iv = new IvParameterSpec(getIV());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//            byte[] encrypted1 = hex2byte(sSrc);
            byte[] encrypted1 = Base64.decode(sSrc,Base64.DEFAULT);
            //先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}

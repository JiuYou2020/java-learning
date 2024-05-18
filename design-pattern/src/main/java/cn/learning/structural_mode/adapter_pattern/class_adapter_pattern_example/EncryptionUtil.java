package cn.learning.structural_mode.adapter_pattern.class_adapter_pattern_example;

import java.util.Base64;

/**
 * @author: jiuyou2020
 * @description: 加密算法工具类
 */
public class EncryptionUtil {
    public static String encrypt(String strToEncrypt) {
        // 将其变成base64编码,模拟加密
        return Base64.getEncoder().encodeToString(strToEncrypt.getBytes());
    }

    public static String decrypt(String strToDecrypt) {
        // 将其变成base64解码,模拟解密
        return new String(Base64.getDecoder().decode(strToDecrypt));
    }
}

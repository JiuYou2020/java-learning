package cn.learning.structural_mode.adapter_pattern.class_adapter_pattern_example;

import java.util.Objects;

/**
 * @author: jiuyou2020
 * @description: 现有加密算法
 */
public class EncryptionAdaptee {
    /**
     * 加密邮箱
     */
    public String encryptEmail(String email) {
        return EncryptionUtil.encrypt(email);
    }

    /**
     * 加密ip,先将ip转换成数字，再加密
     */
    public String encryptIp(String ip) {
        // 将ip转换成数字
        String[] ipArray = ip.split("\\.");
        StringBuilder ipNum = new StringBuilder();
        for (String ipPart : ipArray) {
            ipNum.append(String.format("%03d", Integer.parseInt(ipPart)));
        }
        return EncryptionUtil.encrypt(ipNum.toString());
    }

    /**
     * 解密邮箱
     */
    public String decryptEmail(String email) {
        return EncryptionUtil.decrypt(email);
    }

    /**
     * 解密ip
     */
    public String decryptIp(String ip) {
        String ipNum = EncryptionUtil.decrypt(ip);
        StringBuilder ipStr = new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(ipNum).length(); i += 3) {
            ipStr.append(Integer.parseInt(ipNum.substring(i, i + 3)));
            if (i < ipNum.length() - 3) {
                ipStr.append(".");
            }
        }
        return ipStr.toString();
    }
}

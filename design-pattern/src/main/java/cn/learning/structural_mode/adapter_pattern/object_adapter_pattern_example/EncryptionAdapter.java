package cn.learning.structural_mode.adapter_pattern.object_adapter_pattern_example;

/**
 * @author: jiuyou2020
 * @description: 加密算法适配器
 */
public class EncryptionAdapter implements DatabaseOperationClass {
    private EncryptionAdaptee adaptee;

    public EncryptionAdapter(EncryptionAdaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public String encryptData(String data, String type) {
        String result;
        if ("email".equals(type)) {
            result = adaptee.encryptEmail(data);
        } else if ("ip".equals(type)) {
            result = adaptee.encryptIp(data);
        } else {
            result = "Unsupported type";
        }
        return result;
    }

    @Override
    public String decryptData(String data, String type) {
        String result;
        if ("email".equals(type)) {
            result = adaptee.decryptEmail(data);
        } else if ("ip".equals(type)) {
            result = adaptee.decryptIp(data);
        } else {
            result = "Unsupported type";
        }
        return result;
    }
}

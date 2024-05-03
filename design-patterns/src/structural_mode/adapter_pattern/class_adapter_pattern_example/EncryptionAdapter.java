package structural_mode.adapter_pattern.class_adapter_pattern_example;

/**
 * @author: jiuyou2020
 * @description: 加密算法适配器，类适配器模式，优点是可以重写父类的方法，缺点是只能适配一个类
 */
public final class EncryptionAdapter extends EncryptionAdaptee implements DatabaseOperationClass {
    @Override
    public String encryptData(String data, String type) {
        String result;
        if ("email".equals(type)) {
            result = super.encryptEmail(data);
        } else if ("ip".equals(type)) {
            result = super.encryptIp(data);
        } else {
            result = "Unsupported type";
        }
        return result;
    }

    @Override
    public String decryptData(String data, String type) {
        String result;
        if ("email".equals(type)) {
            result = super.decryptEmail(data);
        } else if ("ip".equals(type)) {
            result = super.decryptIp(data);
        } else {
            result = "Unsupported type";
        }
        return result;
    }
}

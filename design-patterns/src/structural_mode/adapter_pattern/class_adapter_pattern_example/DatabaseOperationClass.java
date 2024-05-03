package structural_mode.adapter_pattern.class_adapter_pattern_example;

/**
 * @author: jiuyou2020
 * @description: 数据库操作类：直接与客户端相连
 */
public interface DatabaseOperationClass {
    String encryptData(String data, String type);

    String decryptData(String data, String type);
}

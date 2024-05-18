package cn.learning.structural_mode.decorative_pattern.data_encryption_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Component component = new SimpleEncryption();
        Decorator decorator = new ModEncrypt(component);
        ReverseOutputEncrypt reverseOutputEncrypt = new ReverseOutputEncrypt(decorator);
        reverseOutputEncrypt.encrypt();
    }
}

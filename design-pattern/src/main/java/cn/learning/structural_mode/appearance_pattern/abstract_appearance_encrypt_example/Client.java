package cn.learning.structural_mode.appearance_pattern.abstract_appearance_encrypt_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        //可以通过配置文件获取具体的加密外观类
        //再通过反射生成具体的加密外观类
        AbstractEncryptFacade abstractEncryptFacade = new NewEncryptFacade();
        abstractEncryptFacade.fileEncrypt("src.txt", "des.txt");
    }
}

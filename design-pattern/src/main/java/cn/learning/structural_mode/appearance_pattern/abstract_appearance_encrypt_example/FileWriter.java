package cn.learning.structural_mode.appearance_pattern.abstract_appearance_encrypt_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class FileWriter {
    public void write(String encryptStr, String fileNameDes) {
        System.out.println("保存密文" + encryptStr + " 写入文件：" + fileNameDes);
    }
}

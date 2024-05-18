package cn.learning.structural_mode.appearance_pattern.abstract_appearance_encrypt_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class FileReader {
    public String read(String fileNameSrc) {
        System.out.println("读取文件：" + fileNameSrc);
        return "读取文件内容";
    }
}

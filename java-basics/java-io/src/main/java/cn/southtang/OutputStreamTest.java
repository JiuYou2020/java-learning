package cn.southtang;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutputStreamTest {
    public static void main(String[] args) {
        try (FileOutputStream output = new FileOutputStream("java-basics/output.txt")) {
            byte[] array = "JiuYou2020".getBytes();
//            output.write(array);
            BufferedOutputStream bos = new BufferedOutputStream(output);
            bos.write(array);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

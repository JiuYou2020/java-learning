package cn.southtang;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriterTest {
    public static void main(String[] args) {
        try (Writer output = new FileWriter("java-basics/output.txt")) {
            output.write("你好，JiuYou2020。");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

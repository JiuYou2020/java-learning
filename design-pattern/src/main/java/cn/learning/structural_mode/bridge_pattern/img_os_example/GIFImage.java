package cn.learning.structural_mode.bridge_pattern.img_os_example;

/**
 * @author: jiuyou2020
 * @description: GIF
 */
public class GIFImage extends Image {

    @Override
    public void parseFile(String fileName) {
        //模拟解析GIF文件
        //Matrix matrix = new Matrix();
        //将解析出来的像素矩阵交给操作系统，由操作系统显示
        imp.doPaint();
        System.out.println(fileName + "，格式为GIF");
    }
}

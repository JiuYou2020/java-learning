package structural_mode.bridge_pattern.img_os_example;

/**
 * @author: jiuyou2020
 * @description: BMP
 */
public class BMPImage extends Image {

    @Override
    public void parseFile(String fileName) {
        //模拟解析BMP文件
        //Matrix matrix = new Matrix();
        //将解析出来的像素矩阵交给操作系统，由操作系统显示
        imp.doPaint();
        System.out.println(fileName + "，格式为BMP");
    }
}

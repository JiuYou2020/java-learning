package structural_mode.bridge_pattern.img_os_example;

/**
 * @author: jiuyou2020
 * @description: 客户端测试类
 */
public class Client {
    public static void main(String[] args) {
        Image image = new JPGImage();
        ImgImpl imgImpl = new WindowsImgImpl();
        image.setImgImpl(imgImpl);
        image.parseFile("小龙女");
    }
}

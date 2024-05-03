package structural_mode.bridge_pattern.img_os_example;

/**
 * @author: jiuyou2020
 * @description: 具体实现类
 */
public class WindowsImgImpl implements ImgImpl {

    @Override
    public void doPaint() {
        System.out.println("在Windows操作系统中显示图像");
    }
}

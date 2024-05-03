package structural_mode.bridge_pattern.img_os_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class LinuxImgImpl implements ImgImpl{
    @Override
    public void doPaint() {
        System.out.println("在Linux操作系统中显示图像");
    }
}

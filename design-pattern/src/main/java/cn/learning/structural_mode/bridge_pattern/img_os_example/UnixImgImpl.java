package cn.learning.structural_mode.bridge_pattern.img_os_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class UnixImgImpl implements ImgImpl {
    @Override
    public void doPaint() {
        System.out.println("在Unix操作系统中显示图像");
    }
}

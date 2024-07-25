package cn.learning.structural_mode.bridge_pattern.img_os_example;

/**
 * @author: jiuyou2020
 * @description: 抽象类
 */
public abstract class Image {
    protected ImgImpl imp;

    public void setImgImpl(ImgImpl imp) {
        this.imp = imp;
    }

    /**
     * 解析文件，将文件解析为像素矩阵
     *
     * @param fileName 文件名
     */
    public abstract void parseFile(String fileName);
}

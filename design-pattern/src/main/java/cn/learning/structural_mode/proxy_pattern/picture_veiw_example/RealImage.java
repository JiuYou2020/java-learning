package cn.learning.structural_mode.proxy_pattern.picture_veiw_example;

import javax.swing.*;
import java.net.URL;

// 真实图片类
class RealImage implements Image {
    private String filename;
    private ImageIcon imageIcon;

    public RealImage(String filename) {
        this.filename = filename;
        loadImageIcon();
    }

    private void loadImageIcon() {
        try {
            URL url = new URL(filename);
            imageIcon = new ImageIcon(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayIcon(JLabel label) {
        label.setIcon(imageIcon);
    }

    @Override
    public void displayFullImage() {
        // 显示原图逻辑
        JOptionPane.showMessageDialog(null, "显示原图：" + filename);
    }
}
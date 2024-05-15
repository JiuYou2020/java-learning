package structural_mode.proxy_pattern.picture_veiw_example;

import javax.swing.*;
import java.net.URL;

// 虚拟图片代理类
class ProxyImage implements Image {
    private String filename;
    private ImageIcon imageIcon;
    private RealImage realImage;

    public ProxyImage(String filename) {
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
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.displayIcon(label);
    }

    @Override
    public void displayFullImage() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.displayFullImage();
    }
}

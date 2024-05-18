package cn.learning.structural_mode.proxy_pattern.picture_veiw_example;

import javax.swing.*;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageViewerApp app = new ImageViewerApp();
            app.setVisible(true);
        });
    }
}

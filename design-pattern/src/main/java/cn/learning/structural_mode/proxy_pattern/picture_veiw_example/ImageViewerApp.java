package cn.learning.structural_mode.proxy_pattern.picture_veiw_example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 图片查看器界面
public class ImageViewerApp extends JFrame {
    private Map<JLabel, ProxyImage> imageMap = new HashMap<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ImageViewerApp() {
        setTitle("网络图片查看器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // 创建面板
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        // 添加图片URL
        addImage("https://tse1-mm.cn.bing.net/th/id/OIP-C.dIFgv5Jaess7oclRS7xg2wHaE8?rs=1&pid=ImgDetMain", panel);
    }

    private void addImage(String imageURL, JPanel panel) {
        JLabel label = new JLabel();
        ProxyImage proxyImage = new ProxyImage(imageURL);
        imageMap.put(label, proxyImage);

        // 显示小图标
        executorService.submit(() -> {
            proxyImage.displayIcon(label);
            panel.add(label);
        });

        // 点击图片查看原图
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    proxyImage.displayFullImage();
                }
            }
        });
    }
}

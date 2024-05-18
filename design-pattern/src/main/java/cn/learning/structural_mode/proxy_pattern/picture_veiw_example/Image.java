package cn.learning.structural_mode.proxy_pattern.picture_veiw_example;

import javax.swing.*;

// 图片接口
interface Image {
    void displayIcon(JLabel label);
    void displayFullImage();
}
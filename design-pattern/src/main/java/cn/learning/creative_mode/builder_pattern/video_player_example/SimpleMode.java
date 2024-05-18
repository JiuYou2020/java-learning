package cn.learning.creative_mode.builder_pattern.video_player_example;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/25 下午3:09
 */
public class SimpleMode extends Builder{
    @Override
    public void buildMenu() {
        mainInterface.setMenu("菜单");
    }

    @Override
    public void buildPlayList() {

    }

    @Override
    public void buildMainWindow() {
        mainInterface.setMainWindow("主窗口");
    }

    @Override
    public void buildControlStrip() {

    }

    @Override
    public void buildFavoriteList() {

    }
}

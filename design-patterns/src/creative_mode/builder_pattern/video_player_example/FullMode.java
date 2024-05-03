package builder_pattern.video_player_example;

/**
 * @author jiuyou2020
 * @description 具体建造者
 * @date 2024/4/25 下午2:35
 */
public class FullMode extends Builder {

    @Override
    public void buildMenu() {
        mainInterface.setMenu("菜单");
    }

    @Override
    public void buildPlayList() {
        mainInterface.setPlayList("播放列表");
    }

    @Override
    public void buildMainWindow(){
        mainInterface.setMainWindow("主窗口");
    }

    @Override
    public void buildControlStrip() {
        mainInterface.setControlStrip("控制条");
    }

    @Override
    public void buildFavoriteList() {
        mainInterface.setFavoriteList("收藏列表");
    }
}

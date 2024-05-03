package creative_mode.builder_pattern.video_player_example;

/**
 * @author jiuyou2020
 * @description 抽象建造者
 * @date 2024/4/25 下午2:34
 */
public abstract class Builder {
    protected MainInterface mainInterface = new MainInterface();

    public abstract void buildMenu();
    public abstract void buildPlayList();
    public abstract void buildMainWindow();
    public abstract void buildControlStrip();
    public abstract void buildFavoriteList();

    public MainInterface getResult() {
        return mainInterface;
    }
}

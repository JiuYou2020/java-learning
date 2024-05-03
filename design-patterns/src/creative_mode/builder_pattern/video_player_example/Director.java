package creative_mode.builder_pattern.video_player_example;

/**
 * @author jiuyou2020
 * @description 指挥者：隔离客户端与创建过程，控制buildx方法是否被调用以及调用次序
 * @date 2024/4/25 下午2:39
 */
public class Director {
    private final Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public MainInterface build() {
        builder.buildMenu();
        builder.buildPlayList();
        builder.buildMainWindow();
        builder.buildControlStrip();
        builder.buildFavoriteList();

        return builder.getResult();
    }
}

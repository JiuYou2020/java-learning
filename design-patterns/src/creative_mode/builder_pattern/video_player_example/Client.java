package builder_pattern.video_player_example;

/**
 * @author jiuyou2020
 * @description 客户端类
 * @date 2024/4/25 下午2:43
 */
public class Client {
    public static void main(String[] args) {
        Builder builder = new FullMode();
        Director director = new Director(builder);
        MainInterface mainInterface = director.build();
        System.out.println(mainInterface.getMenu());
        System.out.println(mainInterface.getPlayList());
        System.out.println(mainInterface.getMainWindow());
        System.out.println(mainInterface.getControlStrip());
        System.out.println(mainInterface.getFavoriteList());

        SimpleMode simpleMode = new SimpleMode();
        Director director1 = new Director(simpleMode);
        MainInterface mainInterface1 = director1.build();
        System.out.println(mainInterface1.getMenu());
        System.out.println(mainInterface1.getPlayList());
        System.out.println(mainInterface1.getMainWindow());
        System.out.println(mainInterface1.getControlStrip());
        System.out.println(mainInterface1.getFavoriteList());
    }
}

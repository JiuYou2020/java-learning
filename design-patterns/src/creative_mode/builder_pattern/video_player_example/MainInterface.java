package builder_pattern.video_player_example;

/**
 * @author jiuyou2020
 * @description 复杂产品类-视频播放器主界面
 * @date 2024/4/25 下午2:35
 */
public class MainInterface {
    private String menu;
    private String playList;
    private String mainWindow;
    private String controlStrip;
    private String favoriteList;

    public String getControlStrip() {
        return controlStrip;
    }

    public void setControlStrip(String controlStrip) {
        this.controlStrip = controlStrip;
    }

    public String getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(String favoriteList) {
        this.favoriteList = favoriteList;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPlayList() {
        return playList;
    }

    public void setPlayList(String playList) {
        this.playList = playList;
    }

    public String getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(String mainWindow) {
        this.mainWindow = mainWindow;
    }
}

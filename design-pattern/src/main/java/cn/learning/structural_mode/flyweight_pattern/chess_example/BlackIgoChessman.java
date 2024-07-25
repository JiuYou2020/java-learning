package cn.learning.structural_mode.flyweight_pattern.chess_example;

/**
 * @author: jiuyou2020
 * @description: 黑色棋子类，具体享元类
 */
public class BlackIgoChessman extends IgoChessman {
    @Override
    public String getColor() {
        return "黑色";
    }
}

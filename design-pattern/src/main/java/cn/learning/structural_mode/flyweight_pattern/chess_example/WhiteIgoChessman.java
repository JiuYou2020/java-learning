package cn.learning.structural_mode.flyweight_pattern.chess_example;

/**
 * @author: jiuyou2020
 * @description: 白色棋子类，具体享元类
 */
public class WhiteIgoChessman extends IgoChessman{
    @Override
    public String getColor() {
        return "白色";
    }
}

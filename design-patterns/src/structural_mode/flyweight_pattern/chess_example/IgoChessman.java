package structural_mode.flyweight_pattern.chess_example;

/**
 * @author: jiuyou2020
 * @description: 享元类
 */
public abstract class IgoChessman {
    public abstract String getColor();

    public void display(Coordinates coordinates) {
        System.out.println("棋子颜色：" + this.getColor() + ", 棋子位置：" + coordinates.getX() + ", " + coordinates.getY());
    }
}

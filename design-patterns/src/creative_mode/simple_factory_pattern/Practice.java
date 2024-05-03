package simple_factory_pattern;

/**
 * @author jiuyou2020
 * @description 使用简单工厂模式设计一个可以创建不同几何形状（如圆形、方形和三角形等）的绘图工具，每个几何图形都具有绘制draw（）和擦除erase（）两个方法，要求在绘制不支持的几何图形时，提示一个UnSupportedShapeException。
 * @date 2024/4/22 下午12:00
 */
public class Practice {
    public static void main(String[] args) {
        Shape round = ShapeFactory.createShape(ShapeFactory.ROUND);
        round.draw();
        round.erase();

        Shape square = ShapeFactory.createShape(ShapeFactory.SQUARE);
        square.draw();
        square.erase();

        Shape triangle = ShapeFactory.createShape(ShapeFactory.TRIANGLE);
        triangle.draw();
        triangle.erase();

        Shape unsupportedShape = ShapeFactory.createShape("unsupportedShape");
        unsupportedShape.draw();
        unsupportedShape.erase();
    }
}

abstract class Shape {
    public abstract void draw();

    public abstract void erase();
}

class Round extends Shape {
    @Override
    public void draw() {
        System.out.println("绘制圆形");
    }

    @Override
    public void erase() {
        System.out.println("擦除圆形");
    }
}

class Square extends Shape {
    @Override
    public void draw() {
        System.out.println("绘制方形");
    }

    @Override
    public void erase() {
        System.out.println("擦除方形");
    }
}

class Triangle extends Shape {
    @Override
    public void draw() {
        System.out.println("绘制三角形");
    }

    @Override
    public void erase() {
        System.out.println("擦除三角形");
    }
}

class ShapeFactory {
    public static final String ROUND = "round";
    public static final String SQUARE = "square";
    public static final String TRIANGLE = "triangle";

    public static Shape createShape(String shapeType) {
        switch (shapeType) {
            case ROUND:
                return new Round();
            case SQUARE:
                return new Square();
            case TRIANGLE:
                return new Triangle();
            default:
                throw new RuntimeException("不支持的几何图形");
        }
    }
}
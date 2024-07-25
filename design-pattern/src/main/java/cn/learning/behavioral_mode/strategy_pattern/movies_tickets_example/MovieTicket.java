package cn.learning.behavioral_mode.strategy_pattern.movies_tickets_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class MovieTicket {
    private double price;
    private Discount discount;

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return discount.calculate(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

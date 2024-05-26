package cn.learning.behavioral_mode.strategy_pattern.movies_tickets_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        MovieTicket movieTicket = new MovieTicket();
        movieTicket.setPrice(100);
        movieTicket.setDiscount(new VipDiscount());
        movieTicket.getPrice();

        movieTicket.setDiscount(new ChildrenDiscount());
        movieTicket.getPrice();

        movieTicket.setDiscount(new StudentDiscount());
        movieTicket.getPrice();
    }
}

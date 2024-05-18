package cn.learning.behavioral_mode.iterator_pattern;
import java.util.*;

public class Client {
	public static void main(String args[]) {
		List<Object> products = new ArrayList<>();
		products.add("Core Java Book");
		products.add("Advanced Java Book");
		products.add("Design Pattern Book");
		products.add("Spring Book");
		products.add("Hibernate Book");
			
		AbstractObjectList list;
		AbstractIterator iterator;
		
		list = new ProductList(products);
		iterator = list.createIterator();

		System.out.println("正向遍历：");
		while(!iterator.isLast()) {
			System.out.print(iterator.getNextItem() + ", ");
			iterator.next();
		}
		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("逆向遍历：");
		while(!iterator.isFirst()) {
			System.out.print(iterator.getPreviousItem() + ", ");
			iterator.previous();
		}
	}
}

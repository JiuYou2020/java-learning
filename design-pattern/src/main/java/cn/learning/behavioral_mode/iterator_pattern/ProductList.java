package cn.learning.behavioral_mode.iterator_pattern;
import java.util.*;

public class ProductList extends AbstractObjectList {
	public ProductList(List<Object> products) {
		super(products);
	}
	
	public AbstractIterator createIterator() {
		return new ProductIterator(this);
	}
} 

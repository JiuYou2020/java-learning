package cn.learning.behavioral_mode.iterator_pattern;

public interface AbstractIterator {
	public void next();
	public boolean isLast();
	public void previous();
	public boolean isFirst();
	public Object getNextItem();
	public Object getPreviousItem();
}
package cn.learning.behavioral_mode.memo_pattern.chess_example;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * @author: jiuyou2020
 * @description:
 */
class Chessman {
    private String label;
    private int x;
    private int y;

    public Chessman(String label, int x, int y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ChessmanMemento save() {
        return new ChessmanMemento(this.label, this.x, this.y);
    }

    public void restore(ChessmanMemento memento) {
        this.label = memento.getLabel();
        this.x = memento.getX();
        this.y = memento.getY();
    }
}

class ChessmanMemento {
    private String label;
    private int x;
    private int y;

    public ChessmanMemento(String label, int x, int y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class MementoCaretaker {
    //在本演示中，这里使用List，会导致在play并悔棋后，如果再play，此后执行悔棋操作可能会出现对象状态错误
    //1,1 play {[1,2]} index=0
    //1,4 play {[1,2],[1,4]} index=1
    //5,4 play {[1,2],[1,4],[5,4]} index=2

    //1,4 undo {[1,2],[1,4],[5,4]} index=1
    //3,4 play {[1,2],[1,4],[5,4],[3,4]} index=2
    //3,6 play {[1,2],[1,4],[5,4],[3,4],[3,6]} index=3 ,此时如果执行undo，会导致对象状态错误，可以使用链表或者栈来解决此问题
    private List<ChessmanMemento> memotoList = new ArrayList<>();

    public ChessmanMemento getMemento(int i) {
        return memotoList.get(i);
    }

    public void setMemento(ChessmanMemento memento) {
        memotoList.add(memento);
    }
}

class Client {
    private static int index = -1;
    private static MementoCaretaker mementoCaretaker = new MementoCaretaker();

    public static void main(String[] args) {
        Chessman chessman = new Chessman("车", 1, 1);
        play(chessman);
        chessman.setY(4);
        play(chessman);
        chessman.setX(5);
        play(chessman);
        undo(chessman);
        undo(chessman);
        redo(chessman);
        redo(chessman);

    }

    private static void redo(Chessman chessman) {
        System.out.println("撤销悔棋");
        index++;
        chessman.restore(mementoCaretaker.getMemento(index));
        System.out.println("棋子" + chessman.getLabel() + "当前位置为：" + "x:" + chessman.getX() + " y:" + chessman.getY());
    }

    private static void undo(Chessman chessman) {
        System.out.println("悔棋");
        index--;
        chessman.restore(mementoCaretaker.getMemento(index));
        System.out.println("棋子" + chessman.getLabel() + "当前位置为：" + "x:" + chessman.getX() + " y:" + chessman.getY());
    }

    private static void play(Chessman chessman) {
        mementoCaretaker.setMemento(chessman.save());
        index++;
        System.out.println("棋子" + chessman.getLabel() + "当前位置为：" + "x:" + chessman.getX() + " y:" + chessman.getY());
    }
}
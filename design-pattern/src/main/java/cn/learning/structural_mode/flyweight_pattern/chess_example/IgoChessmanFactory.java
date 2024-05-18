package cn.learning.structural_mode.flyweight_pattern.chess_example;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: jiuyou2020
 * @description: 工厂类
 */
public class IgoChessmanFactory {
    private static IgoChessmanFactory instance = new IgoChessmanFactory();
    private static ConcurrentHashMap<String, IgoChessman> igoChessmanMap;

    public IgoChessmanFactory() {
        igoChessmanMap = new ConcurrentHashMap<>();
        IgoChessman blackIgoChessman = new BlackIgoChessman();
        IgoChessman whiteIgoChessman = new WhiteIgoChessman();
        igoChessmanMap.put("b", blackIgoChessman);
        igoChessmanMap.put("w", whiteIgoChessman);
    }

    public static IgoChessmanFactory getInstance() {
        return instance;
    }

    public static IgoChessman getIgoChessman(String color) {
        return igoChessmanMap.get(color);
    }
}



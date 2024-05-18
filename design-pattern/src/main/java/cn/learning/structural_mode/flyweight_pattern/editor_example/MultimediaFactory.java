package cn.learning.structural_mode.flyweight_pattern.editor_example;

import java.util.HashMap;
import java.util.Map;

// 多媒体工厂类
class MultimediaFactory {
    private static final Map<String, Multimedia> multimediaMap = new HashMap<>();

    // 获取多媒体对象,本来应该从数据库或者文件中读取，这里简化为直接创建
    public static Multimedia getMultimedia(String type, String name) {
        Multimedia multimedia = multimediaMap.get(name);
        if (multimedia == null) {
            switch (type) {
                case "image":
                    multimedia = new Image(name);
                    break;
                case "animation":
                    multimedia = new Animation(name);
                    break;
                case "video":
                    multimedia = new Video(name);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid multimedia type: " + type);
            }
            multimediaMap.put(name, multimedia);
        }
        return multimedia;
    }
}
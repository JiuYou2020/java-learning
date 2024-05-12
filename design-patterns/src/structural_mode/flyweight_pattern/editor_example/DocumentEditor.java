package structural_mode.flyweight_pattern.editor_example;

import java.util.HashMap;
import java.util.Map;

class DocumentEditor {
    private Map<Integer, Multimedia> multimediaMap = new HashMap<>();

    // 插入多媒体到文档中
    public void insertMultimedia(int position, String type, String name) {
        Multimedia multimedia = MultimediaFactory.getMultimedia(type, name);
        multimediaMap.put(position, multimedia);
    }

    // 显示文档内容
    public void displayDocument() {
        for (Map.Entry<Integer, Multimedia> entry : multimediaMap.entrySet()) {
            entry.getValue().display(entry.getKey(), 100); // 位置和大小可以根据需求调整
        }
    }
}

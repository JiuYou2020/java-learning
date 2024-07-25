package cn.southtang;

import java.nio.CharBuffer;

/**
 * <a href="https://javaguide.cn/java/io/nio-basis.html#buffer-%E7%BC%93%E5%86%B2%E5%8C%BA">...</a>
 */
public class CharBufferDemo {
    public static void main(String[] args) {
        // 分配一个容量为8的CharBuffer
        CharBuffer buffer = CharBuffer.allocate(8);
        System.out.println("初始状态：");
        printState(buffer);

        // 向buffer写入3个字符
        buffer.put('a').put('b').put('c');
        System.out.println("写入3个字符后的状态：");
        printState(buffer);

        // 调用flip()方法，准备读取buffer中的数据，将 position 置 0,limit 的置 3
        buffer.flip();
        System.out.println("调用flip()方法后的状态：");
        printState(buffer);

        // 读取字符
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }

        // 调用clear()方法，清空缓冲区，将 position 的值置为 0，将 limit 的值置为 capacity 的值
        buffer.clear();
        System.out.println("调用clear()方法后的状态：");
        printState(buffer);

    }

    // 打印buffer的capacity、limit、position、mark的位置
    private static void printState(CharBuffer buffer) {
        System.out.print("capacity: " + buffer.capacity());
        System.out.print(", limit: " + buffer.limit());
        System.out.print(", position: " + buffer.position());
        System.out.print(", mark 开始读取的字符: " + buffer.mark());
        System.out.println("\n");
    }
}

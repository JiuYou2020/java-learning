package cn.learning.structural_mode.decorative_pattern.data_encryption_example;

/**
 * @author: jiuyou2020
 * @description: 具体装饰类
 */
public class ReverseOutputEncrypt extends Decorator {

    public ReverseOutputEncrypt(Component component) {
        super(component);
    }

    public void encrypt() {
        super.encrypt();
        reverseOutputEncrypt();
    }

    private void reverseOutputEncrypt() {
        System.out.println("进行逆向输出加密");
    }
}

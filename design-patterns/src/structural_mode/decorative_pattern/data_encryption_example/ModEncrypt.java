package structural_mode.decorative_pattern.data_encryption_example;

/**
 * @author: jiuyou2020
 * @description: 具体装饰类
 */
public class ModEncrypt extends Decorator {

    public ModEncrypt(Component component) {
        super(component);
    }

    public void encrypt() {
        super.encrypt();
        modEncrypt();
    }

    private void modEncrypt() {
        System.out.println("进行求模加密");
    }
}

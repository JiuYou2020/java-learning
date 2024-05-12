package structural_mode.decorative_pattern.data_encryption_example;

/**
 * @author: jiuyou2020
 * @description: 具体构件
 */
public class SimpleEncryption extends Component {
    @Override
    public void encrypt() {
        System.out.println("进行简单加密");
    }
}

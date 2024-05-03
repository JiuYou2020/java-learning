package structural_mode.adapter_pattern.object_adapter_pattern_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        DatabaseOperationClass databaseOperationClass = new EncryptionAdapter(new EncryptionAdaptee());
        String email = databaseOperationClass.encryptData("test@email.com", "email");
        System.out.println(email);
        System.out.println(databaseOperationClass.decryptData(email, "email"));

        String ip = databaseOperationClass.encryptData("127.0.0.1", "ip");
        System.out.println(ip);
        System.out.println(databaseOperationClass.decryptData(ip, "ip"));
    }
}

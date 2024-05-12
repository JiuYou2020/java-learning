package structural_mode.appearance_pattern.abstract_appearance_encrypt_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class NewEncryptFacade extends AbstractEncryptFacade {
    private FileReader fileReader;
    private FileWriter fileWriter;
    private NewCipherMachine cipherMachine;

    public NewEncryptFacade() {
        fileReader = new FileReader();
        fileWriter = new FileWriter();
        cipherMachine = new NewCipherMachine();
    }

    @Override
    public void fileEncrypt(String fileNameSrc, String fileNameDes) {
        String plainStr = fileReader.read(fileNameSrc);
        String encryptStr = cipherMachine.encrypt(plainStr);
        fileWriter.write(encryptStr, fileNameDes);
    }
}

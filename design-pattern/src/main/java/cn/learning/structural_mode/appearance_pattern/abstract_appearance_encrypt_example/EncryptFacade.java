package cn.learning.structural_mode.appearance_pattern.abstract_appearance_encrypt_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class EncryptFacade extends AbstractEncryptFacade {
    private FileReader fileReader;
    private FileWriter fileWriter;
    private CipherMachine cipherMachine;

    public EncryptFacade() {
        fileReader = new FileReader();
        fileWriter = new FileWriter();
        cipherMachine = new CipherMachine();
    }

    @Override
    public void fileEncrypt(String fileNameSrc, String fileNameDes) {
        String plainStr = fileReader.read(fileNameSrc);
        String encryptStr = cipherMachine.encrypt(plainStr);
        fileWriter.write(encryptStr, fileNameDes);
    }
}

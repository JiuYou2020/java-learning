package cn.southtang;

/**
 * 注意META-INF/services/目录下的文件名必须是接口的全限定名，文件内容是实现类的全限定名
 */
public class Main {
    public static void main(String[] args) {
        LoggerService service = LoggerService.getService();

        service.info("Hello SPI");
        service.debug("Hello SPI");
    }
}

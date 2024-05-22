//todo 有问题，有时间学一下测试再过来补全测试
//package cn.jiuyou2020.nettransmit;
//
//import cn.jiuyou2020.annonation.EnableRpcServer;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest(classes = MessageSenderTest.TestConfiguration.class)
//@ExtendWith(SpringExtension.class)
//public class MessageSenderTest {
//
//    @BeforeEach
//    public void setUp() {
//        MessageTestReceiver messageTestReceiver = new MessageTestReceiver();
//        messageTestReceiver.run();
//    }
//
//    @AfterEach
//    public void tearDown() {
//    }
//
//    @Test
//    public void testRefuseConnect() {
//        MessageSender sender = new MessageSender();
//        byte[] serializedData = new byte[]{1, 2, 3, 4, 5};
//        assertThrows(IOException.class, () -> {
//            sender.connect("http://localhost:8088", serializedData);
//        });
//    }
//
//    @Test
//    public void testSuccessfulConnect() throws Exception {
//        MessageSender sender = new MessageSender();
//        byte[] serializedData = new byte[]{1, 2, 3, 4, 5};
//        sender.connect("http://localhost:8088", serializedData);
//    }
//
//    @Configuration(proxyBeanMethods = false)
//    @EnableAutoConfiguration
//    @EnableRpcServer
//    protected static class TestConfiguration {
//
//    }
//}
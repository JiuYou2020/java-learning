package cn.jiuyou2020.logexceptionemailalert;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil extends AppenderBase<ILoggingEvent> {

    private static final long INTERVAL = 10 * 1000 * 60;
    private long lastAlarmTime = 0;

    public static void sendMail(String title, String context) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(ContextUtil.getEnvironment().getProperty("spring.mail.username"));
        simpleMailMessage.setTo("southtang@qq.com");
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(context);

        JavaMailSender javaMailSender = ContextUtil.getApplicationContext().getBean(JavaMailSender.class);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if (canAlarm()) {
            sendMail(iLoggingEvent.getLoggerName(), iLoggingEvent.getFormattedMessage());
        }
    }

    private boolean canAlarm() {
        long now = System.currentTimeMillis();
        if (now - lastAlarmTime >= INTERVAL) {
            lastAlarmTime = now;
            return true;
        }
        return false;
    }
}

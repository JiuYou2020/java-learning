package cn.learning.behavioral_mode.observer_pattern.login_flow_example;

import java.util.concurrent.SubmissionPublisher;

public class Login extends SubmissionPublisher<LoginEvent> {
    public void fireEvent(LoginEvent event) {
        submit(event);
    }
}

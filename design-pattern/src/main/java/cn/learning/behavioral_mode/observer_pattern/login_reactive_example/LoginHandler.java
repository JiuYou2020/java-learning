package cn.learning.behavioral_mode.observer_pattern.login_reactive_example;

public class LoginHandler extends LoginEventListener {
    @Override
    protected void handleEvent(LoginEvent event) {
        System.out.println("Logging in with username: " + event.getUsername() + " and password: " + event.getPassword());
        // 进行实际的登录验证逻辑
    }
}

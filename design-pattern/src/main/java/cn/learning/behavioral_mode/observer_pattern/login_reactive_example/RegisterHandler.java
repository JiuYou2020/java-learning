package cn.learning.behavioral_mode.observer_pattern.login_reactive_example;

public class RegisterHandler extends LoginEventListener {
    @Override
    protected void handleEvent(LoginEvent event) {
        System.out.println("Registering with username: " + event.getUsername() + " and password: " + event.getPassword());
        // 进行实际的注册逻辑
    }
}

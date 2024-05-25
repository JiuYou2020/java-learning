package cn.learning.behavioral_mode.observer_pattern.login_reactive_example;

import javax.swing.*;

public class RxJavaLoginExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("RxJava Login Example");
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.add(panel);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 创建Login主题
        Login login = new Login();

        // 创建并注册观察者
        LoginHandler loginHandler = new LoginHandler();
        RegisterHandler registerHandler = new RegisterHandler();
        loginHandler.subscribe(login.getSubject());
        registerHandler.subscribe(login.getSubject());

        // 将事件监听器注册到按钮
        loginButton.addActionListener(event -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            login.fireEvent(new LoginEvent(username, password));
        });

        registerButton.addActionListener(event -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            login.fireEvent(new LoginEvent(username, password));
        });
    }
}

package cn.southtang.dynamic_proxy.jdk;

public class UserServiceImpl implements UserService {
    @Override
    public void addUser() {
        System.out.println("Adding a user");
    }
}

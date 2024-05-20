package cn.jiuyou2020.serialize;

import cn.jiuyou2020.serialize.PersonOuterClass.AddressBook;
import cn.jiuyou2020.serialize.PersonOuterClass.Person;

import java.io.IOException;

public class ProtobufTest {
    public static void main(String[] args) {
        // 创建一个AddressBook对象并添加Person
        AddressBook.Builder addressBookBuilder = AddressBook.newBuilder();

        Person person = Person.newBuilder()
                .setName("John Doe")
                .setId(1234)
                .setEmail("johndoe@example.com")
                .build();

        addressBookBuilder.addPeople(person);
        AddressBook addressBook = addressBookBuilder.build();

        // 序列化为字节数组
        byte[] serializedData = addressBook.toByteArray();

        // 反序列化从字节数组
        AddressBook deserializedAddressBook;
        try {
            deserializedAddressBook = AddressBook.parseFrom(serializedData);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 打印反序列化后的数据
        for (Person p : deserializedAddressBook.getPeopleList()) {
            System.out.println("Name: " + p.getName());
            System.out.println("ID: " + p.getId());
            System.out.println("Email: " + p.getEmail());
        }
    }
}

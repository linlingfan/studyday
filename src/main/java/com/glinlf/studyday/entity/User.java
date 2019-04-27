package com.glinlf.studyday.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author glinlf
 * @date 2019-03-24
 * @Description TODO
 **/
public class User implements Serializable {

    public static int num = 100;

    public int age;

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // 反射
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        User user = new User(1, "llf");
        Class userClass = user.getClass();
        Class userClass1 = Class.forName("com.glinlf.studyday.entity.User");
        Class userClass2 = User.class;
        System.out.println(userClass == userClass1);
        System.out.println(userClass == userClass2);

        System.out.println(User.num);

//        System.out.println(userClass.getField("id"));
        Field[] fields = userClass.getDeclaredFields(); // 获取字段信息 包括私有
        for (Field f : fields) {
            System.out.println(f);
        }

        // 逆向 通过User的Class对象 获取User的字段信息 并实例化对象赋值
        Field field = userClass.getField("age");
        Object obj = userClass.getConstructor(Integer.class, String.class).newInstance(2, "123"); // 通过获取构造函数对象 对User进行实例化
        field.set(obj, 123);
        User user1 = (User) obj;
        System.out.println("通过反射 实例化对象：id:" + user1.id + ",age:" + user1.age);
    }
}

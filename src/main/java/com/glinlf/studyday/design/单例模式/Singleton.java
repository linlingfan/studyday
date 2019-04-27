package com.glinlf.studyday.design.单例模式;

/**
 * @author glinlf
 * @date 2019-04-27
 * @Description TODO
 **/
public class Singleton {


    // 饿汉式 和 懒汉式 ，单例方法为静态方法。
    public static void main(String[] args){

    }

}

// 饿汉式 静态常量 类装载的时候完成实例化 避免线程同步问题 缺点 没有懒加载 内存浪费
class Singleton1 {

    private final static Singleton1 singleton1 = new Singleton1();

    public static Singleton1 getSingleton1() {
        return singleton1;
    }
}

// 懒汉式 线程不安全
class Singleton2 {
    private static Singleton2 singleton2;

    public static Singleton2 getSingleton2() {
        if (singleton2 == null) {
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}

// 懒汉式 线程安全 同步方法 不推荐
class Singleton3 {
    private static Singleton3 singleton3;

    public static synchronized Singleton3 getSingleton3() {
        if (singleton3 == null) {
            singleton3 = new Singleton3();
        }
        return singleton3;
    }
}

// 懒汉式 双重检查 线程安全（推荐）
class Singleton4 {
    private static volatile Singleton4 singleton4;

    public static synchronized Singleton4 getSingleton4() {
        if (singleton4 == null) {
            synchronized (Singleton4.class) {
                if (singleton4 == null) {
                    singleton4 = new Singleton4();
                }
            }
        }
        return singleton4;
    }
}
package com.glinlf.studyday.design.工厂模式;

/**
 * @author glinlf
 * @date 2019-04-27
 * @Description 1简单工厂模式 2工厂方法 3抽象工厂模式
 **/
public class Factory {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        FactorySimple factorySimple = new FactorySimple();
        Car1 car1 = factorySimple.getCar("BWM");
        System.out.println(car1.getCarName());

        // 工厂方法
        CarFactory carFactory = new CarBWMFactory(); // 需要什么car 从该车工厂获取 其实很不方便。
        Car1 car11 = carFactory.getCar();
        System.out.println(car11.getCarName());

        // 抽象工厂模式 将车工厂再次抽象出去
        AbstractFactory factoryProducer = FactoryProducer.getAbstractFactory("com.glinlf.studyday.design.工厂模式.CarBenZFactory2");
        Car1 car12 = factoryProducer.getCar();
        System.out.println(car12.getCarName());
    }
}

interface Car1 {
    public String getCarName();
}

class CarBwm1 implements Car1 {

    @Override
    public String getCarName() {
        return "BWM";
    }
}

class CarBenZ1 implements Car1 {

    @Override
    public String getCarName() {
        return "BenZ";
    }
}

// 简单工厂模式
class FactorySimple {

    public Car1 getCar(String name) {
        if (name == "BWM") {
            return new CarBwm1();
        } else if (name == "BenZ") {
            return new CarBenZ1();
        } else {
            return null;
        }
    }
}

// 工厂方法 每辆车有自己的生产工厂

interface CarFactory {
    Car1 getCar();
}

class CarBWMFactory implements CarFactory {

    @Override
    public Car1 getCar() {
        return new CarBwm1();
    }
}

class CarBenZFactory implements CarFactory {
    @Override
    public Car1 getCar() {
        return new CarBenZ1();
    }
}

// 抽象工厂模式

abstract class AbstractFactory {

    abstract Car1 getCar();
}

class CarBWMFactory1 extends AbstractFactory {

    @Override
    Car1 getCar() {
        return new CarBwm1();
    }
}

class CarBenZFactory2 extends AbstractFactory {

    @Override
    Car1 getCar() {
        return new CarBenZ1();
    }
}

// 获取需要的生产工厂
class FactoryProducer {
    public static AbstractFactory getAbstractFactory(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        AbstractFactory abstractFactory = null;
        abstractFactory = (AbstractFactory) Class.forName(className).newInstance();
        return abstractFactory;
    }
}

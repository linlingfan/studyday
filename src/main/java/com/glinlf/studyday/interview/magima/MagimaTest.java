package com.glinlf.studyday.interview.magima;

/**
 * @author glinlf
 * @date 2019-04-08
 * @Description TODO
 **/
public class MagimaTest {

    public static void main(String[] args) {

        magimaFunction();
//        MagimaTest st = new MagimaTest();
    }

    static MagimaTest st = new MagimaTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    MagimaTest() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void magimaFunction() {
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;
}

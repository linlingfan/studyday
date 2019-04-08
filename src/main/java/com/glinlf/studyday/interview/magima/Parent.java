package com.glinlf.studyday.interview.magima;

/**
 * @author glinlf
 * @date 2019-04-08
 * @Description TODO
 **/
public class Parent {

    int i = 1;

    Parent() {
        System.out.println(i);
        int x = getValue();
        System.out.println(x);
    }

    {
        i = 2;
    }

    protected int getValue() {
        return i;
    }
}
